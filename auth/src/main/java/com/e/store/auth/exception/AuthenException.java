package com.e.store.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenException extends AuthenticationException {

    public AuthenException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenException(String msg) {
        super(msg);
    }
}
