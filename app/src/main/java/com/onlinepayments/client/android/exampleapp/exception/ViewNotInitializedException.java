package com.onlinepayments.client.android.exampleapp.exception;

import java.io.Serial;

/**
 * Copyright 2020 Global Collect Services B.V
 */
public class ViewNotInitializedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1281137185498064367L;

    public ViewNotInitializedException() {
        super();
    }

    public ViewNotInitializedException(String message) {
        super(message);
    }

    public ViewNotInitializedException(Throwable t) {
        super(t);
    }

    public ViewNotInitializedException(String message, Throwable t) {
        super(message, t);
    }
}
