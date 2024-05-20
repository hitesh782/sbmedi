package com.example.hitdemo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.hitdemo.model.Product;

public interface ProductRepo extends MongoRepository<Product,String> {

}
