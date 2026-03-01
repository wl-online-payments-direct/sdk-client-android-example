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
