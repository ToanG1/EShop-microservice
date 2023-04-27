package com.sheppo.OrderService.User.controller;

import com.sheppo.OrderService.User.dto.CartItem.Request.CreateCartItemRequest;
import com.sheppo.OrderService.User.dto.CartItem.Request.UpdateCartItemRequest;
import com.sheppo.OrderService.User.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("UserCartItemController")
@RequestMapping("api/order/user/cartItem")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCartItem(@RequestBody CreateCartItemRequest createCartItemRequest){
        cartItemService.createCartItem(createCartItemRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCartItem(@RequestBody UpdateCartItemRequest updateCartItemRequest){
        cartItemService.updateCartItem(updateCartItemRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCartItem(@PathVariable Long id, @RequestParam String uid){
        cartItemService.deleteCartItem(id, uid);
    }
}
