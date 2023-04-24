package com.example.UserService.auth.dto.response;

import com.example.UserService.model.User;
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
    private Long id;
    private String uid;
    private String name;
    private String email;
    private Integer phoneNumber;
    private UserRole role;
    private String avatar;
    private Integer point;
}
