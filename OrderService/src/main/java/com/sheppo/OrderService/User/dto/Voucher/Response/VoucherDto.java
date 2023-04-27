package com.sheppo.OrderService.User.dto.Voucher.Response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDto {
    private Long id;
    private String code;
    private String name;
    private String categoryId;     // allCategory for all
    private Long storeId;  // 0 for all
    private Integer role;   // 0 is shipping voucher, 1 is product voucher
    private Float voucherValue;
}
