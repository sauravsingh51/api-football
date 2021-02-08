package com.saurav.apifootball.exception;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

	public BadRequestException(String ex) {
		super(ex);
	}

	public BadRequestException() {
	}
}
