package com.onlinepayments.client.android.exampleapp.render.field;

import java.security.InvalidParameterException;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.onlinepayments.client.android.exampleapp.render.iinlookup.IinLookupTextWatcher;
import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.model.FormatResult;

/**
 * Android text watcher for the credit card field of an account on file.
 *
 * Copyright 2023 Global Collect Services B.V
 *
 */
public class AccountOnFileCardTextWatcher extends FieldInputTextWatcher {

    // IinLookupTextWatcher that needs to be set when the entered value is different from account on file
    private IinLookupTextWatcher iinLookupTextWatcher;

    // To keep track of what the original account on value was
    private String accountOnFileValue;

    private boolean hasIinLookupTextWatcher = false;


    public AccountOnFileCardTextWatcher(InputDataPersister inputDataPersister, String paymentProductFieldId, EditText editText, String accountOnFileValue, IinLookupTextWatcher iinLookupTextWatcher) {
        super(inputDataPersister, paymentProductFieldId, editText, true);

        if (iinLookupTextWatcher == null) {
            throw new InvalidParameterException("Error creating AccountOnFileCardTextWatcher, iinLookupTextWatcher may not be null");
        }

        this.iinLookupTextWatcher = iinLookupTextWatcher;
        this.accountOnFileValue = accountOnFileValue;
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Do nothing if the entered string is empty or the original account on file value
        if (!s.toString().equals("") && !s.toString().equals(accountOnFileValue)) {
            // Add iinlookuptextwatcher if entered string is different from account on file value & editText does not have a watcher yet
            if (!hasIinLookupTextWatcher) {
                editText.addTextChangedListener(iinLookupTextWatcher);
                hasIinLookupTextWatcher = true;
            }

            applyMask(s.toString());

            // Save state of field only if input was changed
            inputDataPersister.setValue(paymentProductFieldId, editText.getText().toString());
        } else {
            inputDataPersister.removeValue(paymentProductFieldId);
        }
    }

    public void removeIinLookupTextWatcher() {
        editText.removeTextChangedListener(iinLookupTextWatcher);
        hasIinLookupTextWatcher = false;
    }
}
