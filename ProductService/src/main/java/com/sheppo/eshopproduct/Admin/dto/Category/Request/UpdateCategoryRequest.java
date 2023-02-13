package com.sheppo.eshopproduct.Admin.dto.Category.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {
    private String id;
    private String name;
}
