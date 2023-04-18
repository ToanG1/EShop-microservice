package com.example.UserService.auth.controller;

import com.example.UserService.auth.dto.request.LoginRequest;
import com.example.UserService.auth.dto.response.LoginResponse;
import com.example.UserService.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController("LoginController")
@RequestMapping("api/user/auth/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest request){
        return loginService.authenticate(request);
    }
}
