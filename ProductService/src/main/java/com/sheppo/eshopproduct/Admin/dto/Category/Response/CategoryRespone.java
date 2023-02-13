package com.sheppo.eshopproduct.Admin.dto.Category.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRespone {
    private List<CategoryDto> categoryDtoList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}
