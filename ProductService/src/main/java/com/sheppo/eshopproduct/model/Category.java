package com.sheppo.eshopproduct.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "category")
public class Category {
    @Id
    private String id;
    private String name;
    private Integer total;
    private String avatar;
    private Date createAt;
    private Date updateAt;
}
