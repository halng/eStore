package com.e.store.auth.exception;

public class AuthenException extends RuntimeException {

	public AuthenException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AuthenException(String msg) {
		super(msg);
	}

}
