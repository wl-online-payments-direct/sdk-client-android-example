package com.onlinepayments.client.android.exampleapp.exception;

import java.io.Serial;

/**
 * Copyright 2020 Global Collect Services B.V
 */
public class ThirdPartyStatusNotAllowedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3271047769311985926L;

    public ThirdPartyStatusNotAllowedException() {
        super();
    }

    public ThirdPartyStatusNotAllowedException(String message) {
        super(message);
    }

    public ThirdPartyStatusNotAllowedException(Throwable t) {
        super(t);
    }

    public ThirdPartyStatusNotAllowedException(String message, Throwable t) {
        super(message, t);
    }
}
