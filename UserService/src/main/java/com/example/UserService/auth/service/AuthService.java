package com.example.UserService.auth.service;

import com.example.UserService.auth.dto.request.LoginRequest;
import com.example.UserService.auth.dto.response.LoginResponse;
import com.example.UserService.auth.dto.response.UserResponse;
import com.example.UserService.jwt.JwtTokenProvider;
import com.example.UserService.model.User;
import com.example.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("LoginService")
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .avatar(user.getAvatar())
                .point(user.getPoint())
                .uid(user.getUid())
                .role(user.getRole())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public LoginResponse authenticate(LoginRequest request) {
        Optional<User> user = this.userRepository.findByName(request.getUsername());
        if (user.isPresent()) {
            if (user.get().getPassword().equals(request.getPassword())) {
                String accessToken = this.jwtTokenProvider.generateToken(user.get());
                log.info("User " + request.getUsername() + " has just logged in, generated jwt token is " + accessToken);
                return new LoginResponse(mapToUserResponse(user.get()), accessToken, "Oke");
            }
            else return new LoginResponse(null, null, "Name or Password is wrong");
        }
        else return new LoginResponse(null, null, "Name or Password is wrong");
    }

    public UserResponse findUserByUsername(String username) {
        Optional<User> user = userRepository.findByName(username);
        if (user.isPresent())
            return mapToUserResponse(user.get());
        else return null;
    }
}
