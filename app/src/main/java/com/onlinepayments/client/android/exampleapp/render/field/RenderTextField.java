package com.onlinepayments.client.android.exampleapp.render.field;

import static com.onlinepayments.client.android.exampleapp.view.detailview.DetailInputViewCreditCardImpl.CREDIT_CARD_NUMBER_FIELD_ID;

import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.model.PaymentContext;
import com.onlinepayments.sdk.client.android.model.paymentproduct.AccountOnFile;
import com.onlinepayments.sdk.client.android.model.paymentproduct.KeyValuePair;
import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentProductField;

import java.security.InvalidParameterException;
import java.util.Objects;


/**
 * This class handles all rendering of the TextField field
 *
 * Copyright 2020 Global Collect Services B.V
 */
public class RenderTextField implements RenderInputFieldInterface {

	@Override
	public View renderField(PaymentProductField field, InputDataPersister inputDataPersister,
							ViewGroup rowView, PaymentContext paymentContext) {

		if (field == null) {
			throw new InvalidParameterException("Error rendering textfield, field may not be null");
		}
		if (rowView == null) {
			throw new InvalidParameterException("Error rendering textfield, rowView may not be null");
		}
		if (inputDataPersister == null) {
			throw new InvalidParameterException("Error rendering textfield, inputDataPersister may not be null");
		}

		AccountOnFile accountOnFile = inputDataPersister.getAccountOnFile();

		// Create new EditText and set its style, restrictions, mask and keyboardtype
		EditText editText = new EditText(rowView.getContext());
		editText.setTextAppearance(rowView.getContext(), R.style.TextField);

		if (field.getDataRestrictions().getValidator().getLength() != null) {
			// Set maxLength for field
			Integer maxLength = field.getDataRestrictions().getValidator().getLength().getMaxLength();
			if (maxLength > 0) {
				editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
				editText.setEms(maxLength);
			}
		}

		// Set placeholder for field
		editText.setHint(field.getDisplayHints().getPlaceholderLabel());

		// Set correct inputType type
		switch (field.getDisplayHints().getPreferredInputType()) {
			case INTEGER_KEYBOARD:
				editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
				break;
			case STRING_KEYBOARD:
				editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
				break;
			case PHONE_NUMBER_KEYBOARD:
				editText.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
				break;
			case EMAIL_ADDRESS_KEYBOARD:
				editText.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
				break;
			case DATE_PICKER:
				editText.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
			default:
				editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
				break;
		}

		// Check if this edittext should be obfuscated
		if (field.getDisplayHints().isObfuscate()) {
			editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
		}

		// Add mask functionality when a mask is set
		boolean addMasking = field.getDisplayHints().getMask() != null;

		Integer maskLength = 0;
		if (field.getDisplayHints().getMask() != null ) {
			maskLength = field.getDisplayHints().getMask().replace("{", "").replace("}", "").length();
			editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maskLength)});
		} else if (field.getDataRestrictions().getValidator().getLength() != null) {

			maskLength = field.getDataRestrictions().getValidator().getLength().getMaxLength();
		}

		// Set values from account on file
		if (accountOnFile != null) {
			for (KeyValuePair attribute : accountOnFile.getAttributes()) {
				if (attribute.getKey().equals(field.getId())) {
					if(field.getType() == PaymentProductField.Type.EXPIRYDATE && addMasking) {
						String maskedValue = field.applyMask(attribute.getValue());
						editText.setText(maskedValue);
					} else {
						editText.setText(attribute.getValue());
					}

					if (!attribute.isEditingAllowed()) {
						editText.setEnabled(false);
					}
				}
			}
			// Do not add text changed watcher on credit card number field if it has account on file, a custom watcher will be set in DetailInputViewCreditCardImpl
			if(!Objects.equals(field.getId(), CREDIT_CARD_NUMBER_FIELD_ID)) {
				this.setTextChangedListener(inputDataPersister, field, editText, addMasking);
			}
		} else {
			this.setTextChangedListener(inputDataPersister, field, editText, addMasking);
		}

		// get input information from inputDataPersister
		String paymentProductValue = inputDataPersister.getValue(field.getId());
		if(paymentProductValue != null){
			editText.setText(paymentProductValue);
		}

		// Add it to parentView
		rowView.addView(editText);

		return editText;
	}

	private void setTextChangedListener(InputDataPersister inputDataPersister, PaymentProductField field, EditText editText, boolean addMasking) {
		// Add OnTextChanged watcher for input field
		FieldInputTextWatcher fieldInputTextWatcher = new FieldInputTextWatcher(inputDataPersister, field.getId(), editText, addMasking);
		editText.addTextChangedListener(fieldInputTextWatcher);
	}
}
