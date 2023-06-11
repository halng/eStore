package com.e.store.auth.exception;

public class TokenException extends RuntimeException{
    TokenException() {
        super();
    }

    public TokenException(String msg){
        super(msg);
    }

}
