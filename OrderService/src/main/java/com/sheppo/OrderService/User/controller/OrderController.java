package com.sheppo.OrderService.User.controller;

import com.sheppo.OrderService.User.dto.Order.Request.DeleteOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Request.FindOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Request.PlaceOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Request.UpdateOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Response.OrderResponse;
import com.sheppo.OrderService.User.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("UserOrderController")
@RequestMapping("api/order/user/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findOrder(@RequestBody FindOrderRequest findOrderRequest){
        return orderService.findOrder(findOrderRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest){
        return orderService.placeOrder(placeOrderRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public String updateOrder(@RequestBody UpdateOrderRequest updateOrderRequest){
        return orderService.updateOrder(updateOrderRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void cancelOrder(@RequestBody DeleteOrderRequest deleteOrderRequest){
        orderService.cancelOrder(deleteOrderRequest);
    }
}
