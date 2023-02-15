package com.sheppo.OrderService.Admin.dto.Shipping.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingResponse {
    private List<ShippingDto> shippingDtoList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}
