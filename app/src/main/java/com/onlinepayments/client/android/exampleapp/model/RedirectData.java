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

/**
 * Pojo that contains redirection information for redirect payment products
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
