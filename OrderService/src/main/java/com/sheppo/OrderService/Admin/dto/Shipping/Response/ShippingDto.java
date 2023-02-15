package com.sheppo.OrderService.Admin.dto.Shipping.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingDto {
    private Long id;
    private String name;
    private Integer shippingCost;
    private Integer priority;
    private Date createAt;
    private Date updateAt;
}
