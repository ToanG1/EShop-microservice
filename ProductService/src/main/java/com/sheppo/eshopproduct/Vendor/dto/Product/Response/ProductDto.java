package com.sheppo.eshopproduct.Vendor.dto.Product.Response;

import com.sheppo.eshopproduct.model.Category;
import com.sheppo.eshopproduct.model.Style;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    private Boolean is_selling;
    private Boolean is_active;
    private List<String> listImages;
    private Category category;
    private List<Style> listStyle;
    private Integer rating;
    private Integer sales;
    private Date createAt;
    private Date updateAt;
}
