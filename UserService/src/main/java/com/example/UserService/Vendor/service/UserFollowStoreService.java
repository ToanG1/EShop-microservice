package com.example.UserService.Vendor.service;

import com.example.UserService.Vendor.dto.UserFollowStore.Request.FindFollowStoreRequest;
import com.example.UserService.Vendor.dto.UserFollowStore.Response.FollowStoreResponse;
import com.example.UserService.Vendor.dto.UserFollowStore.Response.UserDto;
import com.example.UserService.model.UserFollowStore;
import com.example.UserService.repository.UserFollowStoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("VendorFollowStoreService")
@RequiredArgsConstructor
@Slf4j
public class UserFollowStoreService {

    private final UserFollowStoreRepository ufsRepository;

    public FollowStoreResponse findAll(FindFollowStoreRequest ffsRequest) {
        return FollowStoreResponse.builder()
                .userDtosList(
                        ufsRepository.findAllByStoreId
                                        (ffsRequest.getStoreId(), PageRequest.of(ffsRequest.getCurrentPage(), ffsRequest.getSize()))
                                .stream().map(this::mapToUserDto).toList())
                .currentPage(ffsRequest.getCurrentPage())
                .size(ffsRequest.getSize())
                .totalPage(Math.round(ufsRepository.countByStoreId(ffsRequest.getStoreId()) / (ffsRequest.getSize())))
                .build();
    }

    private UserDto mapToUserDto(UserFollowStore userFollowStore) {
        return UserDto.builder()
                .uid(userFollowStore.getUser().getUid())
                .name(userFollowStore.getUser().getName())
                .build();
    }
}
