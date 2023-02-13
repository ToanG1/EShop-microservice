package com.sheppo.eshopproduct.Admin.dto.Product.Request;

import com.sheppo.eshopproduct.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindProductRequest {
    private String name;
    private Category category;
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
