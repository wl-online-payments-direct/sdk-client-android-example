package com.onlinepayments.client.android.exampleapp.view;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Objects;

public class ValidationEditText extends androidx.appcompat.widget.AppCompatEditText {

    public ValidationEditText(Context context) {
        super(context);
        createValidation();
    }

    public ValidationEditText(Context context, AttributeSet attribute_set) {
        super(context, attribute_set, android.R.attr.editTextStyle);
        createValidation();
    }

    public ValidationEditText(
        Context context, AttributeSet attribute_set, int def_style_attribute
    ) {
        super(context, attribute_set, def_style_attribute);
        createValidation();
    }

    public String getValue() {
        return Objects.requireNonNull(getText()).toString();
    }

    public boolean isValid() {
        validate();
        boolean hasErrors = (getError() != null);
        return !hasErrors;
    }

    private void createValidation() {
        setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                return;
            }

            validate();
        });
    }

    private void validate() {
        if (getText().toString().isEmpty()) {
            setError("Value must be provided");
        } else {
            setError(null);
        }
    }
}
