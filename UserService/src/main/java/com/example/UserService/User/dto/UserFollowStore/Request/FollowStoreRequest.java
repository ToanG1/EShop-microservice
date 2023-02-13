package com.example.UserService.User.dto.UserFollowStore.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowStoreRequest {
    private String uid;
    private Long storeId;
}
