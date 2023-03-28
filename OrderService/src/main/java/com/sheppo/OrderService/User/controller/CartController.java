package com.sheppo.OrderService.User.controller;

import com.sheppo.OrderService.User.dto.Cart.Request.FindCartRequest;
import com.sheppo.OrderService.User.dto.Cart.Response.CartResponse;
import com.sheppo.OrderService.User.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("UserCartController")
@RequestMapping("api/order/user/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponse findCart(@RequestBody FindCartRequest findCartRequest){
        return cartService.findCart(findCartRequest);
    }
}
