package com.harshita.sweetshop.controller;

import com.harshita.sweetshop.model.Order;
import com.harshita.sweetshop.model.OrderItem;
import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order placeOrder(@RequestBody List<OrderItem> items) {
        return orderService.placeOrder(items);
    }

    @GetMapping("/{id}")
    public Sweet getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }
}
