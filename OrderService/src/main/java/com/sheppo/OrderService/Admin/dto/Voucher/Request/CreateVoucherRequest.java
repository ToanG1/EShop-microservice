package com.sheppo.OrderService.Admin.dto.Voucher.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVoucherRequest {
    private String code;
    private String name;
    private String categoryId = "allCategory";     // allCategory for all
    private Long storeId = Long.valueOf(0);  // 0 for all
    private Integer role;   // 0 is shipping voucher, 1 is product voucher
    private Float voucherValue;
    private Integer count;
}
