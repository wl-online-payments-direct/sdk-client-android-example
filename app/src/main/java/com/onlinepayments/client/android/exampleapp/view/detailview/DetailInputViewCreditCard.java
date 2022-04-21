package com.onlinepayments.client.android.exampleapp.view.detailview;

import android.view.View;

import com.onlinepayments.client.android.exampleapp.render.iinlookup.IinLookupTextWatcher;
import com.onlinepayments.client.android.exampleapp.render.persister.InputValidationPersister;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;

import java.util.List;

/**
 * Interface for the DetailInputView that has extra functionality for card payment products
 *
 * Copyright 2020 Global Collect Services B.V
 */
public interface DetailInputViewCreditCard extends DetailInputView {
    void initializeCreditCardField(IinLookupTextWatcher iinLookupTextWatcher);

    void renderLuhnValidationMessage(InputValidationPersister inputValidationPersister);

    void renderNotAllowedInContextValidationMessage(InputValidationPersister inputValidationPersister);

    void removeCreditCardValidationMessage(InputValidationPersister inputValidationPersister);

    void renderPaymentProductLogoByIdInCreditCardField(String productId);

    void removeDrawableInEditText();

    void renderCoBrandNotification(List<BasicPaymentItem> paymentProductsAllowedInContext, View.OnClickListener listener);

    void removeCoBrandNotification();
}
