package com.onlinepayments.client.android.exampleapp.render.product;

import android.view.ViewGroup;

import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;

/**
 * Defines the rendering of payment products interface
 * <p>
 * Copyright 2020 Global Collect Services B.V
 * </p>
 */
public interface RenderPaymentItemInterface {

    /***
     * Renders a PaymentProduct by the data in the product.
     * This PaymentProduct is added to the given parent
     *
     * @param product, PaymentProduct containing all data for the PaymentProduct
     * @param parent, the ViewGroup to which the rendered PaymentProduct is added
     */
    void renderPaymentItem(BasicPaymentItem product, ViewGroup parent);
}
