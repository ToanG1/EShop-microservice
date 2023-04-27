package com.sheppo.OrderService.User.controller;

import com.sheppo.OrderService.User.dto.Shipping.Response.ShippingDto;
import com.sheppo.OrderService.User.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("UserShippingController")
@RequestMapping("api/order/user/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShippingDto> findAllShipping (){
        return shippingService.findAll();
    }
}
