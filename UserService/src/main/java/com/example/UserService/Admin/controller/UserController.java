package com.example.UserService.Admin.controller;

import com.example.UserService.Admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("AdminUserController")
@RequestMapping("api/user/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam String uid){ userService.deleteUser(uid);}
}
