package com.example.UserService.Vendor.dto.Store.Response;

import com.example.UserService.model.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListStoreResponse {
    private List<StoreResponse> storeResponseList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}
