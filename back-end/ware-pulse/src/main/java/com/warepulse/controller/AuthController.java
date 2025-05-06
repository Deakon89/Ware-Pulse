package com.warepulse.controller;

import com.warepulse.model.User;
import com.warepulse.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/Auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String email = body.get("email");
        User created = userService.registrer(username, password, email);
        return ResponseEntity.ok(Map.of(
            "id", created.getId(),
            "username", created.getUsername(),
            "email", created.getEmail()
        ));
    }

    
}
