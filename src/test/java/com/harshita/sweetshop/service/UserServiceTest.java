package com.harshita.sweetshop.service;

import com.harshita.sweetshop.model.User;
import com.harshita.sweetshop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    private UserService userService;

    @Test
    void registerUser_shouldSaveUserWithEncryptedPassword() {
        // Arrange
        userService = new UserService(userRepository);

        String email = "john@example.com";
        String plainPassword = "password123";
        String name = "John Doe";

        // Act
        User registeredUser = userService.registerUser(email, plainPassword, name);

        // Assert
        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(email, registeredUser.getEmail());
        assertEquals(name, registeredUser.getName());
        assertEquals("USER", registeredUser.getRole());

        // Password should be encrypted (not plain text)
        assertNotEquals(plainPassword, registeredUser.getPassword());
        assertTrue(registeredUser.getPassword().length() > 20); // BCrypt hashes are long
    }

    @Test
    void registerUser_shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        userService = new UserService(userRepository);

        String email = "duplicate@example.com";

        // First registration
        userService.registerUser(email, "password123", "First User");

        // Act & Assert - Try to register again with same email
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(email, "password456", "Second User");
        });

        assertTrue(exception.getMessage().contains("Email already registered"));
    }

    @Test
    void registerUser_shouldThrowExceptionWhenEmailIsInvalid() {
        // Arrange
        userService = new UserService(userRepository);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser("invalid-email", "password123", "Test User");
        });

        assertTrue(exception.getMessage().contains("Invalid email format"));
    }

    @Test
    void registerUser_shouldThrowExceptionWhenPasswordIsTooShort() {
        // Arrange
        userService = new UserService(userRepository);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser("test@example.com", "123", "Test User");
        });

        assertTrue(exception.getMessage().contains("Password must be at least 6 characters"));
    }

    @Test
    void loginUser_shouldReturnUserWhenCredentialsAreValid() {
        // Arrange
        userService = new UserService(userRepository);

        String email = "alice@example.com";
        String password = "securepass123";

        // Register user first
        userService.registerUser(email, password, "Alice Smith");

        // Act
        User loggedInUser = userService.loginUser(email, password);

        // Assert
        assertNotNull(loggedInUser);
        assertEquals(email, loggedInUser.getEmail());
        assertEquals("Alice Smith", loggedInUser.getName());
    }

    @Test
    void loginUser_shouldThrowExceptionWhenEmailNotFound() {
        // Arrange
        userService = new UserService(userRepository);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.loginUser("nonexistent@example.com", "password123");
        });

        assertTrue(exception.getMessage().contains("Invalid email or password"));
    }

    @Test
    void loginUser_shouldThrowExceptionWhenPasswordIsIncorrect() {
        // Arrange
        userService = new UserService(userRepository);

        String email = "bob@example.com";
        String correctPassword = "correctpass123";

        // Register user
        userService.registerUser(email, correctPassword, "Bob Jones");

        // Act & Assert - Try with wrong password
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.loginUser(email, "wrongpassword");
        });

        assertTrue(exception.getMessage().contains("Invalid email or password"));
    }

    @Test
    void loginUser_shouldWorkWithEncryptedPassword() {
        // Arrange
        userService = new UserService(userRepository);

        String email = "charlie@example.com";
        String password = "mypassword456";

        // Register user (password gets encrypted)
        User registered = userService.registerUser(email, password, "Charlie Brown");

        // Verify password is encrypted in database
        User userInDb = userRepository.findByEmail(email).get();
        assertNotEquals(password, userInDb.getPassword());

        // Act - Login with plain password should still work
        User loggedIn = userService.loginUser(email, password);

        // Assert
        assertNotNull(loggedIn);
        assertEquals(email, loggedIn.getEmail());
    }
}
