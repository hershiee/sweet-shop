package com.harshita.sweetshop.service;

import com.harshita.sweetshop.exception.DuplicateEmailException;
import com.harshita.sweetshop.exception.InvalidCredentialsException;
import com.harshita.sweetshop.exception.ValidationException;
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

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public User registerUser(String email, String password, String name) {
        validateEmail(email);
        validatePassword(password);

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email already registered: " + email);
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);

        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);

        user.setRole("USER");

        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return user;
    }

    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Invalid email format");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters");
        }
    }
}