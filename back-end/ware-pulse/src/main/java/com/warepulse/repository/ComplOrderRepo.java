package com.warepulse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warepulse.model.CompletedOrder;

@Repository
public interface ComplOrderRepo extends JpaRepository<CompletedOrder, Long> {
    List<CompletedOrder> findByOwnerUsername(String username);
}
