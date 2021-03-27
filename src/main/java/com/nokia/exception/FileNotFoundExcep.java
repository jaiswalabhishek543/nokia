package com.nokia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author jaisw
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundExcep extends RuntimeException {
	public FileNotFoundExcep(String message) {
		super(message);
	}

	public FileNotFoundExcep(String message, Throwable cause) {
		super(message, cause);
	}
}