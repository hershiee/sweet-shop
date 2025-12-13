package com.harshita.sweetshop.service;

import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.repository.SweetRepository;
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
}
