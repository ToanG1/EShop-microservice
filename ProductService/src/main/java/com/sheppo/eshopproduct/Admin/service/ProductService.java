package com.sheppo.eshopproduct.Admin.service;

import com.sheppo.eshopproduct.Admin.dto.Product.Request.DeleteProductRequest;
import com.sheppo.eshopproduct.Admin.dto.Product.Request.UpdateActiveProductRequest;
import com.sheppo.eshopproduct.Admin.dto.Product.Response.ProductDto;
import com.sheppo.eshopproduct.Admin.dto.Product.Request.FindProductRequest;
import com.sheppo.eshopproduct.Admin.dto.Product.Response.ProductResponse;
import com.sheppo.eshopproduct.model.Product;
import com.sheppo.eshopproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final MongoTemplate mongoTemplate;

    private final ProductRepository productRepository;

    private ProductDto mapToProductRespone(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .storeId(product.getStoreId())
                .is_selling(product.getIs_selling())
                .is_active(product.getIs_active())
                .listImages(product.getListImages())
                .category(product.getCategory())
                .listStyle(product.getListStyle())
                .sales(product.getSales())
                .rating(product.getRating())
                .createAt(product.getCreateAt())
                .updateAt(product.getUpdateAt())
                .build();
    }

    public ProductResponse findProduct(FindProductRequest findProductRequest) {
        Query query = new Query();

        Pageable pageable = PageRequest.of(findProductRequest.getCurrentPage(), findProductRequest.getSize());
        query.with(pageable);

        if (findProductRequest.getName() != null)
            query.addCriteria(Criteria.where("name").regex(findProductRequest.getName()));
        if (findProductRequest.getCategory() != null)
            query.addCriteria(Criteria.where("categoryId").is(findProductRequest.getCategory()));
        if (findProductRequest.getListStyleId() != null)
            query.addCriteria(Criteria.where("listStyleIds.id").in(findProductRequest.getListStyleId()));
        if (findProductRequest.getStoreId() != null)
            query.addCriteria(Criteria.where("storeId").is(findProductRequest.getStoreId()));
        if (findProductRequest.getIsBestRating() != null) {
            if (findProductRequest.getIsBestRating())
                query.with(Sort.by(Sort.Direction.DESC, "rating"));
            else query.with(Sort.by(Sort.Direction.ASC, "rating"));
        }
        if (findProductRequest.getIsBestSelling() != null) {
            if (findProductRequest.getIsBestSelling() && findProductRequest.getIsBestSelling() != null)
                query.with(Sort.by(Sort.Direction.DESC, "sales"));
            else query.with(Sort.by(Sort.Direction.ASC, "sales"));
        }
        if (findProductRequest.getIsNewest() != null) {
            if (findProductRequest.getIsNewest())
                query.with(Sort.by(Sort.Direction.DESC, "createAt"));
            else query.with(Sort.by(Sort.Direction.ASC, "createAt"));
        }
        if (findProductRequest.getIsPriceIncre() != null) {
            if (findProductRequest.getIsPriceIncre()) query.with(Sort.by(Sort.Direction.ASC, "price"));
            else query.with(Sort.by(Sort.Direction.DESC, "price"));
        }
        if (findProductRequest.getIs_active() != null)
            query.addCriteria(Criteria.where("is_active").is(findProductRequest.getIs_active()));
        if (findProductRequest.getIs_selling() != null)
            query.addCriteria(Criteria.where("is_selling").is(findProductRequest.getIs_selling()));

        return ProductResponse.builder()
                .productDtoList(mongoTemplate.find(query, Product.class).stream().map(this::mapToProductRespone).toList())
                .currentPage(findProductRequest.getCurrentPage())
                .totalPage(((int) mongoTemplate.count(query, Product.class) / findProductRequest.getSize()) + 1)
                .size(findProductRequest.getSize())
                .build();
    }

    public void deleteProduct(DeleteProductRequest deleteProductRequest) {
        try {
            productRepository.findById(deleteProductRequest.getId()).ifPresentOrElse(
                    product -> {
                        productRepository.deleteById(deleteProductRequest.getId());
                        log.info("Product {} is deleted successfully", deleteProductRequest.getId());
                    },
                    () -> {
                        log.info("Product {} is not available", deleteProductRequest.getId());
                    }
            );

        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    public void updateActiveProduct(UpdateActiveProductRequest updateActiveProductRequest) {
        productRepository.findById(updateActiveProductRequest.getId()).ifPresentOrElse(
                updatedProduct -> {
                    updatedProduct.setIs_active(!updatedProduct.getIs_active());
                    productRepository.save(updatedProduct);
                    log.info("{} status of product {} successfully", updatedProduct.getIs_active() ?
                            "Active" : "Unactive", updatedProduct.getId());
                },
                () -> {
                    log.info("Product {} is not available", updateActiveProductRequest.getId());
                }
        );
    }
}
