package com.sheppo.OrderService.User.dto.Cart.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindCartRequest {
    private String uid;
    private Integer currentPage = 0;
    private Integer size = 20;
}
