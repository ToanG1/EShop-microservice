package com.sheppo.OrderService.Admin.dto.Voucher.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherResponse {
    private List<VoucherDto> voucherDtoList;
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
}
