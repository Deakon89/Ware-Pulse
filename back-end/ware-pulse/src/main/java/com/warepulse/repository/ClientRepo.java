package com.warepulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warepulse.model.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    
}
