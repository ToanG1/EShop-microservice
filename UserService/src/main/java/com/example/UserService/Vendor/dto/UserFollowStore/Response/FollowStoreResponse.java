package com.example.UserService.Vendor.dto.UserFollowStore.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowStoreResponse {
    private List<UserDto> userDtosList;
    private Integer currentPage;
    private Integer size;
    private Integer totalPage;
}
