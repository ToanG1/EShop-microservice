package com.sheppo.eshopproduct.Admin.dto.Category.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private String id;
    private String name;
    private Integer total;
    private Date createAt;
    private Date updateAt;
}
