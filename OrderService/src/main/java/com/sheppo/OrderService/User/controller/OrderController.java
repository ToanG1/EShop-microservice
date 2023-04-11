package com.sheppo.OrderService.User.controller;

import com.sheppo.OrderService.User.dto.Order.Request.DeleteOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Request.FindOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Request.PlaceOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Request.UpdateOrderRequest;
import com.sheppo.OrderService.User.dto.Order.Response.OrderResponse;
import com.sheppo.OrderService.User.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController("UserOrderController")
@RequestMapping("api/order/user/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name="inventory", fallbackMethod = "getOrderFallBack")
    @TimeLimiter(name="inventory")
    @Retry(name="inventory")
    public CompletableFuture<OrderResponse> findOrder(@RequestBody FindOrderRequest findOrderRequest){
        return CompletableFuture.supplyAsync(() -> orderService.findOrder(findOrderRequest));
    }
    public CompletableFuture<OrderResponse> getOrderFallBack(FindOrderRequest findOrderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(() -> orderService.findOrder(findOrderRequest));
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
