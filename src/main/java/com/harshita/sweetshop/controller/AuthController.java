package com.harshita.sweetshop.controller;

import com.harshita.sweetshop.dto.AuthResponse;
import com.harshita.sweetshop.dto.LoginRequest;
import com.harshita.sweetshop.dto.RegisterRequest;
import com.harshita.sweetshop.model.User;
import com.harshita.sweetshop.service.JwtService;
import com.harshita.sweetshop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        // Register the user
        User user = userService.registerUser(
                request.getEmail(),
                request.getPassword(),
                request.getName()
        );

        // Create response
        AuthResponse response = new AuthResponse();
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setRole(user.getRole());
        response.setMessage("User registered successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Validate credentials
        User user = userService.loginUser(request.getEmail(), request.getPassword());

        // Generate JWT token
        String token = jwtService.generateToken(user.getEmail(), user.getRole());

        // Create response
        AuthResponse response = new AuthResponse(
                token,
                user.getEmail(),
                user.getName(),
                user.getRole()
        );
        response.setMessage("Login successful");

        return ResponseEntity.ok(response);
    }
}