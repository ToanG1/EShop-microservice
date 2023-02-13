package com.example.UserService.repository;

import com.example.UserService.model.UserFollowStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserFollowStoreRepository extends JpaRepository<UserFollowStore, Long> {
    Optional<UserFollowStore> findByUserUidAndStoreId(String uid, Long storeId);
    Page<UserFollowStore> findAllByUserUid(String user_uid, Pageable pageable);
    Page<UserFollowStore> findAllByStoreId(Long id, Pageable pageable);
    int countByUserUid(String uid);
    int countByStoreId(Long id);
}
