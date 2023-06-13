package com.e.store.auth.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException () {
        super();
    }

    public BadRequestException (String message) {
        super(message);
    }
}
