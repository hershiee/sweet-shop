package com.harshita.sweetshop.controller;

import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.repository.SweetRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class SweetController {
    private final SweetRepository sweetRepository;

    public SweetController(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    @GetMapping
    public List<Sweet> getAllSweets() {
        return sweetRepository.findAll();
    }
}
