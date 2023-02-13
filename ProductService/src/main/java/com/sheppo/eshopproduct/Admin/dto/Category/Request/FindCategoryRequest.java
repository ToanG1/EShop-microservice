package com.sheppo.eshopproduct.Admin.dto.Category.Request;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindCategoryRequest {
    private String id;
    private String name;
    private Integer currentPage = 0;
    private Integer size = 10;
    private Boolean isNewest;
    private Boolean isTotalProductIncre;
}
