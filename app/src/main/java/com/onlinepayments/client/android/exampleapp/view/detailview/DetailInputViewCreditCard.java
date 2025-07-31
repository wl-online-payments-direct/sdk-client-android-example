package com.onlinepayments.client.android.exampleapp.view.detailview;

import android.view.View;

import com.onlinepayments.client.android.exampleapp.render.iinlookup.IinLookupTextWatcher;
import com.onlinepayments.client.android.exampleapp.render.persister.IinDetailsPersister;
import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;
import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentItem;

import java.util.List;

/**
 * Interface for the DetailInputView that has extra functionality for card payment products
 * <p>
 * Copyright 2020 Global Collect Services B.V
 * </p>
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

    void renderPaymentProductLogoByIdInCreditCardField(PaymentItem paymentItem);

    void removeDrawableInEditText();

    void renderCoBrandNotification(
        List<BasicPaymentItem> paymentProductsAllowedInContext, View.OnClickListener listener
    );

    void removeCoBrandNotification();
}
