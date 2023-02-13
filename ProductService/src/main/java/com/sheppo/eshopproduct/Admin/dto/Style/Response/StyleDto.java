package com.sheppo.eshopproduct.Admin.dto.Style.Response;

import com.sheppo.eshopproduct.model.Category;
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
public class StyleDto {
    private String id;
    private String name;
    private List<Category> listCategory;
    private Date createAt;
    private Date updateAt;
}
