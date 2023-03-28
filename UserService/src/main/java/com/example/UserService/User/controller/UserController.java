package com.example.UserService.User.controller;

import com.example.UserService.User.dto.Address.Response.AddressDto;
import com.example.UserService.User.dto.User.Request.CreateUserRequest;
import com.example.UserService.User.dto.User.Request.UpdateUserRequest;
import com.example.UserService.User.dto.User.Response.UserResponse;
import com.example.UserService.User.service.AddressService;
import com.example.UserService.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("UserUserController")
@RequestMapping("api/user/user/user")
@RequiredArgsConstructor
public class UserController {

    private final AddressService addressService;

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody CreateUserRequest createUserRequest){
        userService.createUser(createUserRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findUser(@RequestParam String uid){
        return userService.findUser(uid);
    }

    @GetMapping("/isUserValid")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isUserValid(@RequestParam String uid){
        return userService.findUser(uid) != null ? true : false;
    }

    @GetMapping("/isUserAddressValid")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto isUseValidAndFindAddress(@RequestParam String uid, Long addressId){
        if(userService.findUser(uid) != null)
        return addressService.findByIdAndUid(addressId, uid);
        else return null;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody UpdateUserRequest updateUserRequest){
        userService.updateUser(updateUserRequest);
    }
}
