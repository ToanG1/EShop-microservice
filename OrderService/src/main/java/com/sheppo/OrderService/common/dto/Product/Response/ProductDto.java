package com.sheppo.OrderService.common.dto.Product.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private Integer price;
    private Integer quantity;
    private Long storeId;
    private List<String> listImages;
    private Integer rating;
    private Integer cartItemQuantity;
    private Long cartItemId;
}
