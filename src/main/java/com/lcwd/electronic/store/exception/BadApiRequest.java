package com.lcwd.electronic.store.exception;

import com.sun.net.httpserver.Authenticator;

public class BadApiRequest extends RuntimeException {

    public BadApiRequest(String message, boolean success) {
        super(message);
    }

    public BadApiRequest() {
        super("Bad Request..!!");
    }
}
