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

package com.onlinepayments.client.android.exampleapp.render.product;

import android.view.ViewGroup;

import com.onlinepayments.sdk.client.android.domain.paymentProduct.BasicPaymentProduct;

/**
 * Defines the rendering of payment products interface
 */
public interface RenderPaymentItemInterface {

    /***
     * Renders a PaymentProduct by the data in the product.
     * This PaymentProduct is added to the given parent
     *
     * @param product, PaymentProduct containing all data for the PaymentProduct
     * @param parent, the ViewGroup to which the rendered PaymentProduct is added
     */
    void renderPaymentItem(BasicPaymentProduct product, ViewGroup parent);
}
