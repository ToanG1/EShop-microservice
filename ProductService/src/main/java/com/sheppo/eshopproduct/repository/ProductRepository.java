package com.sheppo.eshopproduct.repository;

import com.sheppo.eshopproduct.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
}
