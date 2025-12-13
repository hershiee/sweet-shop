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
        // Validate quantity
        validateQuantity(quantity);

        // Find the sweet
        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        // Check if enough stock available
        validateStock(sweet, quantity);

        // Reduce stock
        sweet.setStock(sweet.getStock() - quantity);

        // Save updated sweet
        sweetRepository.save(sweet);
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
    }

    private void validateStock(Sweet sweet, int quantity) {
        if (sweet.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " +
                    sweet.getStock() + ", Requested: " + quantity);
        }
    }


    @Transactional
    public Double placeOrderForOneSweetWithTotal(Long sweetId, int quantity) {
        // Validate quantity
        validateQuantity(quantity);

        // Find the sweet
        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        // Check if enough stock available
        validateStock(sweet, quantity);

        // Reduce stock
        sweet.setStock(sweet.getStock() - quantity);

        // Save updated sweet
        sweetRepository.save(sweet);

        // Calculate and return total
        return sweet.getPrice() * quantity;
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
