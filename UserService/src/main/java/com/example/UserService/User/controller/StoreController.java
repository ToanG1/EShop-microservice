package com.example.UserService.User.controller;

import com.example.UserService.User.service.Storeservice;
import com.example.UserService.User.dto.Store.Response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("UserStoreController")
@RequestMapping("api/user/store")
@RequiredArgsConstructor
public class StoreController {

    private final Storeservice storeservice;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StoreResponse findStore(@RequestParam Long id){
        return storeservice.findStore(id);
    }
}
