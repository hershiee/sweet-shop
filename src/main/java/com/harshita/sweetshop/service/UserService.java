package com.harshita.sweetshop.service;

import com.harshita.sweetshop.model.User;
import com.harshita.sweetshop.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Email validation regex
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User registerUser(String email, String password, String name) {
        // Validate email format
        validateEmail(email);

        // Validate password length
        validatePassword(password);

        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered: " + email);
        }

        // Create new user
        User user = new User();
        user.setEmail(email);
        user.setName(name);

        // Encrypt password before saving
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);

        user.setRole("USER");

        // Save and return
        return userRepository.save(user);
    }

    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new RuntimeException("Invalid email format");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }
    }
}