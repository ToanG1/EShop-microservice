package com.example.UserService.repository;

import com.example.UserService.model.UserFollowProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFollowProductRepository extends JpaRepository<UserFollowProduct, Long> {
    Optional<UserFollowProduct> findByUserIdAndProductId(Long id, String productId);
    Page<UserFollowProduct> findAllByUserId(Long uid, Pageable pageable);
    Page<UserFollowProduct> findAllByProductId(String id, Pageable pageable);
    int countByUserId(Long id);
    int countByProductId(String uid);
}
