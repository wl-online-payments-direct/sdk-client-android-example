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

package com.onlinepayments.client.android.exampleapp.render.accountonfile;

import android.view.ViewGroup;

import com.onlinepayments.sdk.client.android.domain.accountOnFile.AccountOnFile;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.BasicPaymentProduct;

/**
 * Defines the rendering of accounts on file interface
 */
public interface RenderAccountOnFileInterface {

    /***
     * Renders account on file by the data in the PaymentProduct.
     * This AccountOnFile is added to the given parent
     *
     * @param accountOnFile, AccountOnFile containing data for the account on file rendering
     * @param basicPaymentProduct, the BasicPaymentproduct belonging to the account on file
     * @param parent, the ViewGroup to which the rendered account on file is added
     */
    void renderAccountOnFile(
        AccountOnFile accountOnFile,
        BasicPaymentProduct basicPaymentProduct,
        ViewGroup parent
    );
}
