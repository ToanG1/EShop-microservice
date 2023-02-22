package com.sheppo.OrderService.User.dto.Order.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequest {
    private String uid;
    private Long addressId;
    private String note;
    private Long shippingId;
    private String payment;
    private String productVoucher;
    private String shippingVoucher;
    private List<Long> cartItemIdList;
}
