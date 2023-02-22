package com.sheppo.OrderService.common.service;

import com.sheppo.OrderService.common.dto.Product.Response.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final WebClient.Builder webClient;

    public List<ProductDto> findProductList(List<String> productIdList){
        ProductDto[] productDtoList = webClient.build().get()
                .uri("http://ProductService/api/product/user/product/listProduct",
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

    public void minusQuantityAfterOrder(String productId, int quantity){
        webClient.build().put().uri("http://ProductService/api/product/vendor/product/afterOrder",
                uriBuilder -> uriBuilder.queryParam("productId", productId).queryParam("quantity", quantity).build())
                .retrieve()
                .bodyToMono(void.class)
                .block();
    }
}
