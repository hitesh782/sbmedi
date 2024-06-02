package com.example.hitdemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.hitdemo.exception.ProductException;
import com.example.hitdemo.exception.SearchException;
import com.example.hitdemo.model.Product;
import com.example.hitdemo.model.query.SearchEvent;
import com.example.hitdemo.service.ProductService;

@RequestMapping(value = "/products")
@RestController
public class ProductController {
	@Autowired
	private ProductService productService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Product createProduct(@RequestBody Product product) throws ProductException {
		return this.productService.saveProduct(product);
	}

	@PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Product updaProduct(@RequestBody Product product) throws ProductException {
		return this.productService.saveProduct(product);
	}

	@PostMapping(value = "/criteria-search")
	@ResponseStatus(HttpStatus.OK)
	public Page<Product> search(@RequestBody final SearchEvent search, final Pageable pageable) throws SearchException {
		return this.productService.search(search, pageable);
	}

	@DeleteMapping(value = "{productId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteProduct(@PathVariable String productId) throws ProductException {
		this.productService.deleteProduct(productId);
	}

}
