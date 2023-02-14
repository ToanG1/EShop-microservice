package com.sheppo.OrderService.User.controller;

import com.sheppo.OrderService.User.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
}
