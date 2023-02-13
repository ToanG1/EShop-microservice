package com.example.UserService.repository;

import com.example.UserService.model.Store;
import com.example.UserService.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findAllByOwnerUidAndIsActiveAndIsOpen(String uid, Boolean isActive, Boolean isOpen, Pageable pageable);

    Integer countByOwnerUidAndIsActiveAndIsOpen(String uid, Boolean isActive, Boolean isOpen);
}
