package com.example.UserService.User.dto.User.Response;

import com.example.UserService.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String uid;
    private String name;
    private String email;
    private Integer phoneNumber;
    private UserRole role;
    private String avatar;
    private Integer point;
}
