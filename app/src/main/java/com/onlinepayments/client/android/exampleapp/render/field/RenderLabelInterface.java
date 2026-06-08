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

import android.view.ViewGroup;
import android.widget.TextView;

import com.onlinepayments.sdk.client.android.domain.paymentProduct.BasicPaymentProduct;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.productField.PaymentProductField;

/**
 * Defines the rendering of label interface methods
 */
public interface RenderLabelInterface {

    /**
     * Renders a tooltip by the data in the PaymentProductField.
     * This label is added to the given rowView
     *
     * @param selectedPaymentProduct, the selected PaymentProduct, used for getting the correct translations
     * @param rowView,                the ViewGroup to which the rendered label is added
     * @noinspection UnusedReturnValue
     */
    TextView renderLabel(
        PaymentProductField field,
        BasicPaymentProduct selectedPaymentProduct,
        ViewGroup rowView
    );
}
