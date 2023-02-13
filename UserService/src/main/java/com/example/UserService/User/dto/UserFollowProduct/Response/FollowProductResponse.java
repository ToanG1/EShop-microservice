package com.example.UserService.User.dto.UserFollowProduct.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowProductResponse {
    private List<ProductDto> productDtoList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}
