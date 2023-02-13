package com.example.UserService.Vendor.dto.UserFollowStore.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindFollowStoreRequest {
    private Long storeId;
    private Integer currentPage = 0;
    private Integer size = 10;
}
