package com.onlinepayments.client.android.exampleapp.render.iinlookup;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.onlinepayments.sdk.client.android.listener.IinLookupResponseListener;
import com.onlinepayments.sdk.client.android.model.PaymentContext;
import com.onlinepayments.sdk.client.android.session.Session;

import java.security.InvalidParameterException;

/**
 * Android TextWatcher that is put on Creditcardnumber fields so an IIN lookup can be done
 * Copyright 2020 Global Collect Services B.V
 *
 */
public class IinLookupTextWatcher implements TextWatcher {


	// The Session which can retrieve the IIN details
	private Session session;

	// The listener which will be called by the AsyncTask
	private IinLookupResponseListener listener;

	// The device meta data
	private Context context;

	// Payment context information that is sent with the IIN lookup
	private PaymentContext paymentContext;

	// Workaround for having twice called the afterTextChanged
	private String previousEnteredValue = "";


	/**
	 * Constructor
	 * @param session, Session for getting IinDetails
	 * @param paymentContext, Payment context that will be used in the request for getting IinDetails
	 * @param listener, IinLookupCompleteListener which will be called by the AsyncTask
	 */
	public IinLookupTextWatcher(Context context, Session session, PaymentContext paymentContext, IinLookupResponseListener listener) {

		if (context == null) {
			throw new InvalidParameterException("Error creating IinLookupTextWatcher, context may not be null");
		}
		if (session == null) {
			throw new InvalidParameterException("Error creating IinLookupTextWatcher, session may not be null");
		}
		if (listener == null) {
			throw new InvalidParameterException("Error creating IinLookupTextWatcher, listener may not be null");
		}
		if (paymentContext == null) {
			throw new InvalidParameterException("Error creating IinLookupTextWatcher, c2sContext may not be null");
		}

		this.context = context;
		this.session = session;
		this.listener = listener;
		this.paymentContext = paymentContext;
	}


	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}


	@Override
	public void afterTextChanged(Editable s) {

		// Strip the spaces that are added by Masking
		String currentEnteredValue = s.toString().replace(" ", "");

		// Retrieve IIN Details if the first 8 digits have changed, and the length of the current
		// value is at least six.
		// The IIN Lookup may return different results depending on the length of the initial
		// digits, between length 6 and 8.
		if (currentEnteredValue.length() >= 6 && isOneOfFirst8DigitsChanged(currentEnteredValue)) {

			// Do iinlookup
			session.getIinDetails(context, currentEnteredValue, listener, paymentContext);
		}

		// Update the previousEnteredValue
		previousEnteredValue = currentEnteredValue;
	}

	private boolean isOneOfFirst8DigitsChanged(String currentEnteredValue) {
		// Add some padding to make sure there are 8 characters to compare
		String currentPadded = currentEnteredValue + "xxxxxxxx";
		String previousPadded = previousEnteredValue + "xxxxxxxx";

		return !currentPadded.substring(0, 8).equals(previousPadded.substring(0, 8));
	}
}
