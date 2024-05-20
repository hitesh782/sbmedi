package com.example.hitdemo.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.hitdemo.model.base.Auditable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Auditable {
	
	@NotBlank(message="Name must not be null or empty")
	private String name;
	
	private String description;
	
}
