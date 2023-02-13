package com.sheppo.eshopproduct.repository;

import com.sheppo.eshopproduct.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
