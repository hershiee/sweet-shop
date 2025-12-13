package com.harshita.sweetshop.service;

import com.harshita.sweetshop.model.Order;
import com.harshita.sweetshop.model.OrderItem;
import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.repository.OrderRepository;
import com.harshita.sweetshop.repository.SweetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final SweetRepository sweetRepository;
    private final OrderRepository orderRepository;

    public OrderService(SweetRepository sweetRepository,
                        OrderRepository orderRepository) {
        this.sweetRepository = sweetRepository;
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(List<OrderItem> items) {

        //TODO 1: validate stock for all items
        //TODO 2: calculate total amount
        //TODO 3: reduce stock
        //TODO 4: save order

        return null; // temporary
    }

    public Sweet getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
