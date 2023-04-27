package com.sheppo.OrderService.User.dto.Shipping.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingDto {
    private Long id;
    private String name;
    private Integer shippingCost;
}
