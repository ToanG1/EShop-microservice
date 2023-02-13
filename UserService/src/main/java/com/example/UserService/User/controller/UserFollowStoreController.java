package com.example.UserService.User.controller;

import com.example.UserService.User.dto.UserFollowStore.Request.FindFollowStoreRequest;
import com.example.UserService.User.dto.UserFollowStore.Request.FollowStoreRequest;
import com.example.UserService.User.dto.UserFollowStore.Response.FollowStoreResponse;
import com.example.UserService.User.dto.UserFollowStore.Response.StoreDto;
import com.example.UserService.User.service.UserFollowStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("UserFollowStoreController")
@RequestMapping("api/user/followStore")
@RequiredArgsConstructor
public class UserFollowStoreController {

    private final UserFollowStoreService ufsService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void followStore(@RequestBody FollowStoreRequest followStoreRequest){
        ufsService.followStore(followStoreRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public FollowStoreResponse findAll(@RequestBody FindFollowStoreRequest ffsRequest){
        return ufsService.findAll(ffsRequest);
    }
}
