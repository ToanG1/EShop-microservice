package com.sheppo.OrderService.User.dto.Order.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessOrderResponse {
    private List<CreateOrderResponse> orders;
    private String res;
}
