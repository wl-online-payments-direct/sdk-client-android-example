/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.view.detailview;

import android.view.View;

import com.onlinepayments.client.android.exampleapp.render.iinlookup.IinLookupTextWatcher;
import com.onlinepayments.client.android.exampleapp.render.persister.IinDetailsPersister;
import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.BasicPaymentProduct;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.PaymentProduct;

import java.util.List;

/**
 * Interface for the DetailInputView that has extra functionality for card payment products
 */
public interface DetailInputViewCreditCard extends DetailInputView {
    void initializeCreditCardField(
        IinLookupTextWatcher iinLookupTextWatcher,
        InputDataPersister inputDataPersister,
        IinDetailsPersister iinDetailsPersister,
        boolean hasAccountOnFile
    );

    void renderLuhnValidationMessage();

    void renderNotAllowedInContextValidationMessage();

    void removeCreditCardValidationMessage();

    void renderPaymentProductLogoByIdInCreditCardField(PaymentProduct paymentProduct);

    void removeDrawableInEditText();

    void renderCoBrandNotification(
        List<BasicPaymentProduct> paymentProductsAllowedInContext,
        View.OnClickListener listener
    );

    void removeCoBrandNotification();
}
