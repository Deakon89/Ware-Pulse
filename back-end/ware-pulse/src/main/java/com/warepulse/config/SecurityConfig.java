package com.warepulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .requestMatchers(
"/api/products/**",
            "/api/orders",
            "/api/orders/**",
            "/api/clients/**",
            "/api/completed-orders",
            "/api/notifications/**"
            ).permitAll()
            // Lascia accessibili gli endpoint per i prodotti
                .anyRequest().authenticated()      
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}

