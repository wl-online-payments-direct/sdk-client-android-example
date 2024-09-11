package com.onlinepayments.client.android.exampleapp.activities;

import static com.onlinepayments.client.android.exampleapp.configuration.Constants.GOOGLE_PAY_TOKEN_FIELD_ID;
import static com.onlinepayments.client.android.exampleapp.configuration.Constants.PAYMENTPRODUCTID_GOOGLEPAY;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.onlinepayments.client.android.exampleapp.BuildConfig;
import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.configuration.CheckCommunication;
import com.onlinepayments.client.android.exampleapp.configuration.Constants;
import com.onlinepayments.client.android.exampleapp.model.ShoppingCart;
import com.onlinepayments.client.android.exampleapp.util.GooglePay;
import com.onlinepayments.client.android.exampleapp.view.selectionview.ProductSelectionView;
import com.onlinepayments.client.android.exampleapp.view.selectionview.ProductSelectionViewImpl;
import com.onlinepayments.sdk.client.android.exception.EncryptDataException;
import com.onlinepayments.sdk.client.android.listener.BasicPaymentItemsResponseListener;
import com.onlinepayments.sdk.client.android.listener.PaymentProductResponseListener;
import com.onlinepayments.sdk.client.android.listener.PaymentRequestPreparedListener;
import com.onlinepayments.sdk.client.android.model.PaymentContext;
import com.onlinepayments.sdk.client.android.model.PaymentRequest;
import com.onlinepayments.sdk.client.android.model.PreparedPaymentRequest;
import com.onlinepayments.sdk.client.android.model.api.ErrorResponse;
import com.onlinepayments.sdk.client.android.model.paymentproduct.AccountOnFile;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItems;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentProduct;
import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentItem;
import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentProduct;
import com.onlinepayments.sdk.client.android.model.paymentproduct.displayhints.DisplayHintsPaymentItem;
import com.onlinepayments.sdk.client.android.session.Session;

import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.List;


/**
 * Activity that lists all the available payment options
 * Copyright 2020 Global Collect Services B.V
 */
public class PaymentProductSelectionActivity extends ShoppingCartActivity implements DialogInterface.OnClickListener {

    private static final String TAG = PaymentProductSelectionActivity.class.getName();

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 42;

    // The view belonging to this activity
    private ProductSelectionView selectionView;

    private ShoppingCart shoppingCart;

    // The session object that is used to connect to the API
    private Session session;

    // Parameters used to initialize the connection
    private String clientSessionId;
    private String customerId;
    private String merchantId;
    private String merchantName;
    private String clientApiUrl;
    private String assetUrl;
    private boolean environmentIsProduction;

    // Variables required to retrieve the payment items that are available for payment
    private PaymentContext paymentContext;
    private boolean groupPaymentProducts;
    private PaymentProduct paymentProduct;

    // Loaded payment product and selected Account On File information
    private BasicPaymentItems paymentItems;
    private AccountOnFile accountOnFile;

    private final PaymentProductResponseListener paymentProductResponseListener = new PaymentProductResponseListener() {
        @Override
        public void onSuccess(@NonNull PaymentProduct paymentProduct) {
            PaymentProductSelectionActivity.this.paymentProduct = paymentProduct;
            handlePaymentItemCallBack(paymentProduct);
        }

        @Override
        public void onApiError(ErrorResponse errorResponse) {
            handleApiErrorOrException();
        }

        @Override
        public void onException(Throwable throwable) {
            handleApiErrorOrException();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment_product);

        selectionView = new ProductSelectionViewImpl(this, R.id.payment_product_selection_view_layout);

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

        if (paymentItems == null) {
            try {
                /*
                 Instantiate the Session.
                 This session has logging enabled, meaning that requests made to the server and responses received from the server will be logged.
                 By default logging is disabled.
                 Logging can also be enabled or disabled at a later stage by calling `session.setLoggingEnabled(Boolean)`.
                 Logging should be disabled in production.
                 To ensure that - for example - logging is enabled in debug and disabled in release, you can use a build property whose value depends on the build variant of your app.
                 */
                session = new Session(clientSessionId, customerId, clientApiUrl, assetUrl, environmentIsProduction, Constants.APPLICATION_IDENTIFIER, BuildConfig.LOGGING_ENABLED);
            } catch (InvalidParameterException e) {
                Log.e(TAG, "Instantiating Session failed with: " + e.getMessage());
                selectionView.showTechnicalErrorDialog(this);
                return;
            }

            selectionView.showLoadingIndicator();
            session.getBasicPaymentItems(getApplicationContext(), paymentContext, groupPaymentProducts, new BasicPaymentItemsResponseListener() {
                @Override
                public void onSuccess(@NonNull BasicPaymentItems basicPaymentItems) {
                    if (basicPaymentItems.getBasicPaymentItems() != null && !basicPaymentItems.getBasicPaymentItems().isEmpty()) {

                        paymentItems = basicPaymentItems;

                        selectionView.renderDynamicContent(basicPaymentItems);

                        updateLogos(basicPaymentItems.getBasicPaymentItems());
                    } else {
                        selectionView.showTechnicalErrorDialog(PaymentProductSelectionActivity.this);
                    }
                    selectionView.hideLoadingIndicator();
                }

                @Override
                public void onApiError(ErrorResponse errorResponse) {
                    handleApiErrorOrException();
                }

                @Override
                public void onException(Throwable throwable) {
                    handleApiErrorOrException();
                }
            });
        } else {
            selectionView.renderDynamicContent(paymentItems);
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
        environmentIsProduction = intent.getBooleanExtra(Constants.MERCHANT_ENVIRONMENT_IS_PRODUCTION, false);
        paymentContext = (PaymentContext) intent.getSerializableExtra(Constants.INTENT_PAYMENT_CONTEXT);
        groupPaymentProducts = intent.getBooleanExtra(Constants.INTENT_GROUP_PAYMENTPRODUCTS, false);
        shoppingCart = (ShoppingCart) intent.getSerializableExtra(Constants.INTENT_SHOPPINGCART);
    }

    private void initializeSavedInstanceStateData(Bundle savedInstanceState) {
        paymentItems = (BasicPaymentItems) savedInstanceState.getSerializable(Constants.BUNDLE_PAYMENT_PRODUCTS);
        shoppingCart = (ShoppingCart) savedInstanceState.getSerializable(Constants.BUNDLE_SHOPPING_CART);
        session = (Session) savedInstanceState.getSerializable(Constants.BUNDLE_GC_SESSION);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//    }

    private void updateLogos(List<BasicPaymentItem> basicPaymentItems) {
        // if you want to specify the size of the logos you update, set your desired width and height
        int newWidth = 100;
        int newHeight = 100;

        for (BasicPaymentItem paymentItem : basicPaymentItems) {
            if(!paymentItem.getDisplayHintsList().isEmpty()) {
                DisplayHintsPaymentItem displayHints = paymentItem.getDisplayHintsList().get(0);

                if (displayHints.getLogo() != null) {
                    Bitmap originalLogo = ((BitmapDrawable) displayHints.getLogo()).getBitmap();

                    BitmapDrawable resizedLogo = this.resizedLogo(originalLogo, newWidth, newHeight);

                    displayHints.setLogo(resizedLogo);
                }
            }
        }
    }

    private BitmapDrawable resizedLogo(Bitmap originalLogo, int width, int height) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalLogo, width, height, true);

        return new BitmapDrawable(getResources(), resizedBitmap);
    }

    // The callback method for when a user selects a payment product
    public void onPaymentProductSelected(View v) {
        selectionView.showLoadingIndicator();
        if (v.getTag() instanceof BasicPaymentProduct) {
            // Reset accountOnFile to null, so its contents will not be filled in for a BasicPaymentProduct
            accountOnFile = null;
            String paymentProductId = ((BasicPaymentProduct) v.getTag()).getId();
            session.getPaymentProduct(getApplicationContext(), paymentProductId, paymentContext, paymentProductResponseListener);

        } else if (v.getTag() instanceof AccountOnFile) {
            // Store the Account on file so it can be added to the intent later on.
            accountOnFile = (AccountOnFile) v.getTag();
            session.getPaymentProduct(getApplicationContext(), accountOnFile.getPaymentProductId(), paymentContext, paymentProductResponseListener);
        } else {
            throw new InvalidParameterException("Tag in view is not of a valid type");
        }
    }

    // Determine what view should be served next, based on whether the product has inputfields.
    // For some products special rules apply, which are also handled here.
    private void handlePaymentItemCallBack(PaymentItem paymentItem) {
        if (paymentItem == null) {
            selectionView.hideLoadingIndicator();
            selectionView.showTechnicalErrorDialog(this);

        } else if (PAYMENTPRODUCTID_GOOGLEPAY.equals(paymentItem.getId())) {
            // Google pay requires a different flow even though it has fields. The fields should
            // not be shown to the user, but they will be filled after the user has completed the
            // Google pay flow.
            startGooglePay((PaymentProduct) paymentItem);

        } else if (paymentItem.getPaymentProductFields().isEmpty()) {
            // For payment products that do not have fields configured other actions are required to
            // complete the payment. These actions may be a redirect to an external payment products'
            // website, or showing instructions to complete the payment.
            // Currently this app does not contain examples for these kinds of products. Instead we
            // will go to the result activity directly.
            startNextActivity(new Intent(this, PaymentResultActivity.class));

        } else {
            // Ask the user for its payment details in the next activity
            determineAndStartDetailInputActivity(paymentItem);
        }
    }

    private void startGooglePay(PaymentProduct paymentProduct) {
        if (!merchantId.isEmpty() && !merchantName.isEmpty()) {
            GooglePay googlePay = new GooglePay(this, paymentContext, paymentProduct, merchantId, merchantName);
            googlePay.start(environmentIsProduction);
        } else {
            Toast
                .makeText(this, "MerchantId & MerchantName cannot be empty when you want to use GooglePay", Toast.LENGTH_LONG)
                .show();
        }
        selectionView.hideLoadingIndicator();
    }

    private void determineAndStartDetailInputActivity(PaymentItem paymentItem) {
        // Determine what DetailInputActivity to load. The base-class just renders the fields and
        // performs default validation on the fields. In some cases this is not enough however. In
        // these cases a subclass of the DetailInputActivity will be loaded that has additional
        // functionality for these specific products/methods.
        Intent detailInputActivityIntent;
        if (((PaymentProduct) paymentItem).getPaymentMethod().equals("card")) {
            detailInputActivityIntent = new Intent(this, DetailInputActivityCreditCards.class);

        } else {
            detailInputActivityIntent = new Intent(this, DetailInputActivity.class);

        }
        detailInputActivityIntent.putExtra(Constants.INTENT_SELECTED_ITEM, paymentItem);
        startNextActivity(detailInputActivityIntent);
    }

    private void startNextActivity(Intent detailInputActivityIntent) {
        // Add data to intent for the detail activity input
        detailInputActivityIntent.putExtra(Constants.INTENT_PAYMENT_CONTEXT, paymentContext);
        detailInputActivityIntent.putExtra(Constants.INTENT_SELECTED_ACCOUNT_ON_FILE, accountOnFile);
        detailInputActivityIntent.putExtra(Constants.INTENT_SHOPPINGCART, shoppingCart);
        detailInputActivityIntent.putExtra(Constants.INTENT_GC_SESSION, session);

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

                    String paymentDataJSONString = paymentData.toJson();
                    try {
                        JSONObject paymentDataJSON = new JSONObject(paymentDataJSONString);

                        JSONObject paymentMethodData = paymentDataJSON.getJSONObject("paymentMethodData");
                        JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
                        String unformattedGooglePayToken = tokenizationData.getString("token");

                        // Token needs to be formatted when using it to create a payment with mobilePaymentMethodSpecificInput.encryptedPaymentData
                        String formattedGooglePayToken = JSONObject.quote(unformattedGooglePayToken);

                        PaymentRequest paymentRequest = new PaymentRequest(paymentProduct);
                        paymentRequest.setValue(GOOGLE_PAY_TOKEN_FIELD_ID, formattedGooglePayToken);
                        paymentRequest.validate();

                        session.preparePaymentRequest(paymentRequest, getApplicationContext(), new PaymentRequestPreparedListener() {
                            @Override
                            public void onPaymentRequestPrepared(PreparedPaymentRequest preparedPaymentRequest) {
                                handlePreparedPaymentRequest(preparedPaymentRequest);
                            }

                            @Override
                            public void onFailure(EncryptDataException e) {
                                Log.e(TAG, "Could not prepare Payment Request due to exception: " + e.getMessage());
                                handleApiErrorOrException();
                            }
                        });
                    } catch (Exception e) {
                        Log.e(TAG, "Could not parse malformed JSON: \"" + paymentDataJSONString + "\"");
                    }

                    break;
                case RESULT_CANCELED:
                    Log.i(TAG, "Google Pay payment was cancelled");
                    selectionView.hideLoadingIndicator();
                    break;
                case AutoResolveHelper.RESULT_ERROR:
                    Status status = AutoResolveHelper.getStatusFromIntent(data);
                    Log.e(TAG, "Something went wrong whilst making a Google Pay payment; errorCode: " + status);
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

    public void handlePreparedPaymentRequest(PreparedPaymentRequest preparedPaymentRequest) {
        // Send the PreparedPaymentRequest to the merchant server, this contains a blob of encrypted values + base64encoded metadata
        //
        // Depending on the response from the merchant server, redirect to one of the following pages:
        //
        // - Successful page if the payment is done
        // - Unsuccesful page when the payment result is unsuccessful, you must supply a paymentProductId and an errorcode which will be translated
        // - Webview page to show an instructions page, or to go to a third party payment page
        //
        // Successful and Unsuccessful results have to be redirected to PaymentResultActivity

        selectionView.hideLoadingIndicator();

        // When the the payment result has come back, go to the successful/unsuccessful page:
        Intent paymentResultIntent = new Intent(this, PaymentResultActivity.class);

        //put shopping cart and payment request inside the intent
        paymentResultIntent.putExtra(Constants.INTENT_SHOPPINGCART, shoppingCart);
        paymentResultIntent.putExtra(Constants.INTENT_PAYMENT_CONTEXT, paymentContext);

        // Add errormessage if there was an error
        //String errorCode = "errorCode";
        //paymentResultIntent.putExtra(Constants.INTENT_ERRORMESSAGE, null);

        startActivity(paymentResultIntent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.BUNDLE_PAYMENT_PRODUCTS, paymentItems);
        outState.putSerializable(Constants.BUNDLE_SHOPPING_CART, shoppingCart);
        outState.putSerializable(Constants.BUNDLE_GC_SESSION, session);

        super.onSaveInstanceState(outState);
    }

    @Override
    // Callback for the the ok button on the error dialogs
    public void onClick(DialogInterface dialogInterface, int which) {
        // When an error has occurred the Activity is no longer valid. Destroy it and go one step back
        finish();
    }
}
