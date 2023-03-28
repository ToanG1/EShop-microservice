package com.sheppo.OrderService.User.dto.Order.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindOrderRequest {
    private Long id;
    private String uid;
    private Integer orderStatus = 0; // 0 for pending, 1 for delivering, 2 for delivered, 3 for canceled, 4 for denied
    private Integer currentPage = 0;
    private Integer size = 10;
}
