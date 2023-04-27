package com.sheppo.OrderService.User.controller;

import com.sheppo.OrderService.User.service.CartBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("UserCartBoxController")
@RequestMapping("api/order/user/cartBox")
@RequiredArgsConstructor
public class CartBoxController {

    private final CartBoxService cartBoxService;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCartBox(@PathVariable Long id, @RequestParam String uid){
        cartBoxService.deleteCartBox(id, uid);
    }
}
