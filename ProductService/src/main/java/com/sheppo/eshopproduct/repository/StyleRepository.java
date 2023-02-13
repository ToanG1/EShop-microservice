package com.sheppo.eshopproduct.repository;

import com.sheppo.eshopproduct.model.Style;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StyleRepository extends MongoRepository<Style, String> {
}
