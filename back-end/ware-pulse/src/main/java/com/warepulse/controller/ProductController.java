package com.warepulse.controller;

import org.springframework.web.bind.annotation.*;
import com.warepulse.model.Product;
import com.warepulse.model.User;
import com.warepulse.service.ProductService;
import com.warepulse.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id).orElse(null);

            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // // Endpoint per creare un nuovo prodotto
    // @PostMapping
    // public Product createProduct(@RequestBody Product product) {
    //     return productService.saveProduct(product);
    // }

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody Product product,
            Principal principal                // <— aggiungi questo
    ) {
        // 1) recupera l’username da Principal
        String username = principal.getName();

        // 2) carica l’utente dal DB
        User owner = userService.findByUsername(username).orElse(null);
        if (owner == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 3) imposta l’owner sul prodotto
        product.setOwner(owner);

        // 4) salva
        Product saved = productService.saveProduct(product);
        return ResponseEntity.ok(saved);
    }


    // Endpoint per aggiornare un prodotto esistente
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product existingProduct = productService.getProductById(id).orElse(null);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        if (productDetails.getName() != null) {
            existingProduct.setName(productDetails.getName());
        }
        if (productDetails.getDescription() != null) {
            existingProduct.setDescription(productDetails.getDescription());
        }
        if (productDetails.getQuantity() != 0) {
            existingProduct.setQuantity(productDetails.getQuantity());
        }
        if (productDetails.getPrice() != 0) {
            existingProduct.setPrice(productDetails.getPrice());
        }

        try {
            Product updatedProduct = productService.saveProduct(existingProduct);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/me")
    public ResponseEntity<List<Product>> myProducts() {
    return ResponseEntity.ok(productService.findMyProducts());
}
  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
}
