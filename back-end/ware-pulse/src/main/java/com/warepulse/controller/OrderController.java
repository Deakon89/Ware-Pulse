package com.warepulse.controller;

import com.warepulse.model.Order;
import com.warepulse.model.OrderStatus;
import com.warepulse.repository.OrderRepo;
import com.warepulse.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
    
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        order.setStatus(OrderStatus.NON_EVASO);
        order.setDate(new Date());
        return orderRepo.save(order);
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
