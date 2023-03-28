package com.sheppo.OrderService.Vendor.service;

import com.sheppo.OrderService.Vendor.dto.Order.Response.OrderDto;
import com.sheppo.OrderService.Vendor.dto.Order.Response.OrderItemDto;
import com.sheppo.OrderService.Vendor.dto.Order.Request.FindOrderRequest;
import com.sheppo.OrderService.Vendor.dto.Order.Response.OrderResponse;
import com.sheppo.OrderService.common.service.StoreService;
import com.sheppo.OrderService.model.Order;
import com.sheppo.OrderService.model.OrderItem;
import com.sheppo.OrderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("VendorOrderService")
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final StoreService storeService;
    private final OrderRepository orderRepository;

    public OrderResponse findOrder(FindOrderRequest findOrderRequest) {
        if (storeService.isStoreValid(findOrderRequest.getId())){
            List<Order> orderList;
            List<OrderDto> orderDtos = new ArrayList<>();
            int count = 0;
            if (findOrderRequest.getIsPaid() ==null){
                orderList = orderRepository.findAllByStoreIdAndOrderStatus(findOrderRequest.getId(), findOrderRequest.getOrderStatus(),
                        PageRequest.of(findOrderRequest.getCurrentPage(), findOrderRequest.getSize())).getContent();
                orderDtos = orderList.stream().map(this::mapToOrderDto).toList();
                count = orderRepository.countAllByStoreIdAndOrderStatus(findOrderRequest.getId(), findOrderRequest.getOrderStatus());
            } else {
                orderList = orderRepository.findAllByStoreIdAndOrderStatusAndIsPaid(findOrderRequest.getId(), findOrderRequest.getOrderStatus(),
                        findOrderRequest.getIsPaid(),PageRequest.of(findOrderRequest.getCurrentPage(), findOrderRequest.getSize())).getContent();
                orderDtos = orderList.stream().map(this::mapToOrderDto).toList();
                count = orderRepository.countAllByStoreIdAndOrderStatusAndIsPaid(findOrderRequest.getId(), findOrderRequest.getOrderStatus(), findOrderRequest.getIsPaid());
            }
            return OrderResponse.builder()
                    .orderStatus(findOrderRequest.getOrderStatus())
                    .orderDtoList(orderDtos)
                    .currentPage(findOrderRequest.getCurrentPage())
                    .totalPage((int) Math.ceil((float) count /
                            findOrderRequest.getSize()))
                    .size(findOrderRequest.getSize())
                    .build();
        } else {
            log.warn("Store {} is not available", findOrderRequest.getId());
            return null;
        }
    }

    private OrderDto  mapToOrderDto(Order order) {
        List<OrderItemDto> orderItemDtos = order.getOrderItemList().stream().map(this::mapToOrderItemDto).toList();
        return OrderDto.builder()
                .id(order.getId())
                .uid(order.getUid())
                .receiverName(order.getReceiverName())
                .receiverAddress(order.getReceiverAddress())
                .phoneNumber(order.getPhoneNumber())
                .note(order.getNote())
                .orderStatus(order.getOrderStatus())
                .orderValue(order.getOrderValue())
                .payment(order.getPayment())
                .isPaid(order.getIsPaid())
                .paymentUrl(order.getPaymentUrl())
                .createAt(order.getCreateAt())
                .updateAt(order.getUpdateAt())
                .orderItemDtoList(orderItemDtos)
                .build();
    }

    private OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .productId(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .productAvatar(orderItem.getProductAvatar())
                .productValue(orderItem.getProductValue())
                .quantity(orderItem.getQuantity())
                .build();
    }
}
