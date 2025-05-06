package com.warepulse.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

import com.warepulse.security.CustomUserDetailService;



@Configuration
public class SecurityConfig {

    private final CustomUserDetailService userDetailService;
    private final PasswordConfig passwordConfig;

    public SecurityConfig(CustomUserDetailService uds, PasswordConfig pwc) {
        this.userDetailService = uds;
        this.passwordConfig = pwc;
    }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authConfig
  ) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Autowired
     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
         auth
           .userDetailsService(userDetailService)
           .passwordEncoder(passwordConfig.passwordEncoder());
     }

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
            "/api/notifications/**",
            "/api/Auth/**"
            ).permitAll()
            // Lascia accessibili gli endpoint per i prodotti
                .anyRequest().authenticated()      
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}

