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

package com.onlinepayments.client.android.exampleapp.render.validation;

import android.view.View;
import android.view.ViewGroup;

import com.onlinepayments.client.android.exampleapp.render.persister.InputValidationPersister;
import com.onlinepayments.sdk.client.android.domain.validation.ValidationErrorMessage;

import java.security.InvalidParameterException;

/**
 * Helper class for rendering validation messages
 */
public class RenderValidationHelper {

    // Default renderer for validation errors on all fields
    private final RenderValidationMessageInterface validationMessageRenderer =
        new RenderValidationMessage();

    // The parent view to which all validationMessages are added
    private final ViewGroup parentView;

    /**
     * Constructor
     *
     * @param parentView, the ViewGroup to which all the rendered validationMessages are added
     */
    public RenderValidationHelper(ViewGroup parentView) {

        if (parentView == null) {
            throw new InvalidParameterException(
                "Error creating Validator, parentView may not be null");
        }

        this.parentView = parentView;
    }

    /**
     * Renders validationMessage for every invalid field in the invalidFields list
     *
     */
    public void renderValidationMessages(
        InputValidationPersister inputValidationPersister
    ) {

        for (ValidationErrorMessage validationResult : inputValidationPersister.getErrorMessages()) {
            renderValidationMessageOnScreen(validationResult);
        }
    }

    public void renderValidationMessage(
        ValidationErrorMessage validationResult
    ) {
        renderValidationMessageOnScreen(validationResult);
    }

    private String getValidationMessage(ValidationErrorMessage validationResult) {
        return validationResult.getErrorMessage();
    }

    private void renderValidationMessageOnScreen(
        ValidationErrorMessage validationResult
    ) {

        String fieldId = validationResult.getPaymentProductFieldId();

        // Get the correct view of the invalid inputField
        View view = parentView.findViewWithTag(fieldId);

        if (!canErrorMessageBeRendered(view, fieldId)) {
            return;
        }

        String validationMessage = getValidationMessage(validationResult);

        validationMessageRenderer.renderValidationMessage(
            validationMessage,
            (ViewGroup) view.getParent(),
            validationResult.getPaymentProductFieldId()
        );
    }

    private boolean canErrorMessageBeRendered(View fieldView, String fieldId) {
        // If the field cannot be found, we can not render an error message for it
        if (fieldView == null) {
            return false;
        }

        // Check if there is not already an error message for this field
        return ((ViewGroup) fieldView.getParent().getParent()).findViewWithTag(
            RenderValidationMessage.VALIDATION_MESSAGE_TAG_PREFIX + fieldId) == null;
    }

    public void removeValidationMessage(ViewGroup rowView, String fieldId) {

        if (rowView == null) {
            throw new InvalidParameterException(
                "Error removing ValidationMessage, rowView may not be null");
        }
        if (fieldId == null) {
            throw new InvalidParameterException(
                "Error removing ValidationMessage, fieldId may not be null");
        }

        validationMessageRenderer.removeValidationMessage(rowView, fieldId);
    }

    /**
     * Hides all visible validationMessages
     */
    public void hideValidationMessages(InputValidationPersister inputValidationPersister) {

        for (ValidationErrorMessage validationErrorMessage : inputValidationPersister.getErrorMessages()) {
            View view =
                parentView.findViewWithTag(validationErrorMessage.getPaymentProductFieldId());
            if (view != null) {
                validationMessageRenderer.removeValidationMessage(
                    (ViewGroup) view.getParent(),
                    validationErrorMessage.getPaymentProductFieldId()
                );
            }
        }
    }
}
