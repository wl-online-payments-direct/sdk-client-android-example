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

import com.onlinepayments.sdk.client.android.domain.paymentProduct.productField.PaymentProductField;

import java.util.List;

/**
 * Pojo that contains MerchantAction information
 */
public class MerchantAction {

    private MerchantActionType actionType;

    private RedirectData redirectData;

    private List<KeyValuePair> showData;

    private List<PaymentProductField> formFields;

    private String renderingData;

    public MerchantActionType getActionType() {
        return actionType;
    }

    public void setActionType(MerchantActionType actionType) {
        this.actionType = actionType;
    }

    public RedirectData getRedirectData() {
        return redirectData;
    }

    public void setRedirectData(RedirectData redirectData) {
        this.redirectData = redirectData;
    }

    public List<KeyValuePair> getShowData() {
        return showData;
    }

    public void setShowData(List<KeyValuePair> showData) {
        this.showData = showData;
    }

    public String getRenderingData() {
        return renderingData;
    }

    public void setRenderingData(String renderingData) {
        this.renderingData = renderingData;
    }

    public List<PaymentProductField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<PaymentProductField> formFields) {
        this.formFields = formFields;
    }
}
