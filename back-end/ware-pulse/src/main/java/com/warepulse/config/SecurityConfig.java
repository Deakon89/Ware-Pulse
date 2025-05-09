package com.warepulse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.warepulse.security.CustomUserDetailService;
import com.warepulse.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

     @SuppressWarnings("unused")
    private final CustomUserDetailService userDetailService;
     @SuppressWarnings("unused")
    private final PasswordConfig passwordConfig;
    

     public SecurityConfig(CustomUserDetailService uds, PasswordConfig pwc) {
         this.userDetailService = uds;
         this.passwordConfig = pwc;
     
     }

//   @Bean
//   public AuthenticationManager authenticationManager(
//       AuthenticationConfiguration authConfig
//   ) throws Exception {
//     return authConfig.getAuthenticationManager();
//   }

@SuppressWarnings("removal")
@Bean
public AuthenticationManager authenticationManager(HttpSecurity http, 
    PasswordEncoder passwordEncoder, UserDetailsService uds) throws Exception {
  return http
    .getSharedObject(AuthenticationManagerBuilder.class)
    .userDetailsService(uds)
    .passwordEncoder(passwordEncoder)
    .and()
    .build();
}

//   @Autowired
//      public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//          auth
//            .userDetailsService(userDetailService)
//            .passwordEncoder(passwordConfig.passwordEncoder());
//      }

    @Autowired
    private JwtFilter jwtFilter;    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/me").authenticated()
            .requestMatchers(
                "/api/products/**",
                "/api/orders",
                "/api/orders/**",
                "/api/clients/**",
                "/api/completed-orders",
                "/api/notifications/**",
                "/api/users",
                "/api/users/**",
                "/api/auth/**",
                "/api/auth/register",
                "/api/auth/login"
                ).permitAll()
                .anyRequest().authenticated()      
            )
            // .httpBasic(Customizer.withDefaults());
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }
}

