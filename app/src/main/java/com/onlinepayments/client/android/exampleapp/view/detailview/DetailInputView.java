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

package com.onlinepayments.client.android.exampleapp.view.detailview;

import android.view.View;

import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.client.android.exampleapp.render.persister.InputValidationPersister;
import com.onlinepayments.client.android.exampleapp.view.GeneralView;
import com.onlinepayments.sdk.client.android.domain.PaymentContextWithAmount;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.PaymentProduct;
import com.onlinepayments.sdk.client.android.domain.validation.ValidationErrorMessage;

/**
 * Interface for the DetailInputView
 */
public interface DetailInputView extends GeneralView {

    void renderDynamicContent(
        InputDataPersister inputDataPersister,
        PaymentContextWithAmount paymentContext,
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
    void renderValidationMessage(
        ValidationErrorMessage validationResult,
        PaymentProduct paymentProduct
    );

    void renderValidationMessages(
        InputValidationPersister inputValidationPersister,
        PaymentProduct paymentProduct
    );

    void hideTooltipAndErrorViews(InputValidationPersister inputValidationPersister);

    void setFocusAndCursorPosition(String fieldId, int cursorPosition);

    View getViewWithFocus();
}
