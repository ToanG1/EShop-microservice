package com.sheppo.eshopproduct.repository;

import com.sheppo.eshopproduct.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
