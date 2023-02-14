package com.sheppo.OrderService.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {

    private final WebClient webClient;

    public boolean isStoreValid(Long storeId){
        Boolean isValid = webClient.get()
                .uri("http://localhost:8081/api/user/store/isValid",
                        uriBuilder -> uriBuilder.queryParam("id", storeId).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return isValid;
    }
}
