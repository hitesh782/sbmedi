package com.example.hitdemo.exception;

public class SearchException extends MpsBooksServiceException {
private static final long serialVersionUID = 1L;
	
	public SearchException(final String message) {
		super(message);
	}
	
	public SearchException(final Throwable cause) {
		super(cause);
	}
	
	public SearchException(final String message, final Throwable cause) {
		super(message,cause);
	}
}
