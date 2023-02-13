package com.example.UserService.User.dto.Address.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressRequest {
    private String uid;
    private String name;
    private String address;
    private Integer phoneNumber;
}
