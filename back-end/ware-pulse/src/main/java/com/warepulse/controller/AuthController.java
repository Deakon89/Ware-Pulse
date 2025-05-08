package com.warepulse.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.warepulse.dto.RegisterRequest;
import com.warepulse.model.User;
import com.warepulse.security.JwtUtil;
import com.warepulse.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService us, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.userService = us;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        User created = userService.register(req.getUsername(), req.getPassword(), req.getEmail());
        // non ritorniamo la passwordHash
        return ResponseEntity
            .status(201)
            .body("{ \"id\": " + created.getId() + ", \"username\": \"" + created.getUsername() + "\" }");
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String,String> creds) {
  try {
    Authentication auth = authManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        creds.get("username"),
        creds.get("password")
      )
    );
    String token = jwtUtil.generateToken(auth.getName());
    return ResponseEntity.ok(Map.of("token", token));
  } catch (AuthenticationException ex) {
    // credenziali non valide → 401 Unauthorized
    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body(Map.of("error", "Invalid credentials"));
  }
}

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody Map<String,String> creds) {
    //   var auth = authManager.authenticate(
    //     new UsernamePasswordAuthenticationToken(
    //       creds.get("username"), creds.get("password")
    //     )
    //   );
    //   String token = jwtUtil.generateToken(auth.getName());
    //   return ResponseEntity.ok(Map.of("token", token));
    // }

    @GetMapping("/me")
//       public ResponseEntity<?> me(Authentication authentication) {
//     // authentication.getName() è lo username estratto dal JWT
//       User user = userService.findByUsername(authentication.getName());
//       if (user == null) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//     }
//     // Maschera la password
//       user.setPassword(null);
//       return ResponseEntity.ok(user);
// }
      public ResponseEntity<?> me(Authentication authentication) {
      Optional<User> opt = userService.findByUsername(authentication.getName());
      if (opt.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
      User user = opt.get();
      user.setPassword(null);
      return ResponseEntity.ok(user);
    }
}
