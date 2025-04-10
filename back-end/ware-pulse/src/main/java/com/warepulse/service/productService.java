package com.warepulse.service;

import org.springframework.stereotype.Service;
import com.warepulse.model.Product;
import com.warepulse.model.product;
import com.warepulse.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    // // Get all products
     public List<Product> getProducts() {
         return productRepo.findAll();
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
    

}