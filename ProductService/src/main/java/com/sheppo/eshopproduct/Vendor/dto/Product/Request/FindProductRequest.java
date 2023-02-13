package com.sheppo.eshopproduct.Vendor.dto.Product.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindProductRequest {
    private String name;
    private String categoryId;
    private String storeId;
    private List<String> listStyleId;
    private Boolean isNewest;
    private Boolean isBestRating;
    private Boolean isBestSelling;
    private Boolean isPriceIncre;
    private Boolean is_selling;
    private Boolean is_active;
    private Integer currentPage = 0;
    private Integer size = 10;
}
