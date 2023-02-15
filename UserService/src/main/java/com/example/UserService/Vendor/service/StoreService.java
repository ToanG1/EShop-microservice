package com.example.UserService.Vendor.service;

import com.example.UserService.Vendor.dto.Store.Request.CreateStoreRequest;
import com.example.UserService.Vendor.dto.Store.Request.FindStoreRequest;
import com.example.UserService.Vendor.dto.Store.Request.UpdateStoreRequest;
import com.example.UserService.Vendor.dto.Store.Response.ListStoreResponse;
import com.example.UserService.Vendor.dto.Store.Response.StoreResponse;
import com.example.UserService.model.Store;
import com.example.UserService.repository.StoreRepository;
import com.example.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("VendorStoreService")
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final UserRepository userRepository;

    private final StoreRepository storeRepository;

    public void createStore(CreateStoreRequest createStoreRequest) {
        userRepository.findByUid(createStoreRequest.getOwnerUid()).ifPresentOrElse(
                user -> {
                    Store store = Store.builder()
                            .name(createStoreRequest.getName())
                            .bio(createStoreRequest.getBio())
                            .owner(user)
                            .avatar(createStoreRequest.getAvatar())
                            .isOpen(false)
                            .isActive(false)
                            .rating(0)
                            .createAt(new Date())
                            .updateAt(new Date())
                            .build();
                    storeRepository.save(store);
                    log.info("Store {} is saved successfully", store.getId());
                },
                () -> log.info("User {} is not available", createStoreRequest.getOwnerUid())
        );

    }

    public StoreResponse findStore(Long id) {
        Optional<Store> store = storeRepository.findById(id);
        if (store.isPresent()) return mapToStoreResponse(store.get());
        else return new StoreResponse();
    }

    public ListStoreResponse findStore(FindStoreRequest findStoreRequest) {
        Pageable pageable = PageRequest.of(findStoreRequest.getCurrentPage(), findStoreRequest.getSize());
        List<Store> storeList = storeRepository.findAllByOwnerUidAndIsActiveAndIsOpen
                (findStoreRequest.getOwnerUid(),findStoreRequest.getIsActive(), findStoreRequest.getIsOpen(), pageable).getContent();
        return ListStoreResponse.builder()
                .storeResponseList(storeList.stream().map(this::mapToStoreResponse).toList())
                .currentPage(findStoreRequest.getCurrentPage())
                .totalPage((int) Math.ceil((float) (int)storeRepository.countByOwnerUidAndIsActiveAndIsOpen
                        (findStoreRequest.getOwnerUid(),findStoreRequest.getIsActive(), findStoreRequest.getIsOpen())
                        / findStoreRequest.getSize()))
                .size(findStoreRequest.getSize())
                .build();
    }

    private StoreResponse mapToStoreResponse(Store store) {
        System.out.println(store.getStaffs());
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .avatar(store.getAvatar())
                .bio(store.getBio())
                .rating(store.getRating())
                .isOpen(store.getIsOpen())
                .isActive(store.getIsActive())
                .createAt(store.getCreateAt())
                .stafflist(store.getStaffs())
                .build();
    }

    public void updateStore(UpdateStoreRequest updateStoreRequest) {
        storeRepository.findById(updateStoreRequest.getId()).ifPresentOrElse(
                store -> {
                    store.setName(updateStoreRequest.getName() != null ? updateStoreRequest.getName() : store.getName());
                    store.setBio(updateStoreRequest.getBio() != null ? updateStoreRequest.getBio() : store.getBio());
                    store.setAvatar(updateStoreRequest.getAvatar() != null ?
                            updateStoreRequest.getAvatar() : store.getAvatar());
                    store.setIsOpen(false);
                    store.setIsActive(false);
                    store.setUpdateAt(new Date());

                    storeRepository.save(store);
                    log.info("Store {} is update successfully", store.getId());
                },
                () -> log.info("Store {} is not available", updateStoreRequest.getId()));
    }

    public void changeOpenStatus(long id) {
        storeRepository.findById(id).ifPresentOrElse(
                store -> {
                    if (store.getIsActive()) {
                        store.setIsOpen(!store.getIsOpen());
                        store.setUpdateAt(new Date());

                        storeRepository.save(store);
                        log.info("Store {} is {} now", store.getId(), store.getIsOpen() ? "open" : "close");
                    } else log.info("Store {} is not active yet", id);
                },
                () -> log.info("Store {} is not available", id)
        );
    }
}
