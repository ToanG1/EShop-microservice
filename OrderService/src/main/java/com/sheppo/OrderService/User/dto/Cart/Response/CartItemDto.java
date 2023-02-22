package com.sheppo.OrderService.User.dto.Cart.Response;

import com.sheppo.OrderService.common.dto.Product.Response.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long id;
    private ProductDto productDto;
    private Integer quantity;
}
