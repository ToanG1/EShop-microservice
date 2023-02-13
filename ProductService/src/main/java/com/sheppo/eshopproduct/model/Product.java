package com.sheppo.eshopproduct.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "product")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private Integer price;
    private Integer quantity;
    private String storeId;
    private Boolean is_selling;
    private Boolean is_active;
    private List<String> listImages;
    private Category category;
    private List<Style> listStyle;
    private Integer rating;
    private Integer sales;
    private Date createAt;
    private Date updateAt;
}
