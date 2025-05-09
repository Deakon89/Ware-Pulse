package com.warepulse.service;

import com.warepulse.model.User;
import com.warepulse.repository.UserRepo;
import com.warepulse.security.CustomUserDetail;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }
    @Transactional
    public User register(String username, String password, String email) {
        // Controlla l’esistenza utilizzando il parametro username
        if (userRepo.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);    // non dimenticarti di settare l’email
        return userRepo.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

        public List<User> findAll() {
        return userRepo.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        if (!userRepo.existsById(id)) {
            throw new UsernameNotFoundException("User not found: " + id);
        }
        userRepo.deleteById(id);
    }

    public Optional<User> currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((CustomUserDetail) auth.getPrincipal()).getUsername();
        return userRepo.findByUsername(username);
    }   

   public Long currentUserId() {
    return currentUser().orElseThrow().getId();
}
}
