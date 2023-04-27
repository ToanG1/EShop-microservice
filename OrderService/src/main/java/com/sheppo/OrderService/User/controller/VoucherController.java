package com.sheppo.OrderService.User.controller;

import com.sheppo.OrderService.User.dto.Voucher.Response.VoucherDto;
import com.sheppo.OrderService.User.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("UserVoucherController")
@RequestMapping("api/order/user/voucher")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VoucherDto> findAllVoucher (){
        return voucherService.findVoucher();
    }

}
