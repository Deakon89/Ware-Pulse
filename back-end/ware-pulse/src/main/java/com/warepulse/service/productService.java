package com.warepulse.service;

import org.springframework.stereotype.Service;
import com.warepulse.model.Product;
import com.warepulse.repository.ProductRepo;
import com.warepulse.security.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SecurityService securityService;
    
    // public List<Product> findByClientOwnerUsername() {
    //     String me = currentUsername();
    //     return productRepo.findByOwnerUsername(me);
    // }
    
    // // Get all products

    public List<Product> getProducts() {
        return productRepo.findAll();
    }
     public List<Product> findMyProducts() {
        String me = securityService.currentUsername();
        return productRepo.findByOwnerUsername(me);
    }
    // // product by id
     public Optional<Product> getProductById(Long id) {
         Optional<Product> product = productRepo.findById(id);
         return product;
     }
    // // save a product
     public Product saveProduct(Product product) {
         return productRepo.save(product);
    }
    // delete a product
    public void deleteProduct(Long id){
        productRepo.deleteById(id);
    }
    public void updateProductQuantity(Long productId, int quantityOrdered) {
        Product product = productRepo.findById(productId).orElse(null);
        if (product != null) {
            int newQuantity = product.getQuantity() - quantityOrdered;
            product.setQuantity(newQuantity);
            productRepo.save(product);

            // if (newQuantity <= 5) {
            //     // Invia notifica via email
            //     String subject = "Notifica: Quantità Prodotto";
            //     String text = "Il prodotto " + product.getName() + " ha raggiunto una quantità di " + newQuantity + " unità.";
            //     emailNotificationService.sendNotification("tuo_indirizzo@gmail.com", subject, text);
            // }
            if (product.getQuantity() >= 5) {
                notificationService.publish(
                    "Attenzione: il prodotto \"" 
                    + product.getName() 
                    + "\" ha quantità = " 
                    + product.getQuantity()
                );
            }
              
        }
    }

    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    public String currentUsername() {
    return SecurityContextHolder
              .getContext()
              .getAuthentication()
              .getName();
}
}