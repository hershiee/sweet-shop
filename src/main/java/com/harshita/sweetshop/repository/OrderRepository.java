package com.harshita.sweetshop.repository;

import com.harshita.sweetshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Can add custom queries later
}