package com.warepulse.controller;

import com.warepulse.model.Client;
import com.warepulse.service.ClientService;
import com.warepulse.model.Order;
import com.warepulse.model.OrderStatus;
import com.warepulse.repository.OrderRepo;
import com.warepulse.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderRepo orderRepo;
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private ClientService clientService;
    
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
    
     @PostMapping
     public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        if(order.getClient() == null || order.getClient().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Client client = clientService.findClientById(order.getClient().getId());
        if(client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        order.setClient(client);
        order.setStatus(OrderStatus.NON_EVASO);
        order.setDate(new Date());
        Order savedOrder = orderRepo.save(order);
        return ResponseEntity.ok(savedOrder);
        //  order.setStatus(OrderStatus.NON_EVASO);
        //  order.setDate(new Date());
        //  return orderRepo.save(order);
     }

    @PostMapping("/{orderId}/complete")
    public ResponseEntity<String> completeOrder(@PathVariable Long orderId) {
        try{
            orderService.completeOrder(orderId);
            return ResponseEntity.ok("Ordine completato con successo");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
