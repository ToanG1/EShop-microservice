package com.sheppo.OrderService.Admin.dto.Shipping.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateShippingRequest {
    private String name;
    private Integer shippingCost;
    private Integer priority;
}
