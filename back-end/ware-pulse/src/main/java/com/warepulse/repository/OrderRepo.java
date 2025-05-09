package com.warepulse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warepulse.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByClientOwnerUsername(String username);
}
