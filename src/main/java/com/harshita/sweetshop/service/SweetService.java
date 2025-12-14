package com.harshita.sweetshop.service;

import com.harshita.sweetshop.exception.ResourceNotFoundException;
import com.harshita.sweetshop.exception.ValidationException;
import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.repository.SweetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        validateSweet(sweet);
        return sweetRepository.save(sweet);
    }

    public Sweet getSweetById(Long id) {
        return sweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found with id: " + id));
    }

    @Transactional
    public Sweet updateSweet(Long id, Sweet updatedSweet) {
        Sweet existingSweet = sweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found with id: " + id));

        validateSweet(updatedSweet);

        existingSweet.setName(updatedSweet.getName());
        existingSweet.setPrice(updatedSweet.getPrice());
        existingSweet.setStock(updatedSweet.getStock());

        if (updatedSweet.getCategory() != null) {
            existingSweet.setCategory(updatedSweet.getCategory());
        }

        return sweetRepository.save(existingSweet);
    }

    @Transactional
    public void deleteSweet(Long id) {
        Sweet sweet = sweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found with id: " + id));

        sweetRepository.delete(sweet);
    }

    private void validateSweet(Sweet sweet) {
        if (sweet.getName() == null || sweet.getName().trim().isEmpty()) {
            throw new ValidationException("Sweet name is required");
        }

        if (sweet.getPrice() == null || sweet.getPrice() <= 0) {
            throw new ValidationException("Price must be greater than 0");
        }

        if (sweet.getStock() == null || sweet.getStock() < 0) {
            throw new ValidationException("Stock cannot be negative");
        }
    }
}