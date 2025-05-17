// src/main/java/com/warepulse/controller/AuthController.java
package com.warepulse.controller;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.warepulse.model.User;
import com.warepulse.repository.UserRepo;
import com.warepulse.security.CustomUserDetailService;
import com.warepulse.security.JwtUtil;
import com.warepulse.service.UserService;

import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authManager;
  private final UserService         userService;
  private final JwtUtil             jwtUtil;
  private final CustomUserDetailService uds;
  private final UserRepo userRepo;
  

  public AuthController(AuthenticationManager am,
                        UserService us,
                        JwtUtil ju,
                        CustomUserDetailService uds,
                        UserRepo ur
                        ) {
    this.authManager = am;
    this.userService = us;
    this.jwtUtil     = ju;
    this.uds         = uds;
    this.userRepo    = ur;
    
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody Map<String,String> b) {
    User user  = userService.register(
                 b.get("username"),
                 b.get("password"),
                 b.get("email")
               );
    return ResponseEntity.ok(Map.of(
      "id", user.getId(),
      "username", user.getUsername(),
      "email", user.getEmail()
    ));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String,String> b) {
    authManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        b.get("username"),
        b.get("password")
      )
    );
    String token = jwtUtil.generateToken(b.get("username"));
    return ResponseEntity.ok(Map.of("token", token));
  }

  // @GetMapping("/me")
  // public ResponseEntity<?> me() {
  //   UserDetails ud = (UserDetails)
  //     SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  //   return ResponseEntity.ok(Map.of(
  //     "username", ud.getUsername(),
  //     "authorities", ud.getAuthorities()
  //   ));
  // }

  @GetMapping("/me")
    public ResponseEntity<User> getProfile() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    User user = userRepo.findByUsername(username).orElseThrow();
    return ResponseEntity.ok(user);
}

}

