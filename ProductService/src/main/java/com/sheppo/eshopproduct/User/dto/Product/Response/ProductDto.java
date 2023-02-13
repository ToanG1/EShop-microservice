package com.sheppo.eshopproduct.User.dto.Product.Response;

import com.sheppo.eshopproduct.model.Category;
import com.sheppo.eshopproduct.model.Style;
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
    private String description;
    private Integer price;
    private Integer quantity;
    private String storeId;
    private List<String> listImages;
    private Category category;
    private List<Style> listStyle;
    private Integer rating;
    private Integer sales;
}
