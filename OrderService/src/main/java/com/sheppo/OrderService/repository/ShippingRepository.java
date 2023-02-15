package com.sheppo.OrderService.repository;

import com.sheppo.OrderService.model.Shipping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    Page<Shipping> findByNameContainsAndPriority(String name, Integer priority, Pageable pageable);
    Integer countByNameContainsAndPriority(String name, Integer priority);
    Page<Shipping> findByNameContains(String name, Pageable pageable);
    Integer countByNameContains(String name);
    Page<Shipping> findByPriority(Integer priority, Pageable pageable);
    Integer countByPriority(Integer priority);

}
