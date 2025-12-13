package com.harshita.sweetshop.controller;

import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.service.SweetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sweets")
public class SweetController {

    private final SweetService sweetService;

    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    @GetMapping
    public ResponseEntity<List<Sweet>> getAllSweets() {
        List<Sweet> sweets = sweetService.getAllSweets();
        return ResponseEntity.ok(sweets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sweet> getSweetById(@PathVariable Long id) {
        Sweet sweet = sweetService.getSweetById(id);
        return ResponseEntity.ok(sweet);
    }

    @PostMapping
    public ResponseEntity<Sweet> createSweet(@RequestBody Sweet sweet) {
        Sweet createdSweet = sweetService.createSweet(sweet);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSweet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sweet> updateSweet(@PathVariable Long id, @RequestBody Sweet sweet) {
        Sweet updatedSweet = sweetService.updateSweet(id, sweet);
        return ResponseEntity.ok(updatedSweet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable Long id) {
        sweetService.deleteSweet(id);
        return ResponseEntity.noContent().build();
    }

}
