package com.sheppo.OrderService.User.service;

import com.sheppo.OrderService.repository.CartBoxRepository;
import com.sheppo.OrderService.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartBoxService {

    private final CartItemRepository cartItemRepository;
    private final CartBoxRepository cartBoxRepository;

    public void deleteCartBox(Long id){
        cartBoxRepository.findById(id).ifPresentOrElse(
                cartBox -> {
                    cartItemRepository.deleteAll(cartBox.getCartItemList());
                    cartBoxRepository.delete(cartBox);
                    log.info("CartBox {} is removed successfully", id);
                },
                () -> log.info("CartBox {} is not available", id)
        );
    }
}
