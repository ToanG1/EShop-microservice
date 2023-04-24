package com.example.UserService.User.dto.UserFollowProduct.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowProductRequest {
    private Long id;
    private String productId;
}
