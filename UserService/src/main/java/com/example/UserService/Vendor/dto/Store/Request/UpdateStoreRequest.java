package com.example.UserService.Vendor.dto.Store.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoreRequest {
    private Long id;
    private String name;
    private String bio;
    private String avatar;
}
