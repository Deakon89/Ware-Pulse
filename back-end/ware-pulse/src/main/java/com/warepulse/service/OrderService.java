package com.warepulse.service;

import org.springframework.stereotype.Service;

import com.warepulse.model.CompletedOrder;
import com.warepulse.model.Order;
import com.warepulse.model.OrderStatus;
import com.warepulse.repository.ComplOrderRepo;
import com.warepulse.repository.OrderRepo;
import com.warepulse.security.SecurityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ComplOrderRepo complOrderRepo;

    @Autowired
    private ProductService productService;

    @Autowired 
    private SecurityService securityService;

    public List<Order> findByOwnerUsername(String username) {
        String me = securityService.currentUsername();
        return orderRepo.findByOwnerUsername(me);
    }

    @Transactional
    public void completeOrder(Long orderId){
        Order order = orderRepo.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if(order.getStatus() == OrderStatus.EVASO){
            throw new RuntimeException("Order already completed");
        }
        productService.updateProductQuantity(order.getProduct().getId(), order.getQuantityOrdered());
        
        // Aggiorna lo stato dell'ordine
        order.setStatus(OrderStatus.EVASO);
        
        // Crea l'oggetto CompletedOrder copiando le informazioni
        CompletedOrder completedOrder = new CompletedOrder();
        completedOrder.setProductId(order.getProduct().getId());
        completedOrder.setProductName(order.getProduct().getName());
        completedOrder.setQuantityOrdered(order.getQuantityOrdered());
        completedOrder.setDate(order.getDate());
        completedOrder.setClient(order.getClient());
        
        // Salva l'ordine evaso nella tabella dei completed orders
        complOrderRepo.save(completedOrder);
        
        // Rimuove l'ordine dalla tabella degli ordini attivi
        orderRepo.delete(order);
    }

    public String currentUsername() {
    return SecurityContextHolder
              .getContext()
              .getAuthentication()
              .getName();
}

   
    
}
