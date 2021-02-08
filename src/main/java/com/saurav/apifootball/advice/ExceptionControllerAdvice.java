package com.saurav.apifootball.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.saurav.apifootball.exception.BadRequestException;
import com.saurav.apifootball.exception.RestApiError;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<RestApiError> handleBadRequestException(Exception ex) {
		log.error("Bad request exception caught : ", ex);
		return new ResponseEntity<>(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ Exception.class, HttpClientErrorException.class })
	public ResponseEntity<RestApiError> handleException(Exception ex) {
		log.error("Rest api exception : ", ex);
		return new ResponseEntity<>(new RestApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
