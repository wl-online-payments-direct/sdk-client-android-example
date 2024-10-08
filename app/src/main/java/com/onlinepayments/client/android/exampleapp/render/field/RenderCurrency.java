package com.onlinepayments.client.android.exampleapp.render.field;

import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.model.PaymentContext;
import com.onlinepayments.sdk.client.android.model.paymentproduct.AccountOnFileAttribute;
import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentProductField;

import java.security.InvalidParameterException;

/**
 * Renders currency field
 * Copyright 2020 Global Collect Services B.V
 *
 */
public class RenderCurrency implements RenderInputFieldInterface{

	@Override
	public View renderField(PaymentProductField field, InputDataPersister inputDataPersister,
							ViewGroup rowView, PaymentContext paymentContext) {

		if (field == null) {
			throw new InvalidParameterException("Error rendering currency, field may not be null");
		}
		if (inputDataPersister == null) {
			throw new InvalidParameterException("Error rendering currency, inputDataPersister may not be null");
		}
		if (rowView == null) {
			throw new InvalidParameterException("Error rendering currency, rowView may not be null");
		}
		if (paymentContext == null) {
			throw new InvalidParameterException("Error rendering currency, paymentContext may not be null");
		}

		// Create new EditText and set its style, restrictions, mask and keyboardtype
		EditText integerPart = new EditText(rowView.getContext());
		integerPart.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)}); //maxlength is 9 - 2 so no integer overflow
		String label = field.getDisplayHints().getLabel();
		integerPart.setHint(label);

		// Set correct inputType type
		switch (field.getDisplayHints().getPreferredInputType()) {
			case INTEGER_KEYBOARD:
				integerPart.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
				break;
			case STRING_KEYBOARD:
				integerPart.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
				break;
			case PHONE_NUMBER_KEYBOARD:
				integerPart.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
				break;
			case EMAIL_ADDRESS_KEYBOARD:
				integerPart.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
				break;
			default:
				integerPart.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
				break;
		}

		// Check if this edittext should be obfuscated
		if (field.getDisplayHints().isObfuscate()) {
			integerPart.setTransformationMethod(PasswordTransformationMethod.getInstance());
		}

		// Set values from account on file
		if (inputDataPersister.getAccountOnFile() != null) {
			for (AccountOnFileAttribute attribute : inputDataPersister.getAccountOnFile().getAccountOnFileAttributes()) {
				if (attribute.getKey().equals(field.getId())) {
					if (!attribute.isEditingAllowed()) {
						integerPart.setEnabled(false);
					}
				}
			}
		}

		LinearLayout linearLayout = new LinearLayout(rowView.getContext());
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		TextView currencySymbol = new TextView(rowView.getContext());
		currencySymbol.setText(paymentContext.getAmountOfMoney().getCurrencyCode());

		TextView separator = new TextView(rowView.getContext());

		EditText decimalPart = new EditText(rowView.getContext());
		decimalPart.setHint("00");
		decimalPart.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
		decimalPart.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

		integerPart.addTextChangedListener(new FieldInputTextWatcherCurrency(inputDataPersister, field.getId(), decimalPart, true));
		decimalPart.addTextChangedListener(new FieldInputTextWatcherCurrency(inputDataPersister, field.getId(), integerPart, false));

        // Restore data that has previously been entered in this field
        if (inputDataPersister.getValue(field.getId()) != null) {
            String value = inputDataPersister.getValue(field.getId());
            if (value.length() > 2) {
                integerPart.setText(value.substring(0, value.length() - 2));
            }
            if (!value.endsWith("00")) {
                decimalPart.setText(value.substring(value.length()-2));
            }
        }

		LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0f);
		linearLayout.addView(currencySymbol, params0);

		LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f);
		linearLayout.addView(integerPart, params1);

		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0f);
		linearLayout.addView(separator, params2);

		LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0f);
		linearLayout.addView(decimalPart, params3);

		// Add it to parentView
		rowView.addView(linearLayout);

		return linearLayout;
	}
}
