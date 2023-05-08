package com.sheppo.eshopproduct.User.service;

import com.sheppo.eshopproduct.model.Category;
import com.sheppo.eshopproduct.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserCategoryService")
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;



    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
