package com.example.UserService.User.service;

import com.example.UserService.User.dto.UserFollowStore.Request.FindFollowStoreRequest;
import com.example.UserService.User.dto.UserFollowStore.Request.FollowStoreRequest;
import com.example.UserService.User.dto.UserFollowStore.Response.FollowStoreResponse;
import com.example.UserService.User.dto.UserFollowStore.Response.StoreDto;
import com.example.UserService.model.UserFollowStore;
import com.example.UserService.repository.StoreRepository;
import com.example.UserService.repository.UserFollowStoreRepository;
import com.example.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("UserFollowStoreService")
@Slf4j
@RequiredArgsConstructor
public class UserFollowStoreService {

    private final UserRepository userRepository;

    private final StoreRepository storeRepository;

    private final UserFollowStoreRepository ufsRepository;

    public void followStore(FollowStoreRequest followStoreRequest) {
        if (followStoreRequest.getStoreId() != null && followStoreRequest.getUid() != null)
            ufsRepository.findByUserUidAndStoreId(followStoreRequest.getUid(), followStoreRequest.getStoreId()).ifPresentOrElse(
                    ufs -> {
                        ufsRepository.delete(ufs);
                        log.info("User {} unfollow store {} successfully", followStoreRequest.getUid(), followStoreRequest.getStoreId());
                    },
                    () -> {
                        storeRepository.findByIdAndIsActiveIsTrue(followStoreRequest.getStoreId()).ifPresentOrElse(
                                store -> {
                                    userRepository.findByUid(followStoreRequest.getUid()).ifPresentOrElse(
                                            user -> {
                                                UserFollowStore ufs = UserFollowStore.builder()
                                                        .store(store)
                                                        .user(user)
                                                        .createAt(new Date())
                                                        .build();
                                                ufsRepository.save(ufs);
                                                log.info("User {} follow store {} successfully", followStoreRequest.getUid(), followStoreRequest.getStoreId());
                                            },
                                            () -> log.info("User {} is not available", followStoreRequest.getUid())
                                    );
                                },
                                () -> log.info("Store {} is not available", followStoreRequest.getStoreId())
                        );
                    }
            );
    }

    public FollowStoreResponse findAll(FindFollowStoreRequest ffsRequest) {
        return FollowStoreResponse.builder()
                .storeDtoList(
                        ufsRepository.findAllByUserUid
                                (ffsRequest.getUid(), PageRequest.of(ffsRequest.getCurrentPage(), ffsRequest.getSize()))
                                .stream().map(this::mapToStoreResponse).toList()
                )
                .currentPage(ffsRequest.getCurrentPage())
                .size(ffsRequest.getSize())
                .totalPage((int) Math.ceil((float) ufsRepository.countByUserUid(ffsRequest.getUid()) / ffsRequest.getSize()))
                .build();
    }

    private StoreDto mapToStoreResponse(UserFollowStore userFollowStore) {
        return StoreDto.builder()
                .id(userFollowStore.getStore().getId())
                .name(userFollowStore.getStore().getName())
                .bio(userFollowStore.getStore().getBio())
                .avatar(userFollowStore.getStore().getAvatar())
                .isOpen(userFollowStore.getStore().getIsOpen())
                .rating(userFollowStore.getStore().getRating())
                .build();
    }
}
