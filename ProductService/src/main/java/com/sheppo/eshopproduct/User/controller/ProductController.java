package com.sheppo.eshopproduct.User.controller;

import com.sheppo.eshopproduct.User.dto.Product.Request.FindProductRequest;
import com.sheppo.eshopproduct.User.dto.Product.Response.ProductDto;
import com.sheppo.eshopproduct.User.dto.Product.Response.ProductResponse;
import com.sheppo.eshopproduct.User.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userProductController")
@RequestMapping("api/product/user/product")
@RequiredArgsConstructor
public class ProductController {

    public final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse find(@RequestBody FindProductRequest productRequest) {
        return productService.find(productRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto findById(@PathVariable String id){
        return productService.findById(id);
    }

    @GetMapping("/listProduct")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findByProductIdList(@RequestParam List<String> productIdlist) {
        return productService.findByProductIdList(productIdlist);
    }

    @PostMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public int countProduct(@RequestBody FindProductRequest productRequest) {
        productRequest.setSize(null);
        productRequest.setCurrentPage(null);
        int countProduct = productService.find(productRequest).getProductDtoList().size();
        return countProduct;
    }


}
