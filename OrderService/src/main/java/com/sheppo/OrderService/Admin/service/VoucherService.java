package com.sheppo.OrderService.Admin.service;

import com.sheppo.OrderService.Admin.dto.Voucher.Request.CreateVoucherRequest;
import com.sheppo.OrderService.Admin.dto.Voucher.Request.FindVoucherRequest;
import com.sheppo.OrderService.Admin.dto.Voucher.Request.UpdateVoucherRequest;
import com.sheppo.OrderService.Admin.dto.Voucher.Response.VoucherDto;
import com.sheppo.OrderService.Admin.dto.Voucher.Response.VoucherResponse;
import com.sheppo.OrderService.model.Voucher;
import com.sheppo.OrderService.repository.VoucherRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public void createVoucher(CreateVoucherRequest createVoucherRequest) {
        Voucher voucher = Voucher.builder()
                .code(createVoucherRequest.getCode())
                .name(createVoucherRequest.getName())
                .storeId(createVoucherRequest.getStoreId())
                .categoryId(createVoucherRequest.getCategoryId())
                .isActive(false)
                .voucherValue(createVoucherRequest.getVoucherValue())
                .count(createVoucherRequest.getCount())
                .role(createVoucherRequest.getRole())
                .createAt(new Date())
                .updateAt(new Date())
                .build();
        voucherRepository.save(voucher);
        log.info("Voucher {} is created successfully", voucher.getId());
    }

    public void updateVoucher(UpdateVoucherRequest updateVoucherRequest) {
        voucherRepository.findById(updateVoucherRequest.getId()).ifPresentOrElse(
                voucher -> {
                    voucher.setName(updateVoucherRequest.getName() != null ?
                            updateVoucherRequest.getName() : voucher.getName());
                    voucher.setCode(updateVoucherRequest.getCode() != null ?
                            updateVoucherRequest.getCode() : voucher.getCode());
                    voucher.setCategoryId(updateVoucherRequest.getCategoryId() != null ?
                            updateVoucherRequest.getCategoryId() : voucher.getCategoryId());
                    voucher.setStoreId(updateVoucherRequest.getStoreId() != null ?
                            updateVoucherRequest.getStoreId() : voucher.getStoreId());
                    voucher.setVoucherValue(voucher.getVoucherValue() != null ?
                            updateVoucherRequest.getVoucherValue() : voucher.getVoucherValue());
                    voucher.setCount(updateVoucherRequest.getCount() != null ?
                            updateVoucherRequest.getCount() : voucher.getCount());
                    voucher.setIsActive(false);
                    voucher.setUpdateAt(new Date());
                    voucherRepository.save(voucher);
                    log.info("Voucher {} is updated successfully", voucher.getId());
                },
                () -> log.info("Voucher {} is not available", updateVoucherRequest.getId())
        );
    }

    public void deleteVoucher(Long id) {
        voucherRepository.findById(id).ifPresentOrElse(
                voucher -> {
                    voucherRepository.delete(voucher);
                    log.info("Voucher {} is deleted successfully", id);
                },
                () -> log.info("Voucher {} is not available", id)
        );
    }

    public VoucherResponse findVoucher(FindVoucherRequest request) {
        Pageable pageable = null;
        pageable = setPaginationAndSort(pageable, request);
        Page page = voucherRepository.findAll((Specification<Voucher>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), request.getId())));
            }
            if(request.getCode() != null){
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("code"), "%"+request.getCode()+"%")));
            }
            if (request.getName() != null){
                predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"), "%"+request.getName()+"%")));
            }
            if (request.getIsActive() != null){
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("isActive"), request.getIsActive())));
            }
            if (request.getCategoryId() != null){
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("categoryId"), request.getCategoryId())));
            }
            if (request.getStoreId() != null){
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("storeId"), request.getStoreId())));
            }
            if(request.getRole() != null){
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("role"), request.getRole())));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
        return VoucherResponse.builder()
                .voucherDtoList(page.getContent())
                .currentPage(request.getCurrentPage())
                .totalPage(page.getTotalPages())
                .size(request.getSize())
                .build();
    }

    private Pageable setPaginationAndSort(Pageable pageable, FindVoucherRequest request) {
        if (request.getIsValueIncre() != null) {
            if (request.getIsValueIncre())
                pageable = PageRequest.of(request.getCurrentPage(), request.getSize(), Sort.by("voucherValue").ascending());
            else
                pageable = PageRequest.of(request.getCurrentPage(), request.getSize(), Sort.by("voucherValue").descending());
        } else if (request.getIsNewest() != null) {
            if (request.getIsNewest())
                pageable = PageRequest.of(request.getCurrentPage(), request.getSize(), Sort.by("createAt").descending());
            else
                pageable = PageRequest.of(request.getCurrentPage(), request.getSize(), Sort.by("createAt").ascending());
        } else {
            pageable = PageRequest.of(request.getCurrentPage(), request.getSize());
        }
        return pageable;
    }

    public void updateActive(Long id) {
        voucherRepository.findById(id).ifPresentOrElse(
                voucher -> {
                    voucher.setIsActive(!voucher.getIsActive());
                    voucherRepository.save(voucher);
                },
                () -> log.info("Voucher {} is not available", id)
        );
    }
}
