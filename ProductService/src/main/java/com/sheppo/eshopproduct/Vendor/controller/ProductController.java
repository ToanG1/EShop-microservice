package com.sheppo.eshopproduct.Vendor.controller;

import com.sheppo.eshopproduct.Vendor.dto.Product.Request.*;
import com.sheppo.eshopproduct.Vendor.dto.Product.Response.ProductListResponse;
import com.sheppo.eshopproduct.Vendor.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("vendorProductController")
@RequestMapping("api/product/vendor/product")
@RequiredArgsConstructor
public class ProductController {
    public final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody CreateProductRequest createProductRequest){
        productService.createProduct(createProductRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductListResponse find(@RequestBody FindProductRequest findProductRequest){
        return productService.find(findProductRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody UpdateProductRequest updateProductRequest){
        productService.updateProduct(updateProductRequest);
    }

    @PutMapping("/afterOrder")
    @ResponseStatus(HttpStatus.OK)
    public void AfterOrder(@RequestParam String productId, Integer quantity){
        productService.AfterOrder(productId, quantity);
    }

    @PutMapping ("/switchSelling")
    @ResponseStatus(HttpStatus.OK)
    public void toggleIs_Selling(@RequestBody UpdateProductRequest updateProductRequest){
        productService.toggleIs_Selling(updateProductRequest);
    }

    @PutMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    public void importProduct(@RequestBody ImportProductRequest importProductRequest){
        productService.importProduct(importProductRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@RequestBody DeleteProductRequest deleteProductRequest){
        productService.deleteProduct(deleteProductRequest);
    }
}
