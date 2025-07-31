package com.onlinepayments.client.android.exampleapp.exception;

import java.io.Serial;

/**
 * Copyright 2020 Global Collect Services B.V
 */
public class IinStatusNotKnownException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8536329875217499493L;

    public IinStatusNotKnownException() {
        super();
    }

    public IinStatusNotKnownException(String message) {
        super(message);
    }

    public IinStatusNotKnownException(Throwable t) {
        super(t);
    }

    public IinStatusNotKnownException(String message, Throwable t) {
        super(message, t);
    }
}
