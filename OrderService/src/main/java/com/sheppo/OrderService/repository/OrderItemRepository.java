package com.sheppo.OrderService.repository;

import com.sheppo.OrderService.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
