package com.example.UserService.User.dto.UserFollowStore.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private Long id;
    private String name;
    private String bio;
    private Boolean isOpen;
    private String avatar;
    private Integer rating;
}
