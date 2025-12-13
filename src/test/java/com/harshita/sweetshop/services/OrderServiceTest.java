package com.harshita.sweetshop.services;

import com.harshita.sweetshop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    void calculateTotal_shouldReturnCorrectSum() {

    }

    @Test
    void placeOrder_shouldReduceStock() {

    }

    @Test
    void placeOrder_shouldThrowWhenStockNotEnough() {

    }

    @Test
    void getOrder_shouldReturnCorrectOrder() {

    }

    @Test
    void listSweets_shouldReturnAllSweets() {

    }



}
