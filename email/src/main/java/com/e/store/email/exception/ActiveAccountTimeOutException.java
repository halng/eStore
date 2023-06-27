package com.e.store.email.exception;

public class ActiveAccountTimeOutException extends RuntimeException {

    public ActiveAccountTimeOutException() {
        super();
    }

    public ActiveAccountTimeOutException(String msg) {
        super(msg);
    }

}
