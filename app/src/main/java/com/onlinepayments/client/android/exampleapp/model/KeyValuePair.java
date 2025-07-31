package com.onlinepayments.client.android.exampleapp.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Pojo that represents a ShowData KeyValuePair
 * <p>
 * Copyright 2020 Global Collect Services B.V
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
