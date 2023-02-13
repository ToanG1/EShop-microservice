package com.example.UserService.User.dto.Address.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListAddressResponse {
    private List<AddressDto> addressDtoList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}
