package com.sheppo.OrderService.repository;

import com.sheppo.OrderService.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long>, JpaSpecificationExecutor<Voucher> {
    Optional<Voucher> findByCode(String code);
    List<Voucher> findAllByIsActiveIsTrue();
}
