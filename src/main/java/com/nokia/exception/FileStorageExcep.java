package com.nokia.exception;

/**
 * @author jaisw
 *
 */
public class FileStorageExcep extends RuntimeException {
	public FileStorageExcep(String message) {
		super(message);
	}

	public FileStorageExcep(String message, Throwable cause) {
		super(message, cause);
	}
}
