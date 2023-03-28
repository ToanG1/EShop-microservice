package com.sheppo.OrderService.User.dto.Order.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteOrderRequest {
    private Long id;
    private String uid;
}
