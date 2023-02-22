package com.sheppo.eshopproduct.Admin.controller;

import com.sheppo.eshopproduct.Admin.dto.Category.Request.CreateCategoryRequest;
import com.sheppo.eshopproduct.Admin.dto.Category.Request.DeleteCategoryRequest;
import com.sheppo.eshopproduct.Admin.dto.Category.Request.FindCategoryRequest;
import com.sheppo.eshopproduct.Admin.dto.Category.Response.CategoryRespone;
import com.sheppo.eshopproduct.Admin.dto.Category.Request.UpdateCategoryRequest;
import com.sheppo.eshopproduct.Admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("adminCategoryController")
@RequestMapping("api/product/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@RequestBody CreateCategoryRequest createCategoryRequest){
        categoryService.createCategory(createCategoryRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryRespone findAll(@RequestBody FindCategoryRequest findCategoryRequest){
        return categoryService.findAll(findCategoryRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateCategory(@RequestBody UpdateCategoryRequest updateCategoryRequest){
        categoryService.updateCategory(updateCategoryRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@RequestBody DeleteCategoryRequest deleteCategoryRequest){
        categoryService.deleteCategory(deleteCategoryRequest);
    }

}
