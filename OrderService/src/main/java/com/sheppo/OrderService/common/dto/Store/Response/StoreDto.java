package com.sheppo.OrderService.common.dto.Store.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private String name;
    private Boolean isOpen;
    private String avatar;
    private Integer rating;
}
