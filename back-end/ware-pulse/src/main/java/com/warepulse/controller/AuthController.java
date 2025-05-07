package com.warepulse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.warepulse.dto.RegisterRequest;
import com.warepulse.model.User;
import com.warepulse.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService us) {
        this.userService = us;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        User created = userService.register(req.getUsername(), req.getPassword(), req.getEmail());
        // non ritorniamo la passwordHash
        return ResponseEntity
            .status(201)
            .body("{ \"id\": " + created.getId() + ", \"username\": \"" + created.getUsername() + "\" }");
    }

    // Se ti basta BASIC auth, non serve un /login esplicito:
    // Spring Security gestisce automaticamente il /login via HttpBasic.
}
