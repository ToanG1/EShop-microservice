package com.sheppo.OrderService.User.dto.CartItem.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemRequest {
    private String uid;
    private Long cartItemId;
    private Integer quantity;
}
