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

    public void deleteCartBox(Long id, String uid){
        cartBoxRepository.findById(id).ifPresentOrElse(
                cartBox -> {
                    if(cartBox.getCart().getUid().equals(uid)){
                        cartItemRepository.deleteAll(cartBox.getCartItemList());
                        cartBoxRepository.delete(cartBox);
                        log.info("CartBox {} is removed successfully", id);
                    }
                    else log.info("CartBox {} is not available", id);
                },
                () -> log.info("CartBox {} is not available", id)
        );
    }
}
