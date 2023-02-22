package com.sheppo.OrderService.User.dto.Cart.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long id;
    private List<CartBoxDto> cartBoxDtoList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}
