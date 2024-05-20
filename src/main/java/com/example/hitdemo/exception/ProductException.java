package com.example.hitdemo.exception;

public class ProductException extends MpsBooksServiceException {
private static final long serialVersionUID = 1L;
	
	public ProductException(final String message) {
		super(message);
	}
	
	public ProductException(final Throwable cause) {
		super(cause);
	}
	
	public ProductException(final String message, final Throwable cause) {
		super(message,cause);
	}
}
