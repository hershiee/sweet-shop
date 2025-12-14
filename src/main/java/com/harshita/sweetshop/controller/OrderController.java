package com.harshita.sweetshop.controller;

import com.harshita.sweetshop.model.Order;
import com.harshita.sweetshop.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Map<String, Object> orderRequest) {
        Long sweetId = Long.valueOf(orderRequest.get("sweetId").toString());
        Integer quantity = Integer.valueOf(orderRequest.get("quantity").toString());

        Order order = orderService.placeOrderAndSave(sweetId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}