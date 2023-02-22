package com.example.UserService.User.service;

import com.example.UserService.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class serviceCommunicationDemo {

    private final WebClient.Builder webClient;

    public void findUser() {
        String content = webClient.build().get()
                .uri("http://ProductService/api/product/user/product/test")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(content);
    }
    public void findList(){
        List<String> stringList = new ArrayList<>();
        User[] response = webClient.build().get()
                .uri("url", uriBuilder -> uriBuilder.queryParam("key", stringList).build())
                .retrieve()
                .bodyToMono(User[].class)
                .block();
    }
}
