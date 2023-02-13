package com.example.UserService.repository;

import com.example.UserService.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findAllByUserUid(String uid, Pageable pageable);
    Integer countAddressByUserUid(String uid);

    Optional<Address> findByUserUidAndId(String uid, Long id);
}
