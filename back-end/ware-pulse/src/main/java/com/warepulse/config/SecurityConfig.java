package com.warepulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

import com.warepulse.security.JwtUtil;
import com.warepulse.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtFilter jwtFilter;
    

    public SecurityConfig(UserDetailsService uds,
                          PasswordEncoder pwEncoder,
                          JwtFilter jwtFilter
                          ) {
        this.userDetailsService = uds;
        this.passwordEncoder   = pwEncoder;
        this.jwtFilter         = jwtFilter;
       

        
    }

    /**
     * 1️⃣ AuthenticationManager: serve a Spring Security per
     * validare username/password.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
        return auth.build();
    }

   

    /**
     * 2️⃣ SecurityFilterChain: qui si configura
     *    - CORS / CSRF
     *    - stateless JWT (no session HTTP)
     *    - quali endpoint sono pubblici, quali protetti
     *    - inserimento del JwtFilter
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          // abilitare CORS e disabilitare CSRF (usiamo token)
          .cors(Customizer.withDefaults())
          .csrf(csrf -> csrf.disable())

          // JWT è stateless: niente sessione server
          .sessionManagement(sess -> sess
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

          // regole di accesso
          .authorizeHttpRequests(auth -> auth

            // sempre per pre-flight CORS
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            // endpoint di registrazione e login APERTI
            .requestMatchers("/api/auth/register", "/api/auth/login")
              .permitAll()

            // info “me” richiedono token
            .requestMatchers("/api/auth/me")
              .authenticated()

            // dashboard e API CRUD private
            .requestMatchers("/api/dashboard/**",
                             "/api/products/**",
                             "/api/orders/**",
                             "/api/clients/**",
                             "/api/completed-orders/**",
                             "/api/notifications/**")
              .authenticated()

            // blocca tutto il resto
            .anyRequest().denyAll()
          )

          // infila il filtro JWT prima di UsernamePasswordAuthentication
          .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
          ;

        return http.build();
    }
}
