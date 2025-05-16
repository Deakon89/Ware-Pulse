// src/main/java/com/warepulse/service/OrderService.java
package com.warepulse.service;

import com.warepulse.model.User;
import com.warepulse.model.Order;
import com.warepulse.repository.OrderRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final ProductService productService;


    public OrderService(OrderRepo orderRepo, ProductService productService) {
        this.orderRepo = orderRepo;
        this.productService = productService;
    }

  
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepo.findById(id);
    }

    public Order save(Order order) {
        return orderRepo.save(order);
    }

    public void delete(Long id) {
        orderRepo.deleteById(id);
    }

    
    public List<Order> findByOwner(User owner) {
        return orderRepo.findByOwner(owner);
    }
}

