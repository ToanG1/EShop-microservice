package com.sheppo.OrderService.repository;

import com.sheppo.OrderService.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUid(String uid);
    boolean existsByUid(String uid);
}
