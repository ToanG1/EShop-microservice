package com.sheppo.OrderService.common.service;

import com.sheppo.OrderService.common.dto.Response.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final WebClient webClient;

    public List<ProductDto> findProductList(List<String> productIdList){
        ProductDto[] productDtoList = webClient.get()
                .uri("http://localhost:8080/api/user/product/listProduct",
                        uriBuilder -> uriBuilder.queryParam("productIdlist", productIdList).build())
                .retrieve()
                .bodyToMono(ProductDto[].class)
                .block();
        return Arrays.stream(productDtoList).toList();
    }
    public ProductDto findProduct(String productId){
        List<String> productIdList = new ArrayList<>();
        productIdList.add(productId);
        return findProductList(productIdList).get(0);
    }

}
