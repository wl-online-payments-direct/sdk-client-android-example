package com.onlinepayments.client.android.exampleapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.configuration.Constants;

/**
 * Copyright 2020 Global Collect Services B.V
 *
 */
public class PaymentResultActivity extends ShoppingCartActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_result);

		// Get the views for displaying the result
		TextView paymentResultTitle = findViewById(R.id.payment_result_title);
		TextView paymentResultDescription = findViewById(R.id.payment_result_description);

		// Retrieve error message from paymentInputIntent
		Intent paymentInputIntent = getIntent();
		String errorMessage = paymentInputIntent.getStringExtra(Constants.INTENT_ERRORMESSAGE);

		String title = errorMessage == null
				? getString(R.string.app_result_success_title)
				: getString(R.string.app_result_failed_title);

		String description = errorMessage == null
				? getString(R.string.app_result_success_bodyText)
				: getString(R.string.app_result_failed_bodyText);

		paymentResultTitle.setText(title);
		paymentResultDescription.setText(description);
	}
}
