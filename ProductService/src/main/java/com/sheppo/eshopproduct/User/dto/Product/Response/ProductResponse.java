package com.sheppo.eshopproduct.User.dto.Product.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private List<ProductDto> productDtoList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}
