// src/main/java/com/warepulse/service/UserService.java
package com.warepulse.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.warepulse.model.User;
import com.warepulse.repository.UserRepo;

@Service
public class UserService {
    private final UserRepo repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepo repo, PasswordEncoder encoder) {
        this.repo    = repo;
        this.encoder = encoder;
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    // Registrazione: codifica la password e salva
    // public User register(String username, String rawPassword, String email) {
    //     User u = new User();
    //     u.setUsername(username);
    //     u.setEmail(email);
    //     u.setPassword(encoder.encode(rawPassword));
    //     return repo.save(u);
    // }

     public User register(String username, String rawPassword, String email) {
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        // ruolo di default
        u.setRoles(List.of("USER"));
        // hash della password
        u.setPassword(encoder.encode(rawPassword));
        return repo.save(u);
    }
}

