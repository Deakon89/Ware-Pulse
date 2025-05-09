package com.warepulse.repository;

import com.warepulse.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByOwnerUsername(String username);
}
