package com.sheppo.eshopproduct.Vendor.dto.Product.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportProductRequest {
    private String id;
    private Integer quantity;
}
