package com.example.UserService.Vendor.controller;

import com.example.UserService.Vendor.dto.Store.Request.CreateStoreRequest;
import com.example.UserService.Vendor.dto.Store.Request.FindStoreRequest;
import com.example.UserService.Vendor.dto.Store.Response.ListStoreResponse;
import com.example.UserService.Vendor.dto.Store.Response.StoreResponse;
import com.example.UserService.Vendor.dto.Store.Request.UpdateStoreRequest;
import com.example.UserService.Vendor.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("VendorStoreController")
@RequestMapping("api/user/vendor/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createStore(@RequestBody CreateStoreRequest createStoreRequest){
        storeService.createStore(createStoreRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StoreResponse findStore(@RequestParam Long id){
        return storeService.findStore(id);
    }

    @GetMapping("/listStore")
    @ResponseStatus(HttpStatus.OK)
    public ListStoreResponse findStoreByOwner(@RequestBody FindStoreRequest findStoreRequest){
        System.out.println(findStoreRequest.getOwnerUid());
        return storeService.findStore(findStoreRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateStore(@RequestBody UpdateStoreRequest updateStoreRequest){
        storeService.updateStore(updateStoreRequest);
    }

    @PutMapping("/open")
    @ResponseStatus(HttpStatus.OK)
    public void changeOpenStatus(@RequestParam Long id){
        storeService.changeOpenStatus(id);
    }
}
