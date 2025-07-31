package com.onlinepayments.client.android.exampleapp.render.field;

import android.view.View;
import android.view.ViewGroup;

import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.model.PaymentContext;
import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentProductField;

/**
 * Defines the rendering of input fields interface
 * <p>
 * Copyright 2020 Global Collect Services B.V
 */
public interface RenderInputFieldInterface {
    /***
     * Renders an input field by the data in the PaymentProductField.
     * This input field is added to the given ViewGroup
     *
     * @param field, PaymentProductField containing all data for the input field
     * @param inputDataPersister, the selected PaymentProduct, used for getting the correct translations
     * @param rowView, the ViewGroup to which the rendered input field is added
     * @param paymentContext, the paymentContext of the current payment
     *
     * @return the rendered view
     */
    View renderField(
        PaymentProductField field,
        InputDataPersister inputDataPersister,
        ViewGroup rowView,
        PaymentContext paymentContext
    );
}
