package com.example.UserService.Vendor.dto.UserFollowProduct.Response;

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
    private List<UserDto> userDtosList;
    private Integer currentPage;
    private Integer size;
    private Integer totalPage;
}
