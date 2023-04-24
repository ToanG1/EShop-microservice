package com.example.UserService.User.controller;

import com.example.UserService.User.service.StoreService;
import com.example.UserService.User.dto.Store.Response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("UserStoreController")
@RequestMapping("api/user/user/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeservice;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public StoreResponse findStore(@RequestParam Long id){
        return storeservice.findStore(id);
    }

    @GetMapping("/isValid")
    @ResponseStatus(HttpStatus.OK)
    public boolean isStoreValid(@RequestParam Long id){
        return storeservice.isStoreValid(id);
    }
}
