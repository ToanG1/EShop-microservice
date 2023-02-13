package com.example.UserService.User.dto.UserFollowProduct.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindFollowProductRequest {
    private String uid;
    private Integer currentPage = 0;
    private Integer size = 10;
}
