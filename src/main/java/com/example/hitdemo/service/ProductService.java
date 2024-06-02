package com.example.hitdemo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.hitdemo.exception.ProductException;
import com.example.hitdemo.exception.SearchException;
import com.example.hitdemo.model.Product;
import com.example.hitdemo.model.query.SearchEvent;

public interface ProductService {
	Product saveProduct(Product product) throws ProductException;

	Product updateProduct(Product product) throws ProductException;

	Page<Product> search(SearchEvent search, Pageable pageable) throws SearchException;

	void deleteProduct(String productId) throws ProductException;

}
