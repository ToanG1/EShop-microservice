package com.sheppo.eshopproduct.Admin.dto.Style.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStyleRequest {
    private String id;
    private String name;
    private List<String> listCategoryId;
}
