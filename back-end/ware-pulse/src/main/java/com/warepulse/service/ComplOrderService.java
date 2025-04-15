package com.warepulse.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.warepulse.model.CompletedOrder;
import com.warepulse.repository.ComplOrderRepo;

import java.util.List;

@Service
public class ComplOrderService {
    
    @Autowired
    private ComplOrderRepo compOrderRepo;
    
    public List<CompletedOrder> findAllCompletedOrders() {
        return compOrderRepo.findAll();
    }

    public CompletedOrder findCompletedOrderById(Long id) {
        return compOrderRepo.findById(id).orElse(null);
    }

    public void deleteCompletedOrder(Long id) {
        compOrderRepo.deleteById(id);
    }
}
