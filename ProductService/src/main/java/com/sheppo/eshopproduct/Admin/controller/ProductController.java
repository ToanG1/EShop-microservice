package com.sheppo.eshopproduct.Admin.controller;

import com.sheppo.eshopproduct.Admin.dto.Product.Request.DeleteProductRequest;
import com.sheppo.eshopproduct.Admin.dto.Product.Request.UpdateActiveProductRequest;
import com.sheppo.eshopproduct.Admin.dto.Product.Response.ProductResponse;
import com.sheppo.eshopproduct.Admin.service.ProductService;
import com.sheppo.eshopproduct.Admin.dto.Product.Request.FindProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminProductController")
@RequestMapping("api/product/admin/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse findProduct(@RequestBody FindProductRequest findProductRequest){
        return productService.findProduct(findProductRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void changeActive(@RequestBody UpdateActiveProductRequest updateActiveProductRequest){
        productService.updateActiveProduct(updateActiveProductRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@RequestBody DeleteProductRequest deleteProductRequest){
        productService.deleteProduct(deleteProductRequest);
    }
}
