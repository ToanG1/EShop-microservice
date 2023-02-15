package com.sheppo.OrderService.Admin.dto.Shipping.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindShippingRequest {
    private Long id;
    private String name;
    private Integer priority;
    private Boolean isShippingCostIncre;
    private Boolean isPriorityIncre;
    private Boolean isNewest;
    private Integer currentPage = 0;
    private Integer size = 10;
}
