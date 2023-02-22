package com.example.UserService.User.service;

import com.example.UserService.User.dto.UserFollowProduct.Request.FindFollowProductRequest;
import com.example.UserService.User.dto.UserFollowProduct.Request.FollowProductRequest;
import com.example.UserService.User.dto.UserFollowProduct.Response.FollowProductResponse;
import com.example.UserService.User.dto.UserFollowProduct.Response.ProductDto;
import com.example.UserService.common.service.ProductService;
import com.example.UserService.model.UserFollowProduct;
import com.example.UserService.repository.UserFollowProductRepository;
import com.example.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service("UserFollowProductService")
@RequiredArgsConstructor
@Slf4j
public class UserFollowProductService {

    private final WebClient.Builder webClient;

    private final ProductService productService;

    private final UserRepository userRepository;

    private final UserFollowProductRepository ufpRepository;

    public void followProduct(FollowProductRequest followProductRequest) {
        if (followProductRequest.getProductId() != null && followProductRequest.getUid() != null)
            ufpRepository.findByUserUidAndProductId(followProductRequest.getUid(), followProductRequest.getProductId()).ifPresentOrElse(
                    ufp -> {
                        ufpRepository.delete(ufp);
                        log.info("User {} unfollow product {} successfully", followProductRequest.getUid(), followProductRequest.getProductId());
                    },
                    () -> {
                        //check product available
                        if (productService.isProductAvailable(followProductRequest.getProductId())) {
                            userRepository.findByUid(followProductRequest.getUid()).ifPresentOrElse(
                                    user -> {
                                        UserFollowProduct ufp = UserFollowProduct.builder()
                                                .user(user)
                                                .productId(followProductRequest.getProductId())
                                                .createAt(new Date())
                                                .build();
                                        ufpRepository.save(ufp);
                                        log.info("User {} follow product {} successfully",
                                                followProductRequest.getUid(), followProductRequest.getProductId());
                                    },
                                    () -> log.info("User {} is not available", followProductRequest.getUid())
                            );

                        } else {
                            log.info("Product {} is not available", followProductRequest.getProductId());
                        }
                    }
            );
    }

    public FollowProductResponse findAll(FindFollowProductRequest ffpRequest) {
        List<String> stringList = ufpRepository.findAllByUserUid
                        (ffpRequest.getUid(), PageRequest.of(ffpRequest.getCurrentPage(), ffpRequest.getSize()))
                .stream().map(userFollowProduct -> userFollowProduct.getProductId()).toList();
        ProductDto[] response = webClient.build().get()
                .uri("http://ProductService/api/product/user/product/listProduct",
                        uriBuilder -> uriBuilder.queryParam("productIdlist", stringList).build())
                .retrieve()
                .bodyToMono(ProductDto[].class)
                .block();
        return FollowProductResponse.builder()
                .productDtoList(Arrays.stream(response).toList())
                .currentPage(ffpRequest.getCurrentPage())
                .size(ffpRequest.getSize())
                .totalPage((int) Math.ceil(ufpRepository.countByUserUid(ffpRequest.getUid()) / ffpRequest.getSize()))
                .build();
    }
}
