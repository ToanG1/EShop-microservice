package com.sheppo.OrderService.Vendor.dto.Order.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private String productId;
    private String productName;
    private String productAvatar;
    private Integer productValue;
    private Integer quantity;
}
