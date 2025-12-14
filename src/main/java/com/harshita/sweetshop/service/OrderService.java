package com.harshita.sweetshop.service;

import com.harshita.sweetshop.exception.ResourceNotFoundException;
import com.harshita.sweetshop.exception.ValidationException;
import com.harshita.sweetshop.exception.InsufficientStockException;
import com.harshita.sweetshop.model.Order;
import com.harshita.sweetshop.model.Sweet;
import com.harshita.sweetshop.repository.OrderRepository;
import com.harshita.sweetshop.repository.SweetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        validateQuantity(quantity);

        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Sweet not found with id: " + sweetId));

        validateStock(sweet, quantity);

        sweet.setStock(sweet.getStock() - quantity);
        sweetRepository.save(sweet);

        Double total = sweet.getPrice() * quantity;

        Order order = new Order();
        order.setSweetId(sweet.getId());
        order.setSweetName(sweet.getName());
        order.setQuantity(quantity);
        order.setTotalAmount(total);
        order.setStatus("COMPLETED");

        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new ValidationException("Quantity must be greater than 0");
        }
    }

    private void validateStock(Sweet sweet, int quantity) {
        if (sweet.getStock() < quantity) {
            throw new InsufficientStockException(
                    String.format("Insufficient stock for %s. Available: %d, Requested: %d",
                            sweet.getName(), sweet.getStock(), quantity)
            );
        }
    }
}