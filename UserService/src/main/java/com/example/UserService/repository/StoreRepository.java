package com.example.UserService.repository;

import com.example.UserService.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StoreRepository extends JpaRepository<Store, Long> {
    Page<Store> findAllByOwnerUidAndIsActiveAndIsOpen(String uid, Boolean isActive, Boolean isOpen, Pageable pageable);

    Integer countByOwnerUidAndIsActiveAndIsOpen(String uid, Boolean isActive, Boolean isOpen);

    Optional<Store> findByIdAndIsActiveIsTrue(Long id);

    boolean existsByIdAndIsActiveIsTrueAndIsActiveIsTrue(Long id);
}
