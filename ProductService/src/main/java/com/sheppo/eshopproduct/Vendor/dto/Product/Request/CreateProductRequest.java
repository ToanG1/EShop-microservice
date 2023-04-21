package com.sheppo.eshopproduct.Vendor.dto.Product.Request;

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
public class CreateProductRequest {
    private String name;
    private String description;
    private Integer price;
    private Long storeId;
    private List<String> listImages;
    private String categoryId;
    private List<String> listStyleId;
}

