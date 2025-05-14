// src/main/java/com/warepulse/service/OrderService.java
package com.warepulse.service;

import com.warepulse.model.Order;
import com.warepulse.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    // Tutti gli ordini (admin)
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

    // Solo gli ordini del mio utente (Dashboard)
    public List<Order> findByClientOwnerUsername(String username) {
        return orderRepo.findByOwnerUsername(username);
    }
}

