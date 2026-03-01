/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.render.field;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.domain.PaymentContextWithAmount;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.productField.PaymentProductField;

import java.security.InvalidParameterException;

/**
 * This class handles all rendering of the TextField field
 */
public class RenderBoolean implements RenderInputFieldInterface {

    @Override
    public View renderField(
        PaymentProductField field,
        InputDataPersister inputDataPersister,
        ViewGroup rowView,
        PaymentContextWithAmount paymentContext
    ) {

        if (field == null) {
            throw new InvalidParameterException("Error rendering checkbox, field may not be null");
        }

        if (rowView == null) {
            throw new InvalidParameterException("Error rendering checkbox, rowView may not be null");
        }

        if (inputDataPersister == null) {
            throw new InvalidParameterException(
                "Error rendering checkbox, inputDataPersister may not be null");
        }

        // Create a new checkbox
        CheckBox checkBox = new CheckBox(rowView.getContext());
        checkBox.setGravity(Gravity.CENTER_VERTICAL);

        checkBox.setOnCheckedChangeListener(createOnCheckedChangedListener(
            inputDataPersister,
            field.getId()
        ));

        // Get input information from inputDataPersister
        String value = inputDataPersister.getValue(field.getId());
        if (value == null) {
            // Store the value as "false" so that the checkbox will not be validated with an empty value
            inputDataPersister.setValue(field.getId(), "false");
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(Boolean.parseBoolean(value));
        }

        rowView.addView(checkBox, 0);

        return checkBox;
    }

    private OnCheckedChangeListener createOnCheckedChangedListener(
        final InputDataPersister inputDataPersister,
        final String fieldId
    ) {
        return (compoundButton, b) -> {
            inputDataPersister.setValue(fieldId, Boolean.toString(b));
            inputDataPersister.setFocusFieldId(fieldId);
        };
    }
}
