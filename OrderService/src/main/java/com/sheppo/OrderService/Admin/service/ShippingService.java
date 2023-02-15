package com.sheppo.OrderService.Admin.service;

import com.sheppo.OrderService.Admin.dto.Shipping.Request.CreateShippingRequest;
import com.sheppo.OrderService.Admin.dto.Shipping.Request.FindShippingRequest;
import com.sheppo.OrderService.Admin.dto.Shipping.Request.UpdateShippingRequest;
import com.sheppo.OrderService.Admin.dto.Shipping.Response.ShippingDto;
import com.sheppo.OrderService.Admin.dto.Shipping.Response.ShippingResponse;
import com.sheppo.OrderService.model.Shipping;
import com.sheppo.OrderService.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service("AdminShippingService")
@Slf4j
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public void createShipping(CreateShippingRequest createShippingRequest) {
        Shipping shipping = Shipping.builder()
                .name(createShippingRequest.getName())
                .shippingCost(createShippingRequest.getShippingCost())
                .priority(createShippingRequest.getPriority())
                .createAt(new Date())
                .updateAt(new Date())
                .build();
        shippingRepository.save(shipping);
        log.info("Shipping {} is saved successfully", shipping.getId());
    }

    public void updateShipping(UpdateShippingRequest updateShippingRequest) {
        shippingRepository.findById(updateShippingRequest.getId()).ifPresentOrElse(
                shipping -> {
                    shipping.setName(updateShippingRequest.getName() != null ? updateShippingRequest.getName() : shipping.getName());
                    shipping.setShippingCost(updateShippingRequest.getShippingCost() != null ?
                            updateShippingRequest.getShippingCost() : shipping.getShippingCost());
                    shipping.setPriority(updateShippingRequest.getPriority() != null ?
                            updateShippingRequest.getPriority() : shipping.getPriority());
                    shippingRepository.save(shipping);
                    log.info("Shipping {} is updated successfully", updateShippingRequest.getId());
                },
                () -> log.info("Shipping {} is not available", updateShippingRequest.getId())
        );
    }

    public void deleteShipping(Long id) {
        shippingRepository.findById(id).ifPresentOrElse(
                shipping -> {
                    shippingRepository.delete(shipping);
                    log.info("Shipping {} is removed successfully", id);
                },
                () -> log.info("Shipping {} is not available", id)
        );
    }

    public ShippingResponse findShipping(FindShippingRequest findShippingRequest) {
        List<ShippingDto> shippingDtoList = new ArrayList<>();
        int totalShipping = 0;
        if (findShippingRequest.getId() != null) {
            Optional<Shipping> shipping = shippingRepository.findById(findShippingRequest.getId());
            if (shipping.isPresent()) {
                shippingDtoList.add(mapToShippingDto(shipping.get()));
                totalShipping = 1;
            } else log.info("Shipping {} is not available", findShippingRequest.getId());
        } else {
            Pageable pageable = null;
            pageable = setPaginationAndSort(pageable, findShippingRequest);

            if (findShippingRequest.getName() != null && findShippingRequest.getPriority() != null){
                shippingDtoList = shippingRepository.findByNameContainsAndPriority(findShippingRequest.getName(),
                        findShippingRequest.getPriority(), pageable).stream().map(this::mapToShippingDto).toList();
                totalShipping = shippingRepository.countByNameContainsAndPriority(findShippingRequest.getName(),
                        findShippingRequest.getPriority());
            }
            else if (findShippingRequest.getName() != null) {
                shippingDtoList = shippingRepository.findByNameContains(findShippingRequest.getName(), pageable)
                        .stream().map(this::mapToShippingDto).toList();
                totalShipping = shippingRepository.countByNameContains(findShippingRequest.getName());
            } else if (findShippingRequest.getPriority() != null) {
                shippingDtoList = shippingRepository.findByPriority(findShippingRequest.getPriority(), pageable)
                        .stream().map(this::mapToShippingDto).toList();
                totalShipping = shippingRepository.countByPriority(findShippingRequest.getPriority());
            } else {
                shippingDtoList = shippingRepository.findAll(pageable).stream().map(this::mapToShippingDto).toList();
                totalShipping = (int) shippingRepository.count();
            }
        }
        return ShippingResponse.builder()
                .shippingDtoList(shippingDtoList)
                .currentPage(findShippingRequest.getCurrentPage())
                .totalPage((int) Math.ceil((float) totalShipping / findShippingRequest.getSize()))
                .size(findShippingRequest.getSize())
                .build();
    }

    private Pageable setPaginationAndSort(Pageable pageable, FindShippingRequest findShippingRequest) {
        if (findShippingRequest.getIsShippingCostIncre() != null) {
            if (findShippingRequest.getIsShippingCostIncre()) {
                pageable = PageRequest.of(findShippingRequest.getCurrentPage(), findShippingRequest.getSize(),
                        Sort.by("shippingCost").ascending());
            }
            else{
                pageable = PageRequest.of(findShippingRequest.getCurrentPage(), findShippingRequest.getSize(),
                        Sort.by("shippingCost").descending());
            }
        }
        else if (findShippingRequest.getIsPriorityIncre() != null) {
            if (findShippingRequest.getIsPriorityIncre()) {
                pageable = PageRequest.of(findShippingRequest.getCurrentPage(), findShippingRequest.getSize(),
                        Sort.by("priority").ascending());
            }
            else {
                pageable = PageRequest.of(findShippingRequest.getCurrentPage(), findShippingRequest.getSize(),
                        Sort.by("priority").descending());
            }
        }
        else if (findShippingRequest.getIsNewest() != null) {
            if (findShippingRequest.getIsNewest()) {
                pageable = PageRequest.of(findShippingRequest.getCurrentPage(), findShippingRequest.getSize(),
                        Sort.by("createAt").descending());
            }
            else {
                pageable = PageRequest.of(findShippingRequest.getCurrentPage(), findShippingRequest.getSize(),
                        Sort.by("createAt").ascending());
            }
        } else {
            pageable = PageRequest.of(findShippingRequest.getCurrentPage(), findShippingRequest.getSize());
        }
        return pageable;
    }

    private ShippingDto mapToShippingDto(Shipping shipping) {
        return ShippingDto.builder()
                .id(shipping.getId())
                .name(shipping.getName())
                .shippingCost(shipping.getShippingCost())
                .priority(shipping.getPriority())
                .createAt(shipping.getCreateAt())
                .updateAt(shipping.getUpdateAt())
                .build();
    }
}
