package com.sheppo.OrderService.common.service;

import com.sheppo.OrderService.common.dto.Store.Response.StoreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {

    private final WebClient.Builder webClient;

    public boolean isStoreValid(Long storeId){
        Boolean isValid = webClient.build().get()
                .uri("http://UserService/api/user/user/store/isValid",
                        uriBuilder -> uriBuilder.queryParam("id", storeId).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return isValid;
    }
    public StoreDto findStoreById(Long storeId){
        StoreDto store = webClient.build().get()
                .uri("http://UserService/api/user/user/store",
                        uriBuilder -> uriBuilder.queryParam("id", storeId).build())
                .retrieve()
                .bodyToMono(StoreDto.class)
                .block();
        return store;
    }
}
