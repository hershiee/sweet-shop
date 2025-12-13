package com.harshita.sweetshop.services;

import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.repository.SweetRepository;
import com.harshita.sweetshop.service.OrderService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //rolls back database changes after each test
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SweetRepository sweetRepository;


                     // Test 1: Stock reduction

    @Test
    void placeOrder_shouldReduceStock() {
        // Arrange - Create and save a sweet to database
        Sweet sweet = new Sweet();
        sweet.setName("Kaju Katli");
        sweet.setStock(10);
        sweet.setPrice(250.0);
        sweet = sweetRepository.save(sweet); // Save to DB and get ID

        Long sweetId = sweet.getId();

        // Act - Place order
        orderService.placeOrderForOneSweet(sweetId, 1);

        // Assert - Check stock was reduced
        Sweet updatedSweet = sweetRepository.findById(sweetId).get();
        assertEquals(9, updatedSweet.getStock()); // 10 - 1 = 9
    }



                    // Test 2: Insufficient stock throws exception

    @Test
    void placeOrder_shouldThrowExceptionWhenStockInsufficient() {
        // Arrange
        Sweet sweet = new Sweet();
        sweet.setName("Gulab Jamun");
        sweet.setStock(5);  // Only 5 in stock
        sweet.setPrice(150.0);
        sweet = sweetRepository.save(sweet);

        Long sweetId = sweet.getId();

        // Act & Assert - Try to order 10 (more than available)
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.placeOrderForOneSweet(sweetId, 10);
        });

        // Check the error message
        assertTrue(exception.getMessage().contains("Insufficient stock"));

        // Verify stock didn't change
        Sweet unchangedSweet = sweetRepository.findById(sweetId).get();
        assertEquals(5, unchangedSweet.getStock());
    }



                     // Test3: add failing tests for invalid quantity validation

    @Test
    void placeOrder_shouldThrowExceptionWhenQuantityIsZero() {
        // Arrange
        Sweet sweet = new Sweet();
        sweet.setName("Jalebi");
        sweet.setStock(20);
        sweet.setPrice(100.0);
        sweet = sweetRepository.save(sweet);

        Long sweetId = sweet.getId();

        // Act & Assert - Try to order 0 quantity
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.placeOrderForOneSweet(sweetId, 0);
        });

        assertTrue(exception.getMessage().contains("Quantity must be greater than 0"));
    }

    @Test
    void placeOrder_shouldThrowExceptionWhenQuantityIsNegative() {
        // Arrange
        Sweet sweet = new Sweet();
        sweet.setName("Ladoo");
        sweet.setStock(15);
        sweet.setPrice(80.0);
        sweet = sweetRepository.save(sweet);

        Long sweetId = sweet.getId();

        // Act & Assert - Try to order -5 quantity
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.placeOrderForOneSweet(sweetId, -5);
        });

        assertTrue(exception.getMessage().contains("Quantity must be greater than 0"));
    }


                        // Test4: test for total price calculation

    @Test
    void placeOrder_shouldCalculateTotalPrice() {
        // Arrange
        Sweet sweet = new Sweet();
        sweet.setName("Rasgulla");
        sweet.setStock(50);
        sweet.setPrice(120.0);  // Price per item
        sweet = sweetRepository.save(sweet);

        Long sweetId = sweet.getId();
        int quantity = 3;

        // Act
        Double totalPrice = orderService.placeOrderForOneSweetWithTotal(sweetId, quantity);

        // Assert
        assertEquals(360.0, totalPrice);  // 120 * 3 = 360
    }



}
