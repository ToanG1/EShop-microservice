package com.sheppo.ApiGateway.common.service;

import com.sheppo.ApiGateway.common.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final WebClient.Builder webClient;

    public User findUserByUsername(String username){
        User user = webClient.build().get()
                .uri("http://localhost:8080/api/user/user/user/username", uriBuilder ->
                        uriBuilder.queryParam("username", username).build())
                .retrieve()
                .bodyToMono(User.class)
                .block();
        return user;
    }
}
