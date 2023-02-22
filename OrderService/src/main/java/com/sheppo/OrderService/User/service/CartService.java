package com.sheppo.OrderService.User.service;

import com.sheppo.OrderService.User.dto.Cart.Request.FindCartRequest;
import com.sheppo.OrderService.User.dto.Cart.Response.CartBoxDto;
import com.sheppo.OrderService.User.dto.Cart.Response.CartItemDto;
import com.sheppo.OrderService.User.dto.Cart.Response.CartResponse;
import com.sheppo.OrderService.common.dto.Product.Response.ProductDto;
import com.sheppo.OrderService.common.service.ProductService;
import com.sheppo.OrderService.common.service.StoreService;
import com.sheppo.OrderService.model.Cart;
import com.sheppo.OrderService.model.CartBox;
import com.sheppo.OrderService.model.CartItem;
import com.sheppo.OrderService.repository.CartBoxRepository;
import com.sheppo.OrderService.repository.CartItemRepository;
import com.sheppo.OrderService.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;

    private final StoreService storeService;

    private final CartRepository cartRepository;

    private final CartBoxRepository cartBoxRepository;

    private final CartItemRepository cartItemRepository;

    public CartResponse findCart(FindCartRequest findCartRequest) {
        Optional<Cart> cart = cartRepository.findByUid(findCartRequest.getUid());
        if (cart.isPresent()) {
            List<CartBox> cartBoxList = cartBoxRepository.findAllByCartOrderByUpdateAtDesc(cart.get());
            List<CartBoxDto> cartBoxDtoList = new ArrayList<>();
            AtomicInteger countCartItem = new AtomicInteger();
            cartBoxList.forEach(cartBox -> {
                List<CartItem> cartItems = new ArrayList<>();
                cartBox.getCartItemList().forEach(cartItem -> {
                    if (countCartItem.get() <= (findCartRequest.getCurrentPage() + 1) * findCartRequest.getSize()) {
                        cartItems.add(cartItem);
                        countCartItem.getAndIncrement();
                    }
                });
                cartBoxDtoList.add(new CartBoxDto(cartBox.getId(),mapToCartItemDtoList(cartItems), storeService.findStoreById(cartBox.getStoreId())));
            });
            return CartResponse.builder()
                    .id(cart.get().getId())
                    .cartBoxDtoList(cartBoxDtoList)
                    .currentPage(findCartRequest.getCurrentPage())
                    .totalPage((int) Math.ceil((float) cartItemRepository.countByCartBox_Cart(cart.get()) / findCartRequest.getSize()))
                    .size(findCartRequest.getSize())
                    .build();
        } else log.info("User {} does not have cart", findCartRequest.getUid());
        return null;
    }

    private List<CartItemDto> mapToCartItemDtoList(List<CartItem> cartItems) {
        List<ProductDto> productDtoList = productService.findProductList(cartItems.stream().map(CartItem::getProductId).toList());
        List<CartItemDto> cartItemDtos = cartItems.stream().map(cartItem -> {
            ProductDto productDto = productDtoList.stream().
                    filter(productDto1 -> productDto1.getId().equals(cartItem.getProductId())).findFirst().get();
            return CartItemDto.builder()
                    .id(cartItem.getId())
                    .productDto(productDto)
                    .quantity(cartItem.getQuantity())
                    .build();
        }).toList();
        return cartItemDtos;
    }

}
