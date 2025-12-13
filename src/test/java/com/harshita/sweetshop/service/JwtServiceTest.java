package com.harshita.sweetshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void generateToken_shouldCreateValidToken() {
        // Arrange
        String email = "test@example.com";
        String role = "USER";

        // Act
        String token = jwtService.generateToken(email, role);

        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 50); // JWT tokens are long
        assertTrue(token.split("\\.").length == 3); // JWT has 3 parts: header.payload.signature
    }

    @Test
    void extractEmail_shouldReturnCorrectEmail() {
        // Arrange
        String email = "alice@example.com";
        String role = "USER";
        String token = jwtService.generateToken(email, role);

        // Act
        String extractedEmail = jwtService.extractEmail(token);

        // Assert
        assertEquals(email, extractedEmail);
    }

    @Test
    void extractRole_shouldReturnCorrectRole() {
        // Arrange
        String email = "admin@example.com";
        String role = "ADMIN";
        String token = jwtService.generateToken(email, role);

        // Act
        String extractedRole = jwtService.extractRole(token);

        // Assert
        assertEquals(role, extractedRole);
    }

    @Test
    void isTokenValid_shouldReturnTrueForValidToken() {
        // Arrange
        String email = "bob@example.com";
        String role = "USER";
        String token = jwtService.generateToken(email, role);

        // Act
        boolean isValid = jwtService.isTokenValid(token, email);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void isTokenValid_shouldReturnFalseForWrongEmail() {
        // Arrange
        String email = "charlie@example.com";
        String role = "USER";
        String token = jwtService.generateToken(email, role);

        // Act
        boolean isValid = jwtService.isTokenValid(token, "wrong@example.com");

        // Assert
        assertFalse(isValid);
    }
}
