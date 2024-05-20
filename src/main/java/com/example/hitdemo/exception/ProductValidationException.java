package com.example.hitdemo.exception;

public class ProductValidationException extends ProductException {
private static final long serialVersionUID = 1L;
	
	public ProductValidationException(final String message) {
		super(message);
	}
	
	public ProductValidationException(final Throwable cause) {
		super(cause);
	}
	
	public ProductValidationException(final String message, final Throwable cause) {
		super(message,cause);
	}
}
