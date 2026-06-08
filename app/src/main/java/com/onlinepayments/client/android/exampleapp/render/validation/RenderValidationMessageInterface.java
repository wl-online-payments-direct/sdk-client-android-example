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

import android.view.ViewGroup;

/**
 * Defines the rendering of tooltip interface methods
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
