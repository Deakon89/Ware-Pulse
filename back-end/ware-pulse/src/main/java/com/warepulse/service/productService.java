package com.warepulse.service;

import org.springframework.stereotype.Service;
import com.warepulse.model.product;
import com.warepulse.repository.productRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class productService {

    @Autowired
    private productRepo productRepo;

    public List<product> findAll() {
        return productRepo.findAll();
    }

    public Optional<product> getProductById(Long id) {
        Optional<product> product = productRepo.findById(id);
        return product;
    }
    
}
