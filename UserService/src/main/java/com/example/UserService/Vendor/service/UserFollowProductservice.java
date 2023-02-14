package com.example.UserService.Vendor.service;

import com.example.UserService.Vendor.dto.UserFollowProduct.Request.FindFollowProductRequest;
import com.example.UserService.Vendor.dto.UserFollowProduct.Response.FollowProductResponse;
import com.example.UserService.Vendor.dto.UserFollowProduct.Response.UserDto;
import com.example.UserService.model.UserFollowProduct;
import com.example.UserService.repository.UserFollowProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFollowProductservice {

    private final UserFollowProductRepository ufpRepository;

    public FollowProductResponse findAllByProductId(FindFollowProductRequest ffpRequest) {
        return FollowProductResponse.builder()
                .userDtosList(ufpRepository.findAllByProductId(ffpRequest.getProductId(),
                        PageRequest.of(ffpRequest.getCurrentPage(), ffpRequest.getSize()))
                        .stream().map(this::mapToUserDto).toList())
                .currentPage(ffpRequest.getCurrentPage())
                .size(ffpRequest.getSize())
                .totalPage(Math.round(ufpRepository.countByProductId(ffpRequest.getProductId()) / ffpRequest.getSize()))
                .build();
    }

    private UserDto mapToUserDto(UserFollowProduct userFollowProduct) {
        return UserDto.builder()
                .uid(userFollowProduct.getUser().getUid())
                .name(userFollowProduct.getUser().getName())
                .build();
    }

}
