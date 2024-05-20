package com.example.hitdemo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Collation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.hitdemo.core.QueryBuilder;
import com.example.hitdemo.exception.ProductException;
import com.example.hitdemo.exception.ProductNotFoundException;
import com.example.hitdemo.exception.ProductValidationException;
import com.example.hitdemo.exception.SearchException;
import com.example.hitdemo.model.Product;
import com.example.hitdemo.model.query.SearchEvent;
import com.example.hitdemo.repo.ProductRepo;
import com.example.hitdemo.service.ProductService;

import io.micrometer.common.util.StringUtils;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public Product saveProduct(Product product) throws ProductException {
		
		
			Assert.notNull(product,"product object must not be null");
			if(StringUtils.isEmpty(product.getName())) {
				throw new ProductValidationException("Product name must not be null or empty");
			}
			return this.productRepo.save(product);
		
		
		
	}

	@Override
	public Product updateProduct(Product product) throws ProductException {
		
		Optional<Product> optionalProduct = this.productRepo.findById(product.getId());
		
		if(optionalProduct.isPresent()) {
			Product newProduct = this.productRepo.save(product);
			return newProduct;
		}else {
			throw new ProductNotFoundException("No Product exist with requested product id: "+product.getId());
		}
	}

	@Override
	public Page<Product> search(SearchEvent search, Pageable pageable) throws SearchException {
		Query query = null;
		query = QueryBuilder.buildSearchQuery(search, pageable);
		query.collation(Collation.of("en").strength(Collation.ComparisonLevel.secondary()));
		log.info("query for product search is: ",query.toString());
		return new PageImpl<>(this.mongoOperations.find(query,Product.class),pageable,this.mongoOperations.count(query, Product.class));
	}

	@Override
	public void deleteProduct(String productId) throws ProductException {
		Optional<Product> optProduct = this.productRepo.findById(productId);
		if(optProduct.isPresent()) {
			Product product = optProduct.get();
			product.setActive(false);
			product.setDeleted(true);
			this.productRepo.save(product);
		}
		else {
			throw new ProductNotFoundException("Product not found with id: "+productId);
		}
		
	}
	
	
}
