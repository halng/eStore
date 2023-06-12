package com.e.store.auth.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException () {
        super();
    }

    public NotFoundException(String msg){
        super(msg);
    }
}
