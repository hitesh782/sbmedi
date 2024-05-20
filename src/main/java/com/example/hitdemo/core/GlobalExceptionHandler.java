package com.example.hitdemo.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.hitdemo.exception.MpsBooksErrorResponse;
import com.example.hitdemo.exception.ProductException;
import com.example.hitdemo.exception.ProductNotFoundException;
import com.example.hitdemo.exception.ProductValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	  @ExceptionHandler(ProductException.class)
	  public ResponseEntity<MpsBooksErrorResponse> handleDiscountException(final ProductException ex) {

		return new ResponseEntity<MpsBooksErrorResponse>(
				new MpsBooksErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR
				);
	  }

	  @ExceptionHandler(ProductValidationException.class)
	  public ResponseEntity<MpsBooksErrorResponse> handleDiscountValidationException(
	      final ProductValidationException ex) {

	    return new ResponseEntity<>(
	        new MpsBooksErrorResponse(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage()),
	        HttpStatus.PRECONDITION_FAILED);
	  }

	  @ExceptionHandler(ProductNotFoundException.class)
	  public ResponseEntity<MpsBooksErrorResponse> handleDiscountNotFoundException(
	      final ProductNotFoundException ex) {

	    return new ResponseEntity<>(
	        new MpsBooksErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
	        HttpStatus.NOT_FOUND);
	  }
}
