package com.example.UserService.Admin.controller;

import com.example.UserService.Admin.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("AdminStoreController")
@RequestMapping("api/user/admin/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateActiveStore(@RequestParam Long id){
        storeService.updateActiveStore(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteStore(@RequestParam Long id) { storeService.deleteStore(id);}
}
