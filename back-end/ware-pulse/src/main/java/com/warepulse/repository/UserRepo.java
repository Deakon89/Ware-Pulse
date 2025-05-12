// package com.warepulse.repository;

// import com.warepulse.model.User;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.Optional;

// public interface UserRepo extends JpaRepository<User,Long> {
//   Optional<User> findByUsername(String username);
//   boolean existsByUsername(String username);
// }


// src/main/java/com/warepulse/repository/UserRepo.java
package com.warepulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warepulse.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  boolean existsByUsername(String username);
}

