package com.example.UserService.User.dto.Address.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAddressRequest {
    private String uid;
    private Integer currentPage = 0;
    private Integer size = 10;
}
