package com.warepulse.service;

import com.warepulse.model.CompletedOrder;
import com.warepulse.model.User;
import com.warepulse.repository.ComplOrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompletedOrderService {

    private final ComplOrderRepo repo;

    public CompletedOrderService(ComplOrderRepo repo) {
        this.repo = repo;
    }

    // Tutti (admin)
    public List<CompletedOrder> findAll() {
        return repo.findAll();
    }

    public Optional<CompletedOrder> findById(Long id) {
        return repo.findById(id);
    }

    public CompletedOrder save(CompletedOrder co) {
        return repo.save(co);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<CompletedOrder> findByOwner(User owner) {
        return repo.findByOwner(owner);
    }
}

