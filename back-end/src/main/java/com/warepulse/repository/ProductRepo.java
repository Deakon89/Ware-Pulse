package com.warepulse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warepulse.model.Product;
import com.warepulse.model.User;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByOwner(User owner);
}
