package com.e.store.email.exception;

public class ActiveAccountException extends RuntimeException {

	public ActiveAccountException() {
		super();
	}

	public ActiveAccountException(String msg) {
		super(msg);
	}

}
