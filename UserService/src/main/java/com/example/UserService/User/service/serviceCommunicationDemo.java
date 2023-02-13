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

    private final WebClient webClient;

    public void findUser() {
        String content = webClient.get()
                .uri("http://localhost:8080/api/user/product/test")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(content);
    }
    public void findList(){
        List<String> stringList = new ArrayList<>();
        User[] response = webClient.get()
                .uri("url", uriBuilder -> uriBuilder.queryParam("key", stringList).build())
                .retrieve()
                .bodyToMono(User[].class)
                .block();
    }
}
