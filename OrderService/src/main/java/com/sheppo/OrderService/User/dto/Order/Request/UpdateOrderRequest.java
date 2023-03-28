package com.sheppo.OrderService.User.dto.Order.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {
    private Long id;
    private String uid;
    private Long addressId;
    private String note;
    private String payment;
}
