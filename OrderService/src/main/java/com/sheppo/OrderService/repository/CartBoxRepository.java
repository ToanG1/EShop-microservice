package com.sheppo.OrderService.repository;

import com.sheppo.OrderService.model.CartBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartBoxRepository extends JpaRepository<CartBox, Long> {
    boolean existsByCartIdAndStoreId(long cartId, long storeId);
    CartBox findByCartIdAndStoreId(long cartId, long storeId);
    int countById(long cartBoxId);

}