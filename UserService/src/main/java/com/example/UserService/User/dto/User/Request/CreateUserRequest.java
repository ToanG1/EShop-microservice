package com.example.UserService.User.dto.User.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String uid;
    private String name;
    private String email;
    private Integer phoneNumber;
    private String avatar;
}
