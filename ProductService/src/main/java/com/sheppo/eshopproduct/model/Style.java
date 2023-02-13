package com.sheppo.eshopproduct.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "style")
public class Style {
    @Id
    private String id;
    private String name;
    private List<Category> listCategory;
    private Date createAt;
    private Date updateAt;
}
