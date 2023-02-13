package com.example.UserService.Vendor.dto.Store.Response;

import com.example.UserService.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreResponse {
    private Long id;
    private String name;
    private String bio;
    private Boolean isActive;
    private Boolean isOpen;
    private String avatar;
    private Integer rating;
    private Date createAt;
    private List<User> stafflist;
}
