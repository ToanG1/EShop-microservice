package com.sheppo.OrderService.User.controller;

import com.sheppo.OrderService.User.service.CartBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order/user/cartBox")
@RequiredArgsConstructor
public class CartBoxController {

    private final CartBoxService cartBoxService;

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteCartBox(@RequestParam Long id){
        cartBoxService.deleteCartBox(id);
    }
}
