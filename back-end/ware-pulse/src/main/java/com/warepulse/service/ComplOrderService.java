package com.warepulse.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.warepulse.model.CompletedOrder;
import com.warepulse.repository.ComplOrderRepo;
import com.warepulse.security.SecurityService;

import java.util.List;

@Service
public class ComplOrderService {
    
    @Autowired
    private ComplOrderRepo compOrderRepo;
    @Autowired 
    private SecurityService securityService;
    
    public List<CompletedOrder> findAllCompletedOrders() {
        String me = securityService.currentUsername();
        return compOrderRepo.findByOwnerUsername(me);
    }

    public CompletedOrder findCompletedOrderById(Long id) {
        return compOrderRepo.findById(id).orElse(null);
    }

    public void deleteCompletedOrder(Long id) {
        compOrderRepo.deleteById(id);
    }

    public String currentUsername() {
    return SecurityContextHolder
              .getContext()
              .getAuthentication()
              .getName();
}
}
