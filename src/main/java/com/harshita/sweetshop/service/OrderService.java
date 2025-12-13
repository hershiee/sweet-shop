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
    public Order placeOrderAndSave(Long sweetId, int quantity) {
        // Validate quantity
        validateQuantity(quantity);

        // Find the sweet
        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        // Check if enough stock available
        validateStock(sweet, quantity);

        // Reduce stock
        sweet.setStock(sweet.getStock() - quantity);
        sweetRepository.save(sweet);

        // Calculate total
        Double total = sweet.getPrice() * quantity;

        // Create and save order
        Order order = new Order();
        order.setSweetId(sweet.getId());
        order.setSweetName(sweet.getName());
        order.setQuantity(quantity);
        order.setTotalAmount(total);
        order.setStatus("COMPLETED");

        return orderRepository.save(order);
    }

    // Keep validation methods
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

    public Order placeOrder(List<OrderItem> items) {

        //TODO 1: validate stock for all items
        //TODO 2: calculate total amount
        //TODO 3: reduce stock
        //TODO 4: save order

        return null; // temporary
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }


}
