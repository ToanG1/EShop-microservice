package com.sheppo.eshopproduct.Admin.service;

import com.sheppo.eshopproduct.Admin.dto.Category.Request.CreateCategoryRequest;
import com.sheppo.eshopproduct.Admin.dto.Category.Request.DeleteCategoryRequest;
import com.sheppo.eshopproduct.Admin.dto.Category.Request.FindCategoryRequest;
import com.sheppo.eshopproduct.Admin.dto.Category.Request.UpdateCategoryRequest;
import com.sheppo.eshopproduct.Admin.dto.Category.Response.CategoryDto;
import com.sheppo.eshopproduct.Admin.dto.Category.Response.CategoryRespone;
import com.sheppo.eshopproduct.model.Category;
import com.sheppo.eshopproduct.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    @Autowired
    private final MongoTemplate mongoTemplate;
    private final CategoryRepository categoryRepository;

    public void createCategory(CreateCategoryRequest createCategoryRequest) {
        Category category = Category.builder()
                .name(createCategoryRequest.getName())
                .total(0)
                .createAt(new Date())
                .updateAt(new Date())
                .build();
        categoryRepository.save(category);
        log.info("Category {} is saved succesfully", category.getId());
    }

    public CategoryRespone findAll(FindCategoryRequest findCategoryRequest) {
        Query query = new Query();

        Pageable pageable = PageRequest.of(findCategoryRequest.getCurrentPage(), findCategoryRequest.getSize());
        query.with(pageable);

        if (findCategoryRequest.getId() != null) query.addCriteria(Criteria.where("id")
                .is(findCategoryRequest.getId()));
        if (findCategoryRequest.getName() != null) query.addCriteria(Criteria.where("name")
                .regex(findCategoryRequest.getName()));

        if (findCategoryRequest.getIsNewest() != null) {
            if (findCategoryRequest.getIsNewest()) query.with(Sort.by(Sort.Direction.DESC, "createAt"));
            else query.with(Sort.by(Sort.Direction.ASC, "createAt"));
        }
        if (findCategoryRequest.getIsTotalProductIncre() != null) {
            if (findCategoryRequest.getIsTotalProductIncre())
                query.with(Sort.by(Sort.Direction.ASC, "total"));
            else query.with(Sort.by(Sort.Direction.DESC, "total"));
        }

        return CategoryRespone.builder()
                .categoryDtoList(mongoTemplate.find(query, Category.class).stream().map(this::mapToCategoryRespone).toList())
                .currentPage(findCategoryRequest.getCurrentPage() != null ? findCategoryRequest.getCurrentPage() : 1)
                .totalPage( Math.round((int) mongoTemplate.count(query, Category.class) / findCategoryRequest.getSize()))
                .size(findCategoryRequest.getSize())
                .build();
    }

    private CategoryDto mapToCategoryRespone(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .total(category.getTotal())
                .createAt(category.getCreateAt())
                .updateAt(category.getUpdateAt())
                .build();
    }

    public void updateCategory(UpdateCategoryRequest updateCategoryRequest) {
        categoryRepository.findById(updateCategoryRequest.getId()).ifPresentOrElse(
                updatedCategory -> {
                    updatedCategory.setName(updateCategoryRequest.getName());
                    categoryRepository.save(updatedCategory);
                    log.info("Category {} is updated successfully", updatedCategory.getId());
                },
                () -> {log.info("Category {} is not available", updateCategoryRequest.getId());
                }
        );
    }

    public void deleteCategory(DeleteCategoryRequest deleteCategoryRequest) {
        categoryRepository.findById(deleteCategoryRequest.getId()).ifPresentOrElse(
                category -> {
                    categoryRepository.delete(category);
                    log.info("Category {} is deleted successfully", deleteCategoryRequest.getId());
                },
                ()->{
                    log.info("Category {} is not available", deleteCategoryRequest.getId());
                }
        );
    }
}
