package com.sheppo.OrderService.User.service;

import com.sheppo.OrderService.User.dto.Voucher.Response.VoucherDto;
import com.sheppo.OrderService.model.Voucher;
import com.sheppo.OrderService.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherDto mapToVoucherDto(Voucher voucher){
        return VoucherDto.builder()
                .id(voucher.getId())
                .name(voucher.getName())
                .code(voucher.getCode())
                .categoryId(voucher.getCategoryId())
                .storeId(voucher.getStoreId())
                .role(voucher.getRole())
                .voucherValue(voucher.getVoucherValue())
                .build();
    }

    public List<VoucherDto> findVoucher() {
        return voucherRepository.findAllByIsActiveIsTrue().stream().map(this::mapToVoucherDto).toList();
    }
}
