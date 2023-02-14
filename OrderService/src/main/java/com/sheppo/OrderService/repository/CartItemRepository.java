package com.sheppo.OrderService.repository;

import com.sheppo.OrderService.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartBoxIdAndProductId(Long cartBoxId, String productId);
    boolean existsByCartBoxIdAndProductId(Long cartBoxId, String productId);
}
