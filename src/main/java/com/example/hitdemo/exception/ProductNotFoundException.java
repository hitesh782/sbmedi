package com.example.hitdemo.exception;

public class ProductNotFoundException extends ProductException {
private static final long serialVersionUID = 1L;
	
	public ProductNotFoundException(final String message) {
		super(message);
	}
	
	public ProductNotFoundException(final Throwable cause) {
		super(cause);
	}
	
	public ProductNotFoundException(final String message, final Throwable cause) {
		super(message,cause);
	}
}
