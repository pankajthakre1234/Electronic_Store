package com.lcwd.electronic.store.exception;

public class BadApiRequestException extends RuntimeException {

    public BadApiRequestException(String message, boolean success) {
        super(message);
    }

    public BadApiRequestException() {
        super("Bad Request..!!");
    }
}
