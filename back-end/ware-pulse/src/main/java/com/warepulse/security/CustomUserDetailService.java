// src/main/java/com/warepulse/security/CustomUserDetailsService.java
package com.warepulse.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.warepulse.model.User;
import com.warepulse.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepo repo;
    public CustomUserDetailService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User u = repo.findByUsername(username)
                     .orElseThrow(() -> 
                       new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                 .withUsername(u.getUsername())
                 .password(u.getPassword())
                 .authorities("USER")      // o ruoli a piacere
                 .build();
    }
}

