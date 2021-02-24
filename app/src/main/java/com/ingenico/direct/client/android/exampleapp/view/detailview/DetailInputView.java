package com.ingenico.direct.client.android.exampleapp.view.detailview;


import android.view.View;

import com.ingenico.direct.client.android.exampleapp.render.persister.InputDataPersister;
import com.ingenico.direct.client.android.exampleapp.render.persister.InputValidationPersister;
import com.ingenico.direct.client.android.exampleapp.view.GeneralView;
import com.ingenico.direct.sdk.client.android.model.PaymentContext;
import com.ingenico.direct.sdk.client.android.model.paymentproduct.PaymentItem;
import com.ingenico.direct.sdk.client.android.model.validation.ValidationErrorMessage;

/**
 * Interface for the DetailInputView
 *
 * Copyright 2020 Global Collect Services B.V
 */
public interface DetailInputView extends GeneralView {

    void renderDynamicContent(InputDataPersister inputDataPersister,
                              PaymentContext paymentContext,
                              InputValidationPersister inputValidationPersister);

    void renderRememberMeCheckBox(boolean isChecked);

    void removeAllFieldViews();

    void activatePayButton();

    void deactivatePayButton();

    void showLoadDialog();

    void hideLoadDialog();

    void renderValidationMessage(ValidationErrorMessage validationResult, PaymentItem paymentItem);

    void renderValidationMessages(InputValidationPersister inputValidationPersister, PaymentItem paymentItem);

    void hideTooltipAndErrorViews(InputValidationPersister inputValidationPersister);

    void setFocusAndCursorPosition(String fieldId, int cursorPosition);

    View getViewWithFocus();
}
