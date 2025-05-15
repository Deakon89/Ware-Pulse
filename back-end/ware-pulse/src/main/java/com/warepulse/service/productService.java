// src/main/java/com/warepulse/service/ProductService.java
package com.warepulse.service;

import com.warepulse.model.Notification;
import com.warepulse.model.Product;
import com.warepulse.model.User;
import com.warepulse.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private NotificationService notificationService;

   
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    

    
    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

   
    public Product save(Product product) {
        Product saved = productRepo.save(product);

    
    if (saved.getQuantity() < 5) {
        Notification n = new Notification();
        n.setMessage("Scorte basse per il prodotto: " + saved.getName());
        n.setOwner(saved.getOwner());
        n.setTimestamp(Instant.now());
        notificationService.saveAndSend(n);
    }

    return saved;
}

    
    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    
    public List<Product> findByOwner(User owner) {
        return productRepo.findByOwner(owner);
    }
}

