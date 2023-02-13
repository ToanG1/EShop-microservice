package com.sheppo.eshopproduct.Admin.dto.Style.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StyleResponse {
    private List<StyleDto> styleDtoList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}
