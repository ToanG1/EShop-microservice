package com.sheppo.eshopproduct.User.service;

import com.sheppo.eshopproduct.User.dto.Product.Request.FindProductRequest;
import com.sheppo.eshopproduct.User.dto.Product.Response.ProductDto;
import com.sheppo.eshopproduct.User.dto.Product.Response.ProductResponse;
import com.sheppo.eshopproduct.model.Product;
import com.sheppo.eshopproduct.repository.ProductRepository;
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

import java.util.List;

@Service("userProductService")
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final ProductRepository productRepository;

    private ProductDto mapToProductRespone(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .storeId(product.getStoreId())
                .listImages(product.getListImages())
                .category(product.getCategory())
                .listStyle(product.getListStyle())
                .rating(product.getRating())
                .sales(product.getSales())
                .build();
    }

    public ProductResponse find(FindProductRequest productRequest) {
        Query query = new Query();

        if (productRequest.getSize() != null && productRequest.getCurrentPage() != null) {
            Pageable pageable = PageRequest.of(productRequest.getCurrentPage(), productRequest.getSize());
            query.with(pageable);
        }

        query.addCriteria(Criteria.where("is_active").is(true).and("is_selling").is(true));

        if (productRequest.getName() != null)
            query.addCriteria(Criteria.where("name").regex(productRequest.getName()));
        if (productRequest.getProductId() != null)
            query.addCriteria(Criteria.where("id").is(productRequest.getProductId()));
        if (productRequest.getCategoryId() != null)
            query.addCriteria(Criteria.where("category.id").is(productRequest.getCategoryId()));
        if (productRequest.getListStyleId() != null)
            query.addCriteria(Criteria.where("listStyle.id").in(productRequest.getListStyleId()));
        if (productRequest.getStoreId()!= null)
            query.addCriteria(Criteria.where("storeId").is(productRequest.getStoreId()));
        if (productRequest.getIsBestRating() != null) {
            if (productRequest.getIsBestRating()) query.with(Sort.by(Sort.Direction.DESC, "rating"));
        }
        if (productRequest.getIsBestSelling() != null) {
            if (productRequest.getIsBestSelling()) query.with(Sort.by(Sort.Direction.DESC, "sales"));
        }
        if (productRequest.getIsNewest() != null) {
            if (productRequest.getIsNewest()) query.with(Sort.by(Sort.Direction.DESC, "createAt"));
        }
        if (productRequest.getIsPriceIncre() != null) {
            if (productRequest.getIsPriceIncre()) query.with(Sort.by(Sort.Direction.ASC, "price"));
            else query.with(Sort.by(Sort.Direction.DESC, "price"));
        }

        return ProductResponse.builder()
                .productDtoList(mongoTemplate.find(query, Product.class).stream().map(this::mapToProductRespone).toList())
                .currentPage(productRequest.getCurrentPage())
                .totalPage(productRequest.getSize() != null ?
                        ((((int) mongoTemplate.count(query, Product.class)) / productRequest.getSize()) + 1) : 1)
                .size(productRequest.getSize())
                .build();
    }

    public List<ProductDto> findByProductIdList(List<String> productIdList){
        return productRepository.findAllById(productIdList).stream().map(this::mapToProductRespone).toList();
    }

}
