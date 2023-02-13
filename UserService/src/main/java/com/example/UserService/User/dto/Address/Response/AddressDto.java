package com.example.UserService.User.dto.Address.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private String receiverName;
    private String receiverAddress;
    private Integer phoneNumber;
}
