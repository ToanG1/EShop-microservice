package com.sheppo.OrderService.Admin.controller;

import com.sheppo.OrderService.Admin.dto.Shipping.Request.CreateShippingRequest;
import com.sheppo.OrderService.Admin.dto.Shipping.Request.FindShippingRequest;
import com.sheppo.OrderService.Admin.dto.Shipping.Request.UpdateShippingRequest;
import com.sheppo.OrderService.Admin.dto.Shipping.Response.ShippingResponse;
import com.sheppo.OrderService.Admin.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("AdminShippingController")
@RequestMapping("api/admin/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ShippingResponse findShipping(@RequestBody FindShippingRequest findShippingRequest){
        return shippingService.findShipping(findShippingRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createShipping(@RequestBody CreateShippingRequest createShippingRequest){
        shippingService.createShipping(createShippingRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateShipping(@RequestBody UpdateShippingRequest updateShippingRequest){
        shippingService.updateShipping(updateShippingRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteShipping(@RequestParam Long id){
        shippingService.deleteShipping(id);
    }
}
