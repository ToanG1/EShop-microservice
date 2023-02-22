package com.example.UserService.Vendor.controller;

import com.example.UserService.Vendor.dto.UserFollowProduct.Response.FollowProductResponse;
import com.example.UserService.Vendor.dto.UserFollowStore.Request.FindFollowStoreRequest;
import com.example.UserService.Vendor.dto.UserFollowStore.Response.FollowStoreResponse;
import com.example.UserService.Vendor.service.UserFollowStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("VendorFollowStoreController")
@RequestMapping("api/user/vendor/followStore")
@RequiredArgsConstructor
public class UserFollowStoreController {

    private final UserFollowStoreService ufsService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public FollowStoreResponse findAll(@RequestBody FindFollowStoreRequest ffsRequest){
        return ufsService.findAll(ffsRequest);
    }
}
