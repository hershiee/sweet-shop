package com.harshita.sweetshop.services;

import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.repository.SweetRepository;
import com.harshita.sweetshop.service.OrderService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional //rolls back database changes after each test
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SweetRepository sweetRepository;

    @Test
    void placeOrder_shouldReduceStock() {
        // Arrange - Create and save a sweet to database
        Sweet sweet = new Sweet();
        sweet.setName("Kaju Katli");
        sweet.setStock(10);
        sweet.setPrice(250.0);
        sweet = sweetRepository.save(sweet); // Save to DB and get ID

        Long sweetId = sweet.getId();

        // Act - Place order (this method doesn't exist yet - will fail!)
        orderService.placeOrderForOneSweet(sweetId, 1);

        // Assert - Check stock was reduced
        Sweet updatedSweet = sweetRepository.findById(sweetId).get();
        assertEquals(9, updatedSweet.getStock()); // 10 - 1 = 9
    }







}
