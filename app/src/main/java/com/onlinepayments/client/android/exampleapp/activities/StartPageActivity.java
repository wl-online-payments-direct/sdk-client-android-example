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

package com.onlinepayments.client.android.exampleapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.configuration.Constants;
import com.onlinepayments.client.android.exampleapp.dialog.DialogUtil;
import com.onlinepayments.client.android.exampleapp.model.ShoppingCart;
import com.onlinepayments.client.android.exampleapp.model.ShoppingCartItem;
import com.onlinepayments.client.android.exampleapp.view.ValidationEditText;
import com.onlinepayments.sdk.client.android.domain.AmountOfMoneyWithAmount;
import com.onlinepayments.sdk.client.android.domain.PaymentContextWithAmount;

import java.util.Currency;
import java.util.Locale;

/**
 * Dummy startPage to start payment.
 */
public class StartPageActivity extends Activity {

    ValidationEditText clientSessionIdentifierEditText;
    ValidationEditText customerIdentifierEditText;
    ValidationEditText clientApiUrlEditText;
    ValidationEditText assetUrlEditText;
    ValidationEditText countryCodeEditText;
    ValidationEditText currencyCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startpage);
        clientSessionIdentifierEditText = findViewById(R.id.client_session_identifier);
        customerIdentifierEditText = findViewById(R.id.customer_identifier);
        clientApiUrlEditText = findViewById(R.id.client_api_url);
        assetUrlEditText = findViewById(R.id.asset_url);
        countryCodeEditText = findViewById(R.id.country_code);
        currencyCodeEditText = findViewById(R.id.currency_code);

        loadData();
    }

    public void payButtonPressed(View view) {

        if (!clientSessionIdentifierEditText.isValid()) {
            return;
        }
        String clientSessionIdentifier = clientSessionIdentifierEditText.getValue();

        if (!customerIdentifierEditText.isValid()) {
            return;
        }
        String customerId = customerIdentifierEditText.getValue();

        EditText merchantIdentifierEditText = findViewById(R.id.merchant_identifier);
        String merchantId = merchantIdentifierEditText.getText().toString();

        EditText merchantNameText = findViewById(R.id.merchant_name);
        String merchantName = merchantNameText.getText().toString();

        ValidationEditText clientApiUrlEditText = findViewById(R.id.client_api_url);
        if (!clientApiUrlEditText.isValid()) {
            return;
        }
        String clientApiUrl = clientApiUrlEditText.getValue();

        if (!assetUrlEditText.isValid()) {
            return;
        }
        String assetUrl = assetUrlEditText.getValue();

        ValidationEditText amountEditText = findViewById(R.id.amount);
        if (!amountEditText.isValid()) {
            return;
        }
        String amount = amountEditText.getValue();

        if (!countryCodeEditText.isValid()) {
            return;
        }
        String countryCode = countryCodeEditText.getValue();

        if (!currencyCodeEditText.isValid()) {
            return;
        }
        String currencyCode = currencyCodeEditText.getValue();

        boolean isRecurring = ((CheckBox) findViewById(R.id.payment_is_recurring)).isChecked();
        boolean environmentIsProduction =
            ((CheckBox) findViewById(R.id.environment_is_production)).isChecked();

        ShoppingCart cart = new ShoppingCart();
        cart.addItemToShoppingCart(new ShoppingCartItem("Something", Long.parseLong(amount), 1));

        // Create the PaymentContextWithAmount object
        AmountOfMoneyWithAmount amountOfMoney =
            new AmountOfMoneyWithAmount(cart.getTotalAmount(), currencyCode);
        PaymentContextWithAmount paymentContext =
            new PaymentContextWithAmount(amountOfMoney, countryCode, isRecurring);

        // and show the PaymentProductSelectionActivity
        Intent paymentIntent = new Intent(this, PaymentProductSelectionActivity.class);

        // Attach the following objects to the paymentIntent
        paymentIntent.putExtra(Constants.INTENT_PAYMENT_CONTEXT, paymentContext);
        paymentIntent.putExtra(Constants.INTENT_SHOPPINGCART, cart);
        paymentIntent.putExtra(
            Constants.MERCHANT_CLIENT_SESSION_IDENTIFIER,
            clientSessionIdentifier
        );
        paymentIntent.putExtra(Constants.MERCHANT_CUSTOMER_IDENTIFIER, customerId);
        paymentIntent.putExtra(Constants.MERCHANT_MERCHANT_IDENTIFIER, merchantId);
        paymentIntent.putExtra(Constants.MERCHANT_NAME, merchantName);
        paymentIntent.putExtra(Constants.MERCHANT_CLIENT_API_URL, clientApiUrl);
        paymentIntent.putExtra(Constants.MERCHANT_ASSET_URL, assetUrl);
        paymentIntent.putExtra(
            Constants.MERCHANT_ENVIRONMENT_IS_PRODUCTION,
            environmentIsProduction
        );

        // Start paymentIntent
        startActivity(paymentIntent);
    }

    public void parseJsonButtonPressed(View view) {
        DialogUtil.showJsonAlertDialog(
            this, sessionDetails -> {

                assetUrlEditText.setText(sessionDetails.assetUrl);
                clientApiUrlEditText.setText(sessionDetails.clientApiUrl);
                clientSessionIdentifierEditText.setText(sessionDetails.clientSessionId);
                customerIdentifierEditText.setText(sessionDetails.customerId);

                assetUrlEditText.isValid();
                clientApiUrlEditText.isValid();
                clientSessionIdentifierEditText.isValid();
                customerIdentifierEditText.isValid();
            }
        );
    }

    private void loadData() {
        // prefill country and currency
        Locale locale = Locale.getDefault();
        countryCodeEditText.setText(locale.getCountry());
        currencyCodeEditText.setText(Currency.getInstance(locale).toString());
    }
}
