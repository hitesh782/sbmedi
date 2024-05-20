package com.example.hitdemo.exception;

public class MpsBooksServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public MpsBooksServiceException(final String message) {
		super(message);
	}
	
	public MpsBooksServiceException(final Throwable cause) {
		super(cause);
	}
	
	public MpsBooksServiceException(final String message, final Throwable cause) {
		super(message,cause);
	}
}
