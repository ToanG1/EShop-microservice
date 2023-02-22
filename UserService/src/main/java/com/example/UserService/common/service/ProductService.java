package com.example.UserService.common.service;

import com.example.UserService.model.UserFollowProduct;
import com.example.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final WebClient.Builder webClient;

    public boolean isProductAvailable(String id){
        Map<String, String> bodyMap = new HashMap();
        bodyMap.put("productId",id);

        int countProduct = webClient.build().post()
                .uri("http://ProductService/api/product/user/product")
                .body(BodyInserters.fromValue(bodyMap))
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
        if (countProduct > 0) return true;
        else return false;
    }

}
