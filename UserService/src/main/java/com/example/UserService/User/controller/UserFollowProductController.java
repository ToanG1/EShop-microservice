package com.example.UserService.User.controller;

import com.example.UserService.User.dto.UserFollowProduct.Request.FindFollowProductRequest;
import com.example.UserService.User.dto.UserFollowProduct.Request.FollowProductRequest;
import com.example.UserService.User.dto.UserFollowProduct.Response.FollowProductResponse;
import com.example.UserService.User.service.UserFollowProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController("UserFollowProductController")
@RequestMapping("api/user/user/followProduct")
@RequiredArgsConstructor
public class UserFollowProductController {

    private final UserFollowProductService ufpService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void followProduct(@RequestBody FollowProductRequest followProductRequest){
        ufpService.followProduct(followProductRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FollowProductResponse findAll(@PathVariable Long id, @RequestParam(defaultValue = "10") Integer currentPage,
                                         @RequestParam(defaultValue = "0") Integer size){
        FindFollowProductRequest ffpRequest = new FindFollowProductRequest(id,
                currentPage >= 0 ? currentPage : 0, size > 0 ? size : 10);
        return ufpService.findAll(ffpRequest);
    }
}
