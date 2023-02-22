package com.sheppo.OrderService.common.service;

import com.sheppo.OrderService.common.dto.Address.Response.AddressDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final WebClient.Builder webClient;

    public Boolean isUserValid(String uid){
        Boolean isValid = webClient.build().get()
                .uri("http://UserService/api/user/user/user/isUserValid", uriBuilder -> uriBuilder.queryParam("uid", uid).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return isValid;
    }

    public AddressDto isUseAndAddressValid(String uid, Long addressId){
        AddressDto addressDto = webClient.build().get()
                .uri("http://UserService/api/user/user/user/isUserAddressValid", uriBuilder -> uriBuilder.
                        queryParam("uid", uid).queryParam("addressId", addressId).build())
                .retrieve()
                .bodyToMono(AddressDto.class)
                .block();
        return addressDto;
    }
}
