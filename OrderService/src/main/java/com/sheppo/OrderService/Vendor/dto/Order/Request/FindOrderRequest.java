package com.sheppo.OrderService.Vendor.dto.Order.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindOrderRequest {
    private Long id;
    private Integer orderStatus = 0;
    private Boolean isPaid;
    private Integer currentPage = 0;
    private Integer size = 10;
}
