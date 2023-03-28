package com.sheppo.OrderService.User.dto.Order.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private String uid;
    private String receiverName;
    private String receiverAddress;
    private Integer phoneNumber;
    private String note;
    private Integer orderStatus; // 0 for pending, 1 for delivering, 2 for delivered, 3 for canceled, 4 for denied
    private Integer orderValue;
    private Integer shippingCost;
    private String payment;
    private Boolean isPaid;
    private String paymentUrl;
    private Date createAt;
    private Date updateAt;

    private List<OrderItemDto> orderItemDtoList;
}
