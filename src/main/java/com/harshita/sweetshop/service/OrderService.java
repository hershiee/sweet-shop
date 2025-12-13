package com.harshita.sweetshop.service;

import com.harshita.sweetshop.model.Order;
import com.harshita.sweetshop.model.OrderItem;
import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.repository.OrderRepository;
import com.harshita.sweetshop.repository.SweetRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void placeOrderForOneSweet(Long sweetId, int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
        // Validate quantity
        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        // NEW: Check if enough stock available
        if (sweet.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " +
                    sweet.getStock() + ", Requested: " + quantity);
        }

        //Reduce Stock
        int newStock = sweet.getStock() - quantity;
        sweet.setStock(newStock);

        //Save updated sweet
        sweetRepository.save(sweet);
    }

    public Order placeOrder(List<OrderItem> items) {

        //TODO 1: validate stock for all items
        //TODO 2: calculate total amount
        //TODO 3: reduce stock
        //TODO 4: save order

        return null; // temporary
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
