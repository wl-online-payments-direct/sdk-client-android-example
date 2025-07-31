package com.onlinepayments.client.android.exampleapp.render.field;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;

import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.model.PaymentContext;
import com.onlinepayments.sdk.client.android.model.paymentproduct.AccountOnFileAttribute;
import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentProductField;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all rendering of the list field
 * Copyright 2020 Global Collect Services B.V
 */
public class RenderList implements RenderInputFieldInterface, AdapterView.OnItemSelectedListener {

    @Override
    public View renderField(
        PaymentProductField field,
        InputDataPersister inputDataPersister,
        ViewGroup rowView,
        PaymentContext paymentContext
    ) {

        if (field == null) {
            throw new InvalidParameterException("Error rendering list, field may not be null");
        }
        if (inputDataPersister == null) {
            throw new InvalidParameterException(
                "Error rendering list, inputDataPersister may not be null");
        }
        if (rowView == null) {
            throw new InvalidParameterException("Error rendering list, rowView may not be null");
        }

        // Create new spinner and fill its values
        Spinner spinner = new Spinner(rowView.getContext());

        // Parse the loaded values to array and set as ArrayAdapter
        List<String> values = new ArrayList<>();

        // Make and set adapter to spinner
        ArrayAdapter<String> dataAdapter =
            new ArrayAdapter<>(rowView.getContext(), android.R.layout.simple_spinner_item, values);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        // Set the spinner to the stored value in the account
        if (inputDataPersister.getAccountOnFile() != null) {

            for (AccountOnFileAttribute attribute : inputDataPersister.getAccountOnFile()
                .getAttributes()) {
                if (attribute.getKey().equals(field.getId())) {
                    int spinnerPosition = dataAdapter.getPosition(attribute.getValue());
                    spinner.setSelection(spinnerPosition);

                    if (!attribute.isEditingAllowed()) {
                        spinner.setEnabled(false);
                    }
                }
            }
        }

        // Set Value that is last stored in the inputDataPersister; relevant for when the View
        // is redrawn (i.e. when the user turns the phone)
        if (inputDataPersister.getValue(field.getId()) != null) {
            int spinnerPosition =
                dataAdapter.getPosition(inputDataPersister.getValue(field.getId()));
            spinner.setSelection(spinnerPosition);
        }

        // Add this as listener for this inputField
        spinner.setOnItemSelectedListener(this);

        LayoutParams params =
            new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rowView.addView(spinner, params);
        return spinner;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /* No-op: logic removed due to deprecation of classes */
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
