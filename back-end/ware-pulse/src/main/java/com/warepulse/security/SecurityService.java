// src/main/java/com/warepulse/service/SecurityService.java
package com.warepulse.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    /** Restituisce lo username dell’utente autenticato */
    public String currentUsername() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }
}

