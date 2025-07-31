package com.onlinepayments.client.android.exampleapp.render.field;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;

import java.security.InvalidParameterException;

/**
 * Android textWatcher that fills the paymentProductField with this currencyField's value
 * <p>
 * Copyright 2020 Global Collect Services B.V
 * </p>
 */
public class FieldInputTextWatcherCurrency implements TextWatcher {

    // PaymentRequest is the object where all entered values are stored for a field
    private final InputDataPersister inputDataPersister;

    // PaymentProductFieldId needed for storing values in the paymentRequest
    private final String paymentProductFieldId;

    // To make difference between the two EditText and get the value of the other EditText
    private final EditText otherEditText;
    private final Boolean isIntegerPart;

    public FieldInputTextWatcherCurrency(
        InputDataPersister inputDataPersister,
        String paymentProductFieldId,
        EditText otherEditText,
        Boolean isIntegerPart
    ) {

        if (inputDataPersister == null) {
            throw new InvalidParameterException(
                "Error creating FieldInputTextWatcherCurrency, paymentRequest may not be null");
        }
        if (paymentProductFieldId == null) {
            throw new InvalidParameterException(
                "Error creating FieldInputTextWatcherCurrency, paymentProductFieldId may not be null");
        }
        if (otherEditText == null) {
            throw new InvalidParameterException(
                "Error creating FieldInputTextWatcherCurrency, otherEditText may not be null");
        }
        if (isIntegerPart == null) {
            throw new InvalidParameterException(
                "Error creating FieldInputTextWatcherCurrency, isIntegerPart may not be null");
        }

        this.inputDataPersister = inputDataPersister;
        this.paymentProductFieldId = paymentProductFieldId;
        this.otherEditText = otherEditText;
        this.isIntegerPart = isIntegerPart;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

        int value;
        String valueOtherEditText = otherEditText.getText().toString();
        if (valueOtherEditText.isEmpty()) {
            valueOtherEditText = "0";
        }

        String valueEditText = s.toString();
        if (valueEditText.isEmpty()) {
            valueEditText = "0";
        }

        if (isIntegerPart) {
            value = (int) (Double.parseDouble(valueEditText) * 100 + Double.parseDouble(
                valueOtherEditText));
        } else {
            value =
                (int) (Double.parseDouble(valueEditText) + Double.parseDouble(valueOtherEditText) * 100);
        }

        // Save state of field
        inputDataPersister.setValue(paymentProductFieldId, Integer.toString(value));
    }
}
