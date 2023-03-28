package com.sheppo.OrderService.repository;

import com.sheppo.OrderService.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUidAndId(String uid, Long id);
    Page<Order> findAllByUidAndOrderStatus(String uid, Integer orderStatus, Pageable pageable);
    int countAllByUidAndOrderStatus(String uid, Integer orderStatus);

    Page<Order> findAllByStoreIdAndOrderStatus(Long id, Integer orderStatus, Pageable pageable);
    int countAllByStoreIdAndOrderStatus(Long id, Integer orderStatus);

    Page<Order> findAllByStoreIdAndOrderStatusAndIsPaid(Long id, Integer orderStatus, Boolean isPaid, Pageable pageable);
    int countAllByStoreIdAndOrderStatusAndIsPaid(Long id, Integer orderStatus, Boolean isPaid);

}
