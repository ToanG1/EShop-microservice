package com.sheppo.eshopproduct.User.controller;

import com.sheppo.eshopproduct.User.dto.Product.Request.FindProductRequest;
import com.sheppo.eshopproduct.User.dto.Product.Response.ProductResponse;
import com.sheppo.eshopproduct.User.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("userProductController")
@RequestMapping("api/user/product")
@RequiredArgsConstructor
public class ProductController {

    public final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse find(@RequestBody FindProductRequest productRequest) {
        return productService.find(productRequest);
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public String find() {
        return "this is async";
    }


}
