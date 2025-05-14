package com.warepulse.config;

import com.warepulse.security.JwtFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsService customUserDetailsService;

    // 1) PasswordEncoder unico
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2) AuthenticationManager esposto come bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // 3) CORS configuration per autorizzare Angular
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:4200")); // il tuo front-end
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    // 4) Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .cors(Customizer.withDefaults())            // applica il corsConfigurationSource()
          .csrf(csrf -> csrf.disable())               // disabilita CSRF (stateless JWT)
          .sessionManagement(sess -> 
               sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(auth -> auth
              .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
              .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
              .requestMatchers("/api/auth/me").authenticated()
              .requestMatchers(
                  "/api/dashboard/**",
                //   "/api/products/**",
                //   "/api/orders/**",
                //   "/api/clients/**",
                //   "/api/completed-orders/**",
                  "/api/notifications/**",
                  "/api/users/**"
              ).hasAnyRole("USER") 
              .anyRequest().denyAll()
          )
          // inserisce il filtro JWT prima di quello standard
          .addFilterBefore((Filter) jwtFilter, 
                   org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}






// package com.warepulse.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.warepulse.security.JwtUtil;
// import com.warepulse.security.JwtFilter;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final UserDetailsService userDetailsService;
//     private final PasswordEncoder passwordEncoder;
//     private final JwtFilter jwtFilter;
    

//     public SecurityConfig(UserDetailsService uds,
//                           PasswordEncoder pwEncoder,
//                           JwtFilter jwtFilter
//                           ) {
//         this.userDetailsService = uds;
//         this.passwordEncoder   = pwEncoder;
//         this.jwtFilter         = jwtFilter;
       

        
//     }

//     /**
//      * 1️⃣ AuthenticationManager: serve a Spring Security per
//      * validare username/password.
//      */
//     @Bean
//     public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//         AuthenticationManagerBuilder auth = 
//             http.getSharedObject(AuthenticationManagerBuilder.class);
//         auth.userDetailsService(userDetailsService)
//             .passwordEncoder(passwordEncoder);
//         return auth.build();
//     }

   

//     /**
//      * 2️⃣ SecurityFilterChain: qui si configura
//      *    - CORS / CSRF
//      *    - stateless JWT (no session HTTP)
//      *    - quali endpoint sono pubblici, quali protetti
//      *    - inserimento del JwtFilter
//      */
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//           // abilitare CORS e disabilitare CSRF (usiamo token)
//           .cors(Customizer.withDefaults())
//           .csrf(csrf -> csrf.disable())

//           // JWT è stateless: niente sessione server
//           .sessionManagement(sess -> sess
//             .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

//           // regole di accesso
//           .authorizeHttpRequests(auth -> auth

//             // sempre per pre-flight CORS
//             .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

//             // endpoint di registrazione e login APERTI
//             .requestMatchers("/api/auth/register", "/api/auth/login")
//               .permitAll()

//             // info “me” richiedono token
//             .requestMatchers("/api/auth/me")
//               .authenticated()

//             // dashboard e API CRUD private
//             .requestMatchers("/api/dashboard/**",
//                              "/api/products/**",
//                              "/api/orders/**",
//                              "/api/clients/**",
//                              "/api/completed-orders/**",
//                              "/api/notifications/**")
//               .authenticated()

//             // blocca tutto il resto
//             .anyRequest().denyAll()
//           )

//           // infila il filtro JWT prima di UsernamePasswordAuthentication
//           .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//           ;

//         return http.build();
//     }
// }
