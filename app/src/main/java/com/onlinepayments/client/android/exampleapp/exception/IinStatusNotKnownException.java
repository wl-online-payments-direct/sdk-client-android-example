/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.exception;

import java.io.Serial;

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
