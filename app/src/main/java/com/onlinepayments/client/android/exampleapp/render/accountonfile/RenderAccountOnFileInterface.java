package com.onlinepayments.client.android.exampleapp.render.accountonfile;

import android.view.ViewGroup;

import com.onlinepayments.sdk.client.android.model.paymentproduct.AccountOnFile;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;

/**
 * Defines the rendering of accounts on file interface
 * <p>
 * Copyright 2020 Global Collect Services B.V
 * </p>
 */
public interface RenderAccountOnFileInterface {

    /***
     * Renders account on file by the data in the PaymentProduct.
     * This AccountOnFile is added to the given parent
     *
     * @param accountOnFile, AccountOnFile containing data for the account on file rendering
     * @param paymentItem, the BasicPaymentItem belonging to the account on file
     * @param parent, the ViewGroup to which the rendered account on file is added
     */
    void renderAccountOnFile(
        AccountOnFile accountOnFile, BasicPaymentItem paymentItem, ViewGroup parent
    );
}
