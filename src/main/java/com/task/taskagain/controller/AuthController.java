package com.task.taskagain.controller;

import com.task.taskagain.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> payload) {
        String token = authService.registerUser(
                (String) payload.get("username"),
                (String) payload.get("password"),
                (boolean) payload.get("isAdmin")
        );
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String token = authService.loginUser(payload.get("username"), payload.get("password"));

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", payload.get("username")
        ));
    }

}
