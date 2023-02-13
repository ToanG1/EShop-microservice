package com.example.UserService.User.dto.Address.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAddressRequest {
    private String uid;
    private Long id;
}
