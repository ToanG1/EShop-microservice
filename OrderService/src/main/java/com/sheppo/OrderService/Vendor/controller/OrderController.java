package com.sheppo.OrderService.Vendor.controller;

import com.sheppo.OrderService.Vendor.dto.Order.Request.FindOrderRequest;
import com.sheppo.OrderService.Vendor.dto.Order.Response.OrderResponse;
import com.sheppo.OrderService.Vendor.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("VendorOrderController")
@RequestMapping("api/order/vendor/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findOrder(@RequestBody FindOrderRequest findOrderRequest){
        return orderService.findOrder(findOrderRequest);
    }
}
