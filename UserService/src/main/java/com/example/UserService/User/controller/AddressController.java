package com.example.UserService.User.controller;

import com.example.UserService.User.dto.Address.Request.CreateAddressRequest;
import com.example.UserService.User.dto.Address.Request.DeleteAddressRequest;
import com.example.UserService.User.dto.Address.Request.FindAddressRequest;
import com.example.UserService.User.dto.Address.Request.UpdateAddressRequest;
import com.example.UserService.User.dto.Address.Response.AddressDto;
import com.example.UserService.User.dto.Address.Response.ListAddressResponse;
import com.example.UserService.User.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/user/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAddress(@RequestBody CreateAddressRequest createAddressRequest){
        addressService.createAddress(createAddressRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ListAddressResponse findAllByUid(@RequestBody FindAddressRequest findAddressRequest){
        return addressService.findAllByUid(findAddressRequest);
    }

    @GetMapping("/addressId")
    @ResponseStatus(HttpStatus.OK)
    public AddressDto findById(@RequestParam Long id ){
        return addressService.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateAddress(@RequestBody UpdateAddressRequest updateAddressRequest){
        addressService.updateAddress(updateAddressRequest);
    }

    @DeleteMapping("/{uid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAddress(@PathVariable String uid, @RequestParam Long id){
        DeleteAddressRequest deleteAddressRequest = new DeleteAddressRequest(uid, id);
        addressService.deleteAddress(deleteAddressRequest);
    }
}
