/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Pojo that represents a ShowData KeyValuePair
 */
public class KeyValuePair implements Serializable {

    @Serial
    private static final long serialVersionUID = -2060644958674272920L;

    private String key;
    private String value;

    public KeyValuePair() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
