package com.sheppo.OrderService.User.controller;

import com.sheppo.OrderService.User.dto.Order.Request.PlaceOrderRequest;
import com.sheppo.OrderService.User.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order/user/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest){
        return orderService.placeOrder(placeOrderRequest);
    }
}
