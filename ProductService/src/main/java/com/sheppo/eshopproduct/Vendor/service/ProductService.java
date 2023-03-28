package com.sheppo.eshopproduct.Vendor.service;

import com.sheppo.eshopproduct.Vendor.dto.Product.Request.*;
import com.sheppo.eshopproduct.Vendor.dto.Product.Response.ProductDto;
import com.sheppo.eshopproduct.Vendor.dto.Product.Response.ProductListResponse;
import com.sheppo.eshopproduct.model.Category;
import com.sheppo.eshopproduct.model.Product;
import com.sheppo.eshopproduct.repository.CategoryRepository;
import com.sheppo.eshopproduct.repository.ProductRepository;
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
import java.util.Optional;

@Service("vendorProductService")
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public final CategoryRepository categoryRepository;

    public final StyleRepository styleRepository;

    public final ProductRepository productRepository;

    public void createProduct(CreateProductRequest createProductRequest) {
        Product product = Product.builder()
                .name(createProductRequest.getName())
                .description(createProductRequest.getDescription())
                .quantity(0)
                .price(createProductRequest.getPrice())
                .storeId(createProductRequest.getStoreId())
                .isSelling(false)
                .isActive(false)
                .listImages(createProductRequest.getListImages())
                .category(mongoTemplate.findById(createProductRequest.getCategoryId(), Category.class))
                .listStyle(styleRepository.findAllById(createProductRequest.getListStyleId()))
                .rating(0)
                .sales(0)
                .createAt(new Date())
                .updateAt(new Date())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved succesfully", product.getId());
    }


    public ProductListResponse find(FindProductRequest productRequest) {
        Query query = new Query();

        Pageable pageable = PageRequest.of(productRequest.getCurrentPage(), productRequest.getSize());
        query.with(pageable);
        if (productRequest.getIs_active() != null)
            query.addCriteria(Criteria.where("is_active").is(productRequest.getIs_active()));
        if (productRequest.getIs_selling() != null)
            query.addCriteria(Criteria.where("is_selling").is(productRequest.getIs_selling()));


        if (productRequest.getName() != null) query.addCriteria(Criteria.where("name").regex(productRequest.getName()));
        if (productRequest.getCategoryId() != null)
            query.addCriteria(Criteria.where("category.id").is(productRequest.getCategoryId()));
        if (productRequest.getListStyleId() != null)
            query.addCriteria(Criteria.where("listStyle.id").in(productRequest.getListStyleId()));
        if (productRequest.getStoreId() != null)
            query.addCriteria(Criteria.where("storeId").is(productRequest.getStoreId()));
        if (productRequest.getIsBestRating() != null) {
            if (productRequest.getIsBestRating())
                query.with(Sort.by(Sort.Direction.DESC, "rating"));
            else query.with(Sort.by(Sort.Direction.ASC, "rating"));
        }
        if (productRequest.getIsBestSelling() != null) {
            if (productRequest.getIsBestSelling())
                query.with(Sort.by(Sort.Direction.DESC, "sales"));
            else query.with(Sort.by(Sort.Direction.ASC, "sales"));
        }
        if (productRequest.getIsNewest() != null) {
            if (productRequest.getIsNewest())
                query.with(Sort.by(Sort.Direction.DESC, "createAt"));
            else query.with(Sort.by(Sort.Direction.ASC, "createAt"));
        }
        if (productRequest.getIsPriceIncre() != null) {
            if (productRequest.getIsPriceIncre()) query.with(Sort.by(Sort.Direction.ASC, "price"));
            else query.with(Sort.by(Sort.Direction.DESC, "price"));
        }

        List<Product> productList = mongoTemplate.find(query, Product.class);
        List<ProductDto> productDtoList = productList.stream().map(this::mapToProductRespone).toList();
        return ProductListResponse.builder()
                .productDtoList(productDtoList)
                .currentPage(productRequest.getCurrentPage())
                .size(productRequest.getSize())
                .totalPage((int) Math.ceil((float) mongoTemplate.count(query, Product.class) / productRequest.getSize()))
                .build();
    }

    private ProductDto mapToProductRespone(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .storeId(product.getStoreId())
                .is_selling(product.getIsSelling())
                .is_active(product.getIsActive())
                .listImages(product.getListImages())
                .category(product.getCategory())
                .listStyle(product.getListStyle())
                .rating(product.getRating())
                .sales(product.getSales())
                .createAt(product.getCreateAt())
                .updateAt(product.getUpdateAt())
                .build();
    }

    public void updateProduct(UpdateProductRequest updateProductRequest) {
        Optional<Product> product = productRepository.findById(updateProductRequest.getId());
        product.ifPresentOrElse(
                updatedProduct -> {
                    updatedProduct.setName(updateProductRequest.getName() != null ?
                            updateProductRequest.getName() : updatedProduct.getName());
                    updatedProduct.setDescription(updateProductRequest.getDescription() != null ?
                            updateProductRequest.getDescription() : updatedProduct.getDescription());
                    updatedProduct.setPrice(updateProductRequest.getPrice() != null ?
                            updateProductRequest.getPrice() : updatedProduct.getPrice());
                    updatedProduct.setListImages(updateProductRequest.getListImages() != null ?
                            updateProductRequest.getListImages() : updatedProduct.getListImages());
                    if (updateProductRequest.getCategory() != null)
                        categoryRepository.findById(updateProductRequest.getCategory())
                                .ifPresent(category -> {
                                    updatedProduct.setCategory(category);
                                });
                    updatedProduct.setListStyle(updateProductRequest.getListStyle() != null ?
                            styleRepository.findAllById(updateProductRequest.getListStyle()) : updatedProduct.getListStyle());
                    updatedProduct.setIsActive(false);
                    updatedProduct.setIsSelling(false);
                    updatedProduct.setUpdateAt(new Date());
                    productRepository.save(updatedProduct);
                    log.info("Product {} is updated successfully", updatedProduct.getId());
                },
                () -> {
                    log.info("Product {} is not avalable", updateProductRequest.getId());
                });
    }

    public void importProduct(ImportProductRequest importProductRequest) {
        Optional<Product> product = productRepository.findById(importProductRequest.getId());
        product.ifPresentOrElse(updatedProduct -> {
            updatedProduct.setQuantity(importProductRequest.getQuantity() != null ?
                    importProductRequest.getQuantity() : updatedProduct.getQuantity());
            updatedProduct.setUpdateAt(new Date());
            productRepository.save(updatedProduct);
            log.info("Product {} is imported successfully", updatedProduct.getQuantity());
        }, () -> {
            log.info("Product {} is not available", importProductRequest.getId());
        });
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

    public void changeIs_Selling(UpdateProductRequest updateProductRequest) {
        productRepository.findById(updateProductRequest.getId()).ifPresentOrElse(updatedProduct -> {
                    updatedProduct.setIsSelling(!updatedProduct.getIsSelling());
                    productRepository.save(updatedProduct);
                    log.info("Switching selling mode of {} successfully", updatedProduct.getId());
                },
                () -> {
                    log.info("Product {} is not available", updateProductRequest.getId());
                });
    }
    public void minusQuantityAfterOrder(String productId, int quantity){
        productRepository.findById(productId).ifPresentOrElse(
                product -> {
                    product.setQuantity(product.getQuantity() - quantity);
                    productRepository.save(product);
                    log.info("Product {} quantity is minus {} successfully", productId, quantity);
                },
                () -> log.info("Product {} is not available", productId)
        );
    }
}
