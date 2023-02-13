package com.example.UserService.User.controller;

import com.example.UserService.User.dto.Address.Request.CreateAddressRequest;
import com.example.UserService.User.dto.Address.Request.DeleteAddressRequest;
import com.example.UserService.User.dto.Address.Request.FindAddressRequest;
import com.example.UserService.User.dto.Address.Request.UpdateAddressRequest;
import com.example.UserService.User.dto.Address.Response.ListAddressResponse;
import com.example.UserService.User.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAddress(@RequestBody CreateAddressRequest createAddressRequest){
        addressService.createAddress(createAddressRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListAddressResponse findAllByUid(@RequestBody FindAddressRequest findAddressRequest){
        return addressService.findAllByUid(findAddressRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest){
        addressService.updateAddress(updateAddressRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteAddress(@RequestBody DeleteAddressRequest deleteAddressRequest){
        addressService.deleteAddress(deleteAddressRequest);
    }
}
