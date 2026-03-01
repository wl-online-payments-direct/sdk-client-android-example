/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.activities;

import static com.onlinepayments.client.android.exampleapp.configuration.Constants.GOOGLE_PAY_TOKEN_FIELD_ID;
import static com.onlinepayments.client.android.exampleapp.configuration.Constants.PAYMENTPRODUCTID_GOOGLEPAY;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.configuration.CheckCommunication;
import com.onlinepayments.client.android.exampleapp.configuration.Constants;
import com.onlinepayments.client.android.exampleapp.model.ShoppingCart;
import com.onlinepayments.client.android.exampleapp.util.GooglePay;
import com.onlinepayments.client.android.exampleapp.util.SdkManager;
import com.onlinepayments.client.android.exampleapp.view.selectionview.ProductSelectionView;
import com.onlinepayments.client.android.exampleapp.view.selectionview.ProductSelectionViewImpl;
import com.onlinepayments.sdk.client.android.domain.PaymentContextWithAmount;
import com.onlinepayments.sdk.client.android.domain.accountOnFile.AccountOnFile;
import com.onlinepayments.sdk.client.android.domain.exceptions.SdkException;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.BasicPaymentProduct;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.BasicPaymentProducts;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.PaymentProduct;
import com.onlinepayments.sdk.client.android.domain.paymentRequest.EncryptedRequest;
import com.onlinepayments.sdk.client.android.domain.paymentRequest.PaymentRequest;
import com.onlinepayments.sdk.client.android.facade.OnlinePaymentsSdk;
import com.onlinepayments.sdk.client.android.facade.listeners.BasicPaymentProductsResponseListener;
import com.onlinepayments.sdk.client.android.facade.listeners.PaymentProductResponseListener;
import com.onlinepayments.sdk.client.android.facade.listeners.PaymentRequestPreparedListener;

import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.Objects;

/**
 * Activity that lists all the available payment options
 */
public class PaymentProductSelectionActivity extends ShoppingCartActivity implements
    DialogInterface.OnClickListener {

    private static final String TAG = PaymentProductSelectionActivity.class.getName();

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 42;

    // The view belonging to this activity
    private ProductSelectionView selectionView;

    private ShoppingCart shoppingCart;

    // The sdk object that is used to connect to the API
    private OnlinePaymentsSdk sdk;

    // Parameters used to initialize the connection
    private String clientSessionId;
    private String customerId;
    private String merchantId;
    private String merchantName;
    private String clientApiUrl;
    private String assetUrl;
    private boolean environmentIsProduction;

    // Variables required to retrieve the payment items that are available for payment
    private PaymentContextWithAmount paymentContext;
    private PaymentProduct paymentProduct;

    // Loaded payment product and selected Account On File information
    private BasicPaymentProducts basicPaymentProducts;
    private AccountOnFile accountOnFile;

    private final PaymentProductResponseListener paymentProductResponseListener =
        new PaymentProductResponseListener() {
            @Override
            public void onSuccess(@NonNull PaymentProduct paymentProduct) {
                PaymentProductSelectionActivity.this.paymentProduct = paymentProduct;
                handlePaymentProductCallBack(paymentProduct);
            }

            @Override
            public void onFailure(@NonNull SdkException sdkException) {
                handleApiErrorOrException();
            }
        };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_product);

        selectionView =
            new ProductSelectionViewImpl(this, R.id.payment_product_selection_view_layout);

        // This activity won't work without internet, so show an error message if there is no
        // connection
        if (!CheckCommunication.isOnline(this)) {
            selectionView.showNoInternetDialog(this);
            return;
        }

        loadIntentData();

        if (savedInstanceState != null) {
            initializeSavedInstanceStateData(savedInstanceState);
        }

        if (basicPaymentProducts == null) {
            try {
                /*
                 Instantiate the Sdk.
                 This session has logging enabled, meaning that requests made to the server and
                 responses received from the server will be logged.
                 By default, logging is disabled.
                 Logging can also be enabled or disabled at a later stage by calling
                 `session.setLoggingEnabled(Boolean)`.
                 Logging should be disabled in production.
                 To ensure that - for example - logging is enabled in debug and disabled in release,
                 you can use a build property whose value depends on the build variant of your app.
                 */
                sdk = SdkManager.initSdk(
                    clientSessionId,
                    customerId,
                    clientApiUrl,
                    assetUrl,
                    environmentIsProduction,
                    Constants.APPLICATION_IDENTIFIER,
                    //                    BuildConfig.LOGGING_ENABLED,
                    false,
                    this
                );
            } catch (InvalidParameterException e) {
                Log.e(TAG, "Instantiating Sdk failed with: " + e.getMessage());
                selectionView.showTechnicalErrorDialog(this);
                return;
            }

            selectionView.showLoadingIndicator();

            sdk.getBasicPaymentProducts(
                paymentContext, new BasicPaymentProductsResponseListener() {
                    @Override
                    public void onSuccess(BasicPaymentProducts basicPaymentProducts) {
                        if (basicPaymentProducts != null) {

                            PaymentProductSelectionActivity.this.basicPaymentProducts =
                                basicPaymentProducts;

                            selectionView.renderDynamicContent(basicPaymentProducts);
                        } else {
                            selectionView.showTechnicalErrorDialog(PaymentProductSelectionActivity.this);
                        }
                        selectionView.hideLoadingIndicator();
                    }

                    @Override
                    public void onFailure(@NonNull SdkException e) {
                        handleApiErrorOrException();
                    }
                }
            );
        } else {
            selectionView.renderDynamicContent(basicPaymentProducts);
        }
    }

    private void handleApiErrorOrException() {
        selectionView.showTechnicalErrorDialog(PaymentProductSelectionActivity.this);
        selectionView.hideLoadingIndicator();
    }

    private void loadIntentData() {
        Intent intent = getIntent();
        clientSessionId = intent.getStringExtra(Constants.MERCHANT_CLIENT_SESSION_IDENTIFIER);
        customerId = intent.getStringExtra(Constants.MERCHANT_CUSTOMER_IDENTIFIER);
        clientApiUrl = intent.getStringExtra(Constants.MERCHANT_CLIENT_API_URL);
        merchantId = intent.getStringExtra(Constants.MERCHANT_MERCHANT_IDENTIFIER);
        merchantName = intent.getStringExtra(Constants.MERCHANT_NAME);
        assetUrl = intent.getStringExtra(Constants.MERCHANT_ASSET_URL);
        environmentIsProduction =
            intent.getBooleanExtra(Constants.MERCHANT_ENVIRONMENT_IS_PRODUCTION, false);
        paymentContext =
            (PaymentContextWithAmount) intent.getSerializableExtra(Constants.INTENT_PAYMENT_CONTEXT);
        shoppingCart = (ShoppingCart) intent.getSerializableExtra(Constants.INTENT_SHOPPINGCART);
    }

    private void initializeSavedInstanceStateData(Bundle savedInstanceState) {
        basicPaymentProducts =
            (BasicPaymentProducts) savedInstanceState.getSerializable(Constants.BUNDLE_PAYMENT_PRODUCTS);
        shoppingCart =
            (ShoppingCart) savedInstanceState.getSerializable(Constants.BUNDLE_SHOPPING_CART);
    }

    // The callback method for when a user selects a payment product
    public void onPaymentProductSelected(View v) {
        selectionView.showLoadingIndicator();
        if (v.getTag() instanceof BasicPaymentProduct) {
            // Reset accountOnFile to null, so its contents will not be filled in for a BasicPaymentProduct
            accountOnFile = null;
            String paymentProductId = String.valueOf(((BasicPaymentProduct) v.getTag()).getId());
            sdk.getPaymentProduct(
                Integer.parseInt(paymentProductId),
                paymentContext,
                paymentProductResponseListener
            );
        } else if (v.getTag() instanceof AccountOnFile) {
            // Store the Account on file so it can be added to the intent later on.
            accountOnFile = (AccountOnFile) v.getTag();
            sdk.getPaymentProduct(
                accountOnFile.getPaymentProductId(),
                paymentContext,
                paymentProductResponseListener
            );
        } else {
            throw new InvalidParameterException("Tag in view is not of a valid type");
        }
    }

    // Determine what view should be served next, based on whether the product has inputFields.
    // For some products special rules apply, which are also handled here.
    private void handlePaymentProductCallBack(PaymentProduct paymentProduct) {
        if (paymentProduct == null) {
            selectionView.hideLoadingIndicator();
            selectionView.showTechnicalErrorDialog(this);
        } else if (paymentProduct.getId() != null && PAYMENTPRODUCTID_GOOGLEPAY == paymentProduct.getId()) {
            // Google pay requires a different flow even though it has fields. The fields should
            // not be shown to the user, but they will be filled after the user has completed the
            // Google pay flow.
            startGooglePay(paymentProduct);
        } else if (paymentProduct.getFields().isEmpty()) {
            // For payment products that do not have fields configured other actions are required to
            // complete the payment. These actions may be a redirect to an external payment products'
            // website, or showing instructions to complete the payment.
            // Currently this app does not contain examples for these kinds of products. Instead we
            // will go to the result activity directly.
            startNextActivity(new Intent(this, PaymentResultActivity.class));
        } else {
            // Ask the user for its payment details in the next activity
            determineAndStartDetailInputActivity(paymentProduct);
        }
    }

    private void startGooglePay(PaymentProduct paymentProduct) {
        if (!merchantId.isEmpty() && !merchantName.isEmpty()) {
            GooglePay googlePay =
                new GooglePay(this, paymentContext, paymentProduct, merchantId, merchantName);
            googlePay.start(environmentIsProduction);
        } else {
            Toast.makeText(
                this,
                "MerchantId & MerchantName cannot be empty when you want to use GooglePay",
                Toast.LENGTH_LONG
            ).show();
        }
        selectionView.hideLoadingIndicator();
    }

    private void determineAndStartDetailInputActivity(PaymentProduct paymentProduct) {
        // Determine what DetailInputActivity to load. The base-class just renders the fields and
        // performs default validation on the fields. In some cases this is not enough however. In
        // these cases a subclass of the DetailInputActivity will be loaded that has additional
        // functionality for these specific products/methods.
        Intent detailInputActivityIntent;
        if (Objects.equals(paymentProduct.getPaymentMethod(), "card")) {
            detailInputActivityIntent = new Intent(this, DetailInputActivityCreditCards.class);
        } else {
            detailInputActivityIntent = new Intent(this, DetailInputActivity.class);
        }

        detailInputActivityIntent.putExtra(Constants.INTENT_SELECTED_ITEM, paymentProduct);
        startNextActivity(detailInputActivityIntent);
    }

    private void startNextActivity(Intent detailInputActivityIntent) {
        // Add data to intent for the detail activity input
        detailInputActivityIntent.putExtra(Constants.INTENT_PAYMENT_CONTEXT, paymentContext);
        detailInputActivityIntent.putExtra(
            Constants.INTENT_SELECTED_ACCOUNT_ON_FILE,
            accountOnFile
        );
        detailInputActivityIntent.putExtra(Constants.INTENT_SHOPPINGCART, shoppingCart);

        // Start the intent
        startActivity(detailInputActivityIntent);
        selectionView.hideLoadingIndicator();
    }

    // Handle the result returned by the Google Pay client. In case the payment is successful,
    // the payment result page is loaded.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectionView.showLoadingIndicator();
        // value passed in AutoResolveHelper
        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    PaymentData paymentData = PaymentData.getFromIntent(data);
                    assert paymentData != null;
                    String paymentDataJSONString = paymentData.toJson();

                    try {
                        JSONObject paymentDataJSON = new JSONObject(paymentDataJSONString);

                        JSONObject paymentMethodData =
                            paymentDataJSON.getJSONObject("paymentMethodData");
                        JSONObject tokenizationData =
                            paymentMethodData.getJSONObject("tokenizationData");
                        String unformattedGooglePayToken = tokenizationData.getString("token");

                        // Token needs to be formatted when using it to create a payment with mobilePaymentMethodSpecificInput.encryptedPaymentData
                        String formattedGooglePayToken =
                            JSONObject.quote(unformattedGooglePayToken);

                        PaymentRequest paymentRequest =
                            new PaymentRequest(paymentProduct, null, false);
                        paymentRequest.setValue(GOOGLE_PAY_TOKEN_FIELD_ID, formattedGooglePayToken);
                        paymentRequest.validate();

                        sdk.encryptPaymentRequest(
                            paymentRequest, new PaymentRequestPreparedListener() {
                                @Override
                                public void onSuccess(EncryptedRequest encryptedRequest) {
                                    handlePreparedPaymentRequest(encryptedRequest);
                                }

                                @Override
                                public void onFailure(@NonNull SdkException e) {
                                    Log.e(
                                        TAG,
                                        "Could not prepare Payment Request due to exception: " + e.getMessage()
                                    );
                                    handleApiErrorOrException();
                                }
                            }
                        );
                    } catch (Exception e) {
                        Log.e(
                            TAG,
                            "Could not parse malformed JSON: \"" + paymentDataJSONString + "\""
                        );
                    }

                    break;
                case RESULT_CANCELED:
                    Log.i(TAG, "Google Pay payment was cancelled");
                    selectionView.hideLoadingIndicator();
                    break;
                case AutoResolveHelper.RESULT_ERROR:
                    Status status = AutoResolveHelper.getStatusFromIntent(data);
                    Log.e(
                        TAG,
                        "Something went wrong whilst making a Google Pay payment; errorCode: " + status
                    );
                    selectionView.hideLoadingIndicator();
                    selectionView.showTechnicalErrorDialog(this);
                    break;
                default:
                    selectionView.hideLoadingIndicator();
                    selectionView.showTechnicalErrorDialog(this);
                    Log.e(TAG, "Something went wrong whilst making a Google Pay payment");
            }
        }
    }

    /**
     * @noinspection unused
     */
    public void handlePreparedPaymentRequest(EncryptedRequest encryptedRequest) {
        // Send the PreparedPaymentRequest to the merchant server, this contains a blob of encrypted values + base64encoded metadata
        //
        // Depending on the response from the merchant server, redirect to one of the following pages:
        //
        // - Successful page if the payment is done
        // - Unsuccessful page when the payment result is unsuccessful, you must supply a paymentProductId and an errorCode which will be translated
        // - Web view page to show an instructions page, or to go to a third party payment page
        //
        // Successful and Unsuccessful results have to be redirected to PaymentResultActivity

        selectionView.hideLoadingIndicator();

        // When the the payment result has come back, go to the successful/unsuccessful page:
        Intent paymentResultIntent = new Intent(this, PaymentResultActivity.class);

        // put shopping cart and payment request inside the intent
        paymentResultIntent.putExtra(Constants.INTENT_SHOPPINGCART, shoppingCart);
        paymentResultIntent.putExtra(Constants.INTENT_PAYMENT_CONTEXT, paymentContext);

        startActivity(paymentResultIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.BUNDLE_PAYMENT_PRODUCTS, basicPaymentProducts);
        outState.putSerializable(Constants.BUNDLE_SHOPPING_CART, shoppingCart);

        super.onSaveInstanceState(outState);
    }

    @Override
    // Callback for the the ok button on the error dialogs
    public void onClick(DialogInterface dialogInterface, int which) {
        // When an error has occurred the Activity is no longer valid. Destroy it and go one step back
        finish();
    }
}
