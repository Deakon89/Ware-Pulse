// src/main/java/com/warepulse/service/ProductService.java
package com.warepulse.service;

import com.warepulse.model.Product;
import com.warepulse.model.User;
import com.warepulse.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    /** Recupera tutti i prodotti (senza filtro). */
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    

    /** Recupera un prodotto per ID. */
    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    /** Crea o aggiorna un prodotto. */
    public Product save(Product product) {
        return productRepo.save(product);
    }

    /** Elimina un prodotto per ID. */
    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    /** Recupera solo i prodotti appartenenti allo user indicato. */
    public List<Product> findByOwner(User owner) {
        return productRepo.findByOwner(owner);
    }
}

