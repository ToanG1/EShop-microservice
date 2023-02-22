package com.sheppo.OrderService.User.dto.Cart.Response;

import com.sheppo.OrderService.common.dto.Store.Response.StoreDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartBoxDto {
    private Long id;
    private List<CartItemDto> cartItemDtoList;
    private StoreDto store;
}
