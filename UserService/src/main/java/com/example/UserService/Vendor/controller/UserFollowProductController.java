package com.example.UserService.Vendor.controller;

import com.example.UserService.Vendor.dto.UserFollowProduct.Request.FindFollowProductRequest;
import com.example.UserService.Vendor.dto.UserFollowProduct.Response.FollowProductResponse;
import com.example.UserService.Vendor.service.UserFollowProductservice;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("VendorFollowProductController")
@RequestMapping("api/vendor/followProduct")
@RequiredArgsConstructor
public class UserFollowProductController {

    private final UserFollowProductservice ufpService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public FollowProductResponse findAllByProductId(@RequestBody FindFollowProductRequest ffpRequst){
        return ufpService.findAllByProductId(ffpRequst);
    }
}
