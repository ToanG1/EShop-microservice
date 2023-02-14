package com.sheppo.OrderService.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient;

    public Boolean isUserValid(String uid){
        Boolean isValid = webClient.get()
                .uri("http://localhost:8081/api/user/user/isValid", uriBuilder -> uriBuilder.queryParam("uid", uid).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return isValid;
    }
}
