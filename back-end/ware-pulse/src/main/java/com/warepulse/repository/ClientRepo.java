package com.warepulse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warepulse.model.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    List<Client> findByOwnerUsername(String username);
}
