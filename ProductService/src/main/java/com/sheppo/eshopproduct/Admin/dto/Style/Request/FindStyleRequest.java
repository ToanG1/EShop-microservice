package com.sheppo.eshopproduct.Admin.dto.Style.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindStyleRequest {
    private String id;
    private String name;
    private List<String> listCategoryId;
    private Boolean isNewest;
    private Integer currentPage = 0;
    private Integer size = 10;
}
