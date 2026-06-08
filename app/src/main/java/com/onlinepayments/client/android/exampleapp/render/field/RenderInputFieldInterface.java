/*
 * Do not remove or alter the notices in this preamble.
 *
 * This software is owned by Worldline and may not be be altered, copied, reproduced, republished, uploaded, posted, transmitted or distributed in any way, without the prior written consent of Worldline.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.render.field;

import android.view.View;
import android.view.ViewGroup;

import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.domain.PaymentContextWithAmount;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.productField.PaymentProductField;

/**
 * Defines the rendering of input fields interface
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
        PaymentContextWithAmount paymentContext
    );
}
