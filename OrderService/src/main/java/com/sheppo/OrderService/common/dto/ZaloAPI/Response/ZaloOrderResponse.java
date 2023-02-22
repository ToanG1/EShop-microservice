package com.sheppo.OrderService.common.dto.ZaloAPI.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZaloOrderResponse {
    private String orderUrl;
}
