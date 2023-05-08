package com.sheppo.eshopproduct.User.controller;

import com.sheppo.eshopproduct.User.service.CategoryService;
import com.sheppo.eshopproduct.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("UserCategoryController")
@RequiredArgsConstructor
@RequestMapping("api/product/user/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Category> findCategory(){
        return categoryService.findAll();
    }
}
