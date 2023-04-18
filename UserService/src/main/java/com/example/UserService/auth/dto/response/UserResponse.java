package com.example.UserService.auth.dto.response;

import com.example.UserService.model.User;
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
    private Integer role;
    private String avatar;
    private Integer point;
}
