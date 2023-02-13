package com.example.UserService.User.dto.UserFollowStore.Response;

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
    private List<StoreDto> storeDtoList;
    private Integer currentPage;
    private Integer size;
    private Integer totalPage;
}
