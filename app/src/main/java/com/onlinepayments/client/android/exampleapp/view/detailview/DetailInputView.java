package com.onlinepayments.client.android.exampleapp.view.detailview;

import android.view.View;

import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.client.android.exampleapp.render.persister.InputValidationPersister;
import com.onlinepayments.client.android.exampleapp.view.GeneralView;
import com.onlinepayments.sdk.client.android.model.PaymentContext;
import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentItem;
import com.onlinepayments.sdk.client.android.model.validation.ValidationErrorMessage;

/**
 * Interface for the DetailInputView
 * <p>
 * Copyright 2020 Global Collect Services B.V
 * </p>
 */
public interface DetailInputView extends GeneralView {

    void renderDynamicContent(
        InputDataPersister inputDataPersister,
        PaymentContext paymentContext,
        InputValidationPersister inputValidationPersister
    );

    void renderRememberMeCheckBox(boolean isChecked);

    void removeAllFieldViews();

    void activatePayButton();

    void deactivatePayButton();

    void showLoadDialog();

    void hideLoadDialog();

    /**
     * @noinspection unused
     */
    void renderValidationMessage(ValidationErrorMessage validationResult, PaymentItem paymentItem);

    void renderValidationMessages(
        InputValidationPersister inputValidationPersister, PaymentItem paymentItem
    );

    void hideTooltipAndErrorViews(InputValidationPersister inputValidationPersister);

    void setFocusAndCursorPosition(String fieldId, int cursorPosition);

    View getViewWithFocus();
}
