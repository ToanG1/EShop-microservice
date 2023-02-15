package com.sheppo.OrderService.Admin.dto.Voucher.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDto {
    private Long id;
    private String code;
    private String name;
    private Boolean isActive;
    private String categoryId;     // allCategory for all
    private Long storeId;  // 0 for all
    private Integer role;   // 0 is shipping voucher, 1 is product voucher
    private Float voucherValue;
    private Integer count;
    private Date createAt;
    private Date updateAt;
}
