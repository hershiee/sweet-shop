package com.harshita.sweetshop.services;

import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.repository.SweetRepository;
import com.harshita.sweetshop.service.SweetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SweetServiceTest {

    @Autowired
    private SweetRepository sweetRepository;

    private SweetService sweetService;

    @Test
    void getAllSweets_shouldReturnAllSweets() {
        // Arrange - Create SweetService (doesn't exist yet!)
        sweetService = new SweetService(sweetRepository);

        // Save some test sweets
        Sweet sweet1 = new Sweet();
        sweet1.setName("Kaju Katli");
        sweet1.setPrice(250.0);
        sweet1.setStock(100);
        sweetRepository.save(sweet1);

        Sweet sweet2 = new Sweet();
        sweet2.setName("Gulab Jamun");
        sweet2.setPrice(150.0);
        sweet2.setStock(50);
        sweetRepository.save(sweet2);

        Sweet sweet3 = new Sweet();
        sweet3.setName("Jalebi");
        sweet3.setPrice(100.0);
        sweet3.setStock(75);
        sweetRepository.save(sweet3);

        // Act
        List<Sweet> allSweets = sweetService.getAllSweets();

        // Assert
        assertNotNull(allSweets);
        assertEquals(3, allSweets.size());
        assertTrue(allSweets.stream().anyMatch(s -> s.getName().equals("Kaju Katli")));
        assertTrue(allSweets.stream().anyMatch(s -> s.getName().equals("Gulab Jamun")));
        assertTrue(allSweets.stream().anyMatch(s -> s.getName().equals("Jalebi")));
    }

    @Test
    void createSweet_shouldSaveSweet() {
        // Arrange
        sweetService = new SweetService(sweetRepository);

        Sweet newSweet = new Sweet();
        newSweet.setName("Mysore Pak");
        newSweet.setPrice(300.0);
        newSweet.setStock(60);
        newSweet.setCategory("Traditional");

        // Act
        Sweet savedSweet = sweetService.createSweet(newSweet);

        // Assert
        assertNotNull(savedSweet);
        assertNotNull(savedSweet.getId());
        assertEquals("Mysore Pak", savedSweet.getName());
        assertEquals(300.0, savedSweet.getPrice());
        assertEquals(60, savedSweet.getStock());
        assertEquals("Traditional", savedSweet.getCategory());
    }

    @Test
    void createSweet_shouldThrowExceptionWhenNameIsNull() {
        // Arrange
        sweetService = new SweetService(sweetRepository);

        Sweet invalidSweet = new Sweet();
        invalidSweet.setName(null);  // Invalid!
        invalidSweet.setPrice(200.0);
        invalidSweet.setStock(50);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sweetService.createSweet(invalidSweet);
        });

        assertTrue(exception.getMessage().contains("Sweet name is required"));
    }

    @Test
    void createSweet_shouldThrowExceptionWhenPriceIsNegative() {
        // Arrange
        sweetService = new SweetService(sweetRepository);

        Sweet invalidSweet = new Sweet();
        invalidSweet.setName("Halwa");
        invalidSweet.setPrice(-50.0);  // Invalid!
        invalidSweet.setStock(30);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sweetService.createSweet(invalidSweet);
        });

        assertTrue(exception.getMessage().contains("Price must be greater than 0"));
    }

    @Test
    void createSweet_shouldThrowExceptionWhenStockIsNegative() {
        // Arrange
        sweetService = new SweetService(sweetRepository);

        Sweet invalidSweet = new Sweet();
        invalidSweet.setName("Peda");
        invalidSweet.setPrice(180.0);
        invalidSweet.setStock(-10);  // Invalid!

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            sweetService.createSweet(invalidSweet);
        });

        assertTrue(exception.getMessage().contains("Stock cannot be negative"));
    }
}
