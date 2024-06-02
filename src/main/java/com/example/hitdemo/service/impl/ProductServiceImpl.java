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

		log.info("inside save product", product);

		if (StringUtils.isEmpty(product.getName())) {
			throw new ProductValidationException("Product name must not be null or empty");
		}
		if (product.getCategory() == null) {
			throw new ProductValidationException("category can not be empty");
		}
		// Check if a product with the same name already exists
		Optional<Product> existingProduct = productRepo.findByName(product.getName());
		if (existingProduct.isPresent()) {
			throw new ProductValidationException("Product with the same name already exists");
		}
		return this.productRepo.save(product);

	}

	@Override
	public Product updateProduct(Product product) throws ProductException {

		Optional<Product> optionalProduct = this.productRepo.findById(product.getId());

		if (optionalProduct.isPresent()) {
			Product existingProduct = optionalProduct.get();

			if (StringUtils.isEmpty(product.getName())) {
				throw new ProductValidationException("Product name must not be null or empty");
			}

			if (product.getCategory() == null) {
				throw new ProductValidationException("Category cannot be empty");
			}

			// Check if the name has changed
			if (!existingProduct.getName().equals(product.getName())) {
				throw new ProductValidationException("Product name cannot be changed");
			}

			// Proceed with the update
			Product updatedProduct = this.productRepo.save(product);
			return updatedProduct;
		} else {
			throw new ProductNotFoundException("No product exists with the requested product ID: " + product.getId());
		}

//		Optional<Product> optionalProduct = this.productRepo.findById(product.getId());
//		
//		if(optionalProduct.isPresent()) {
//			if(StringUtils.isEmpty(product.getName())) {
//				throw new ProductValidationException("Product name must not be null or empty");
//			}
//			if(product.getCategory()==null) {
//				throw new ProductValidationException("category can not be empty");
//			}
//			Product newProduct = this.productRepo.save(product);
//			return newProduct;
//		}else {
//			throw new ProductNotFoundException("No Product exist with requested product id: "+product.getId());
//		}
	}

	@Override
	public Page<Product> search(SearchEvent search, Pageable pageable) throws SearchException {
		Query query = null;
		query = QueryBuilder.buildSearchQuery(search, pageable);
		query.collation(Collation.of("en").strength(Collation.ComparisonLevel.secondary()));
		log.info("query for product search is: ", query.toString());
		return new PageImpl<>(this.mongoOperations.find(query, Product.class), pageable,
				this.mongoOperations.count(query, Product.class));
	}

	@Override
	public void deleteProduct(String productId) throws ProductException {
		Optional<Product> optProduct = this.productRepo.findById(productId);
		if (optProduct.isPresent()) {
			Product product = optProduct.get();

			if (product.isDeleted() == true) {
				log.error("product is already deleted with productId {}", productId);
				throw new ProductValidationException("product is already deleted with productId: " + productId);
			}

			product.setActive(false);
			product.setDeleted(true);
			this.productRepo.save(product);
		} else {
			throw new ProductNotFoundException("Product not found with id: " + productId);
		}

	}

}
