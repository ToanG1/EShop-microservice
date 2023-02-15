package com.sheppo.OrderService.Admin.dto.Voucher.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVoucherRequest {
    private Long id;
    private String code;
    private String name;
    private String categoryId;     // allCategory for all
    private Long storeId;  // 0 for all
    private Integer role;   // 0 is shipping voucher, 1 is product voucher
    private Float voucherValue;
    private Integer count;
}
