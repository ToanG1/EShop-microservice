package com.example.UserService.User.dto.Store.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponse {
    private Long id;
    private String name;
    private String bio;
    private Boolean isOpen;
    private String avatar;
    private Integer rating;
    private Date createAt;
}
