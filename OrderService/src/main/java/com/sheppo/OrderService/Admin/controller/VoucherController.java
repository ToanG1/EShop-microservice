package com.sheppo.OrderService.Admin.controller;

import com.sheppo.OrderService.Admin.dto.Voucher.Request.CreateVoucherRequest;
import com.sheppo.OrderService.Admin.dto.Voucher.Request.FindVoucherRequest;
import com.sheppo.OrderService.Admin.dto.Voucher.Request.UpdateVoucherRequest;
import com.sheppo.OrderService.Admin.dto.Voucher.Response.VoucherResponse;
import com.sheppo.OrderService.Admin.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin/voucher")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVoucher(@RequestBody CreateVoucherRequest createVoucherRequest){
        voucherService.createVoucher(createVoucherRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VoucherResponse findVoucher(@RequestBody FindVoucherRequest findVoucherRequest){
        return voucherService.findVoucher(findVoucherRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateVoucher(@RequestBody UpdateVoucherRequest updateVoucherRequest){
        voucherService.updateVoucher(updateVoucherRequest);
    }

    @PutMapping("active")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateActive(@RequestParam Long id){
        voucherService.updateActive(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteVoucher(@RequestParam Long id){
        voucherService.deleteVoucher(id);
    }
}
