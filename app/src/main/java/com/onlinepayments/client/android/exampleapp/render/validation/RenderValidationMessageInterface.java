package com.onlinepayments.client.android.exampleapp.render.validation;

import android.view.ViewGroup;

/**
 * Defines the rendering of tooltip interface methods
 * <p>
 * Copyright 2020 Global Collect Services B.V
 * </p>
 */
public interface RenderValidationMessageInterface {

    /**
     * Renders validation message by the data in the PaymentProductField.
     * This validation message is added to the given ViewGroup
     *
     * @param validationMessage, the message that must be shown
     * @param rowView,           the ViewGroup to which the validationMessage is added
     * @param fieldId,           PaymentProductFieldId used for rendering validation message
     */
    void renderValidationMessage(String validationMessage, ViewGroup rowView, String fieldId);

    /**
     * Removes a validation message for the given PaymentProductField
     *
     * @param rowView, the row which contains the showing validationMessage
     * @param fieldId, the PaymentProductField for which the validationMessage will be removed
     */
    void removeValidationMessage(ViewGroup rowView, String fieldId);
}
