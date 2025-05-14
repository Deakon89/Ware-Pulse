// src/main/java/com/warepulse/controller/CompletedOrderController.java
package com.warepulse.controller;

import com.warepulse.model.CompletedOrder;
import com.warepulse.service.CompletedOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/completed-orders")
public class CompletedOrderController {

    private final CompletedOrderService service;
    public CompletedOrderController(CompletedOrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<CompletedOrder> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompletedOrder> getById(@PathVariable Long id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CompletedOrder create(@RequestBody CompletedOrder co) {
        return service.save(co);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompletedOrder> update(@PathVariable Long id,
                                                 @RequestBody CompletedOrder co) {
        return service.findById(id)
            .map(existing -> {
                co.setId(id);
                return ResponseEntity.ok(service.save(co));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

