package com.onlinepayments.client.android.exampleapp.model;

import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentProductField;

import java.util.List;

/**
 * Pojo that contains MerchantAction information
 * <p>
 * Copyright 2020 Global Collect Services B.V
 * </p>
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
