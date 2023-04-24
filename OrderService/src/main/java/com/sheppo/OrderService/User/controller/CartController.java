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

    @GetMapping("/{uid}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse findCart(@PathVariable String uid,
                                 @RequestParam(defaultValue = "0") Integer currentPage,
                                 @RequestParam(defaultValue = "10") Integer size){
        FindCartRequest findCartRequest = new FindCartRequest(uid, currentPage, size);
        return cartService.findCart(findCartRequest);
    }
}
