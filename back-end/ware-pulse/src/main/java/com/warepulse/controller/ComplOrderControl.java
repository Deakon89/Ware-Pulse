package com.warepulse.controller;

import com.warepulse.model.CompletedOrder;
import com.warepulse.service.ComplOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/completed-orders")
public class ComplOrderControl {

    @Autowired
    private ComplOrderService compOrderService;

    @GetMapping
    public ResponseEntity<List<CompletedOrder>> getAllCompletedOrders() {
        List<CompletedOrder> completedOrders = compOrderService.findAllCompletedOrders();
        return ResponseEntity.ok(completedOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompletedOrder> getCompletedOrderById(@PathVariable Long id) {
        CompletedOrder completedOrder = compOrderService.findCompletedOrderById(id);
        if (completedOrder != null) {
            return ResponseEntity.ok(completedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompletedOrder(@PathVariable Long id) {
        compOrderService.deleteCompletedOrder(id);
        return ResponseEntity.noContent().build();
    }
    
}
