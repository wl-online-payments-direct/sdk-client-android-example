/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.render.field;

import android.view.ViewGroup;

import com.onlinepayments.sdk.client.android.domain.paymentProduct.BasicPaymentProduct;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.productField.PaymentProductField;

/**
 * Defines the rendering of tooltip interface methods
 */
public interface RenderTooltipInterface {

    // Tag for identifying tooltips elements
    String TOOLTIP_TAG = "tooltip_";

    /**
     * Renders a tooltip by the data in the PaymentProductField.
     * This tooltip is added to the given ViewGroup
     *
     * @param field,                  PaymentProductField for the tooltip
     * @param selectedPaymentProduct, the selected PaymentProduct, used for getting the correct translations
     * @param rowView,                the ViewGroup to which the rendered tooltip is added
     */
    void renderTooltip(
        PaymentProductField field,
        BasicPaymentProduct selectedPaymentProduct,
        ViewGroup rowView
    );
}
