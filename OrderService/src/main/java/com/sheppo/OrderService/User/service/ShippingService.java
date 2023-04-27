package com.sheppo.OrderService.User.service;

import com.sheppo.OrderService.User.dto.Shipping.Response.ShippingDto;
import com.sheppo.OrderService.model.Shipping;
import com.sheppo.OrderService.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserShippingService")
@RequiredArgsConstructor
public class ShippingService {
    private final ShippingRepository shippingRepository;

    public ShippingDto mapToShippingDto(Shipping shipping){
        return ShippingDto.builder()
                .id(shipping.getId())
                .name(shipping.getName())
                .shippingCost(shipping.getShippingCost())
                .build();
    }

    public List<ShippingDto> findAll() {
        return shippingRepository.findAll().stream().map(this::mapToShippingDto).toList();
    }
}
