package com.harshita.sweetshop.service;

import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.repository.SweetRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SweetService {

    private final SweetRepository sweetRepository;

    public SweetService(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }

    @Transactional
    public Sweet createSweet(Sweet sweet) {
        // Validate sweet data
        validateSweet(sweet);

        // Save and return
        return sweetRepository.save(sweet);
    }

    public Sweet getSweetById(Long id) {
        return sweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sweet not found with id: " + id));
    }

    private void validateSweet(Sweet sweet) {
        if (sweet.getName() == null || sweet.getName().trim().isEmpty()) {
            throw new RuntimeException("Sweet name is required");
        }

        if (sweet.getPrice() == null || sweet.getPrice() <= 0) {
            throw new RuntimeException("Price must be greater than 0");
        }

        if (sweet.getStock() == null || sweet.getStock() < 0) {
            throw new RuntimeException("Stock cannot be negative");
        }
    }


}
