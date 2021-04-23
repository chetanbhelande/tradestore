package com.db.tradestore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.db.tradestore.exception.TradeInvalidException;

@ControllerAdvice
public class TradeControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(TradeInvalidException.class)
	public ResponseEntity<String> tradeInvalidException(final TradeInvalidException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}

}
