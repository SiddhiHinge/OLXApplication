package com.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class InvalidUserException extends RuntimeException{

	private String message;
	public InvalidUserException() {
		this.message = "";
	}
	public InvalidUserException(String message) {
		this.message = message;
	}
	public String toString() {
		return "Invalid Authorization Token: " + this.message + " , Please try to login again.";
	}
}
