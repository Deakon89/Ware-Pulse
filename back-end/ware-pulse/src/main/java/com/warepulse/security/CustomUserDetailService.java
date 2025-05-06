// src/main/java/com/warepulse/security/CustomUserDetailService.java
package com.warepulse.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.warepulse.service.UserService;
import com.warepulse.model.User;

@Service
public class CustomUserDetailService implements UserDetailsService {
  
    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }
  
    @Override
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException {
        User user = userService.findByUsername(username)
            .orElseThrow(() -> 
                new UsernameNotFoundException("Utente non trovato: " + username));
        return new CustomUserDetail(user);
    }
}

