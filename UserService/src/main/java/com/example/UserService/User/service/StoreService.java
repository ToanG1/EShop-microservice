package com.example.UserService.User.service;

import com.example.UserService.User.dto.Store.Response.StoreResponse;
import com.example.UserService.model.Store;
import com.example.UserService.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("UserStoreService")
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponse findStore(Long id) {
        return mapToStoreResponse(storeRepository.findById(id));
    }

    private StoreResponse mapToStoreResponse(Optional<Store> store) {
        if (store.isPresent())
            return StoreResponse.builder()
                    .id(store.get().getId())
                    .name(store.get().getName())
                    .bio(store.get().getBio())
                    .isOpen(store.get().getIsOpen())
                    .avatar(store.get().getAvatar())
                    .rating(store.get().getRating())
                    .build();
        else return new StoreResponse();
    }

    public boolean isStoreValid(Long id) {
        return storeRepository.existsByIdAndIsActiveIsTrueAndIsActiveIsTrue(id);
    }
}
