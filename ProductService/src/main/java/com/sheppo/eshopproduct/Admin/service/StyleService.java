package com.sheppo.eshopproduct.Admin.service;

import com.sheppo.eshopproduct.Admin.dto.Style.Request.CreateStyleRequest;
import com.sheppo.eshopproduct.Admin.dto.Style.Request.FindStyleRequest;
import com.sheppo.eshopproduct.Admin.dto.Style.Response.StyleDto;
import com.sheppo.eshopproduct.Admin.dto.Style.Response.StyleResponse;
import com.sheppo.eshopproduct.model.Category;
import com.sheppo.eshopproduct.model.Style;
import com.sheppo.eshopproduct.repository.CategoryRepository;
import com.sheppo.eshopproduct.repository.StyleRepository;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StyleService {

    @Autowired
    private final MongoTemplate mongoTemplate;

    private final CategoryRepository categoryRepository;

    private final StyleRepository styleRepository;

    public void createStyle(CreateStyleRequest createStyleRequest){
        List<Category> categories = categoryRepository.findAllById(createStyleRequest.getListCategoryId());
        Style style = Style.builder()
                .name(createStyleRequest.getName())
                .listCategory(categories)
                .createAt(new Date())
                .updateAt(new Date())
                .build();
        styleRepository.save(style);
        log.info("Style {} is saved successfully", style.getId());
    }

    public StyleResponse findAll(FindStyleRequest findStyleRequest){
        Query query = new Query();

        Pageable pageable = PageRequest.of(findStyleRequest.getCurrentPage(), findStyleRequest.getSize());
        query.with(pageable);
        if (findStyleRequest.getId() != null) query.addCriteria(Criteria.where("id").is(findStyleRequest.getId()));
        if (findStyleRequest.getName() != null)
            query.addCriteria(Criteria.where("name").regex(findStyleRequest.getName()));
        if (findStyleRequest.getIsNewest() != null){
            if (findStyleRequest.getIsNewest()) query.with(Sort.by(Sort.Direction.DESC, "createAt"));
            else query.with(Sort.by(Sort.Direction.ASC, "createAt"));
        }
        if (findStyleRequest.getListCategoryId() != null){
            query.addCriteria(Criteria.where("listCategory.id").in(findStyleRequest.getListCategoryId()));
        }
        return StyleResponse.builder()
                .styleDtoList(mongoTemplate.find(query, Style.class).stream().map(this::mapToStyleResponse).toList())
                .currentPage(findStyleRequest.getCurrentPage())
                .totalPage((int) Math.ceil((float) mongoTemplate.count(query, Style.class) / findStyleRequest.getSize()))
                .size(findStyleRequest.getSize())
                .build();
    }

    private StyleDto mapToStyleResponse(Style style) {
        return StyleDto.builder()
                .id(style.getId())
                .name(style.getName())
                .listCategory(style.getListCategory())
                .createAt(style.getCreateAt())
                .updateAt(style.getUpdateAt())
                .build();
    }

}
