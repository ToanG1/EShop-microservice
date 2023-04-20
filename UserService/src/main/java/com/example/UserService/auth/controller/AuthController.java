package com.example.UserService.auth.controller;

import com.example.UserService.auth.dto.response.UserResponse;
import com.example.UserService.auth.dto.request.LoginRequest;
import com.example.UserService.auth.dto.response.LoginResponse;
import com.example.UserService.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("LoginController")
@RequestMapping("api/user/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return authService.authenticate(request);
    }

    @GetMapping("/username")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findUserByUsername(@RequestParam String username){
        return authService.findUserByUsername(username);
    }
}
