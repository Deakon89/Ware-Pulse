package com.warepulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warepulse.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  boolean existsByUsername(String username);
}

