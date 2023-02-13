package com.sheppo.eshopproduct.Vendor.dto.Product.Request;

import com.sheppo.eshopproduct.model.Category;
import com.sheppo.eshopproduct.model.Style;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    private String id;
    private String name;
    private String description;
    private Integer price;
    private Integer quantity;
    private List<String> listImages;
    private String category;
    private List<String> listStyle;
}
