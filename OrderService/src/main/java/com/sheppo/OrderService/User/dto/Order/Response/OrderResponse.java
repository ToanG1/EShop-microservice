package com.sheppo.OrderService.User.dto.Order.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer orderStatus; // 0 for pending, 1 for delivering, 2 for delivered, 3 for canceled, 4 for denied
    private List<OrderDto> orderDtoList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}
