package com.sheppo.OrderService.User.dto.CartItem.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartItemRequest {
    private String uid;
    private String productId;
    private Integer quantity = 1;
}
