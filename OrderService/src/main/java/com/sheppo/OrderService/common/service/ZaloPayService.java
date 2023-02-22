package com.sheppo.OrderService.common.service;

import com.sheppo.OrderService.common.dto.ZaloAPI.Response.ZaloOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ZaloPayService {

    public String createOrder(Long id){
        ZaloOrderResponse response = new ZaloOrderResponse();
        response.setOrderUrl("https://zalopay.vn/payfororder"+id);
        return response.getOrderUrl();
    }
}
