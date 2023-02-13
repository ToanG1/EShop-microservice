package com.example.UserService.Vendor.dto.Store.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindStoreRequest {
    private String ownerUid;
    private Boolean isActive;
    private Boolean isOpen;
    private Integer currentPage = 0;
    private Integer size = 10;
}
