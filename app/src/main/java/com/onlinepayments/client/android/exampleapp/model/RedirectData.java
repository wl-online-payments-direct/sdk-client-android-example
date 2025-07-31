package com.onlinepayments.client.android.exampleapp.model;

/**
 * Pojo that contains redirection information for redirect payment products
 * <p>
 * Copyright 2020 Global Collect Services B.V
 * </p>
 */
public class RedirectData {

    private String redirectURL;
    private String RETURNMAC;

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getRETURNMAC() {
        return RETURNMAC;
    }

    public void setRETURNMAC(String RETURNMAC) {
        this.RETURNMAC = RETURNMAC;
    }
}
