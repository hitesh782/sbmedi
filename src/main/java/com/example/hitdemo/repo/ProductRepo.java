package com.example.hitdemo.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.hitdemo.model.Product;

public interface ProductRepo extends MongoRepository<Product,String> {
	Optional<Product> findByName(String name);
}
