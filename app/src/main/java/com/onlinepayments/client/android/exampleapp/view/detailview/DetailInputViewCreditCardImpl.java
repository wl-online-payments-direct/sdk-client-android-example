package com.onlinepayments.client.android.exampleapp.view.detailview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.IdRes;
import android.view.View;
import android.widget.EditText;

import com.onlinepayments.client.android.exampleapp.exception.ViewNotInitializedException;
import com.onlinepayments.client.android.exampleapp.render.field.AccountOnFileCardTextWatcher;
import com.onlinepayments.client.android.exampleapp.render.iinlookup.IinLookupTextWatcher;
import com.onlinepayments.client.android.exampleapp.render.iinlookup.RenderIinCoBranding;
import com.onlinepayments.client.android.exampleapp.render.persister.IinDetailsPersister;
import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;
import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentItem;
import com.onlinepayments.sdk.client.android.model.paymentproduct.displayhints.DisplayHintsPaymentItem;
import com.onlinepayments.sdk.client.android.model.validation.ValidationErrorMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * View for the DetailInputActivity with added functionality for credit card payment products
 *
 * Copyright 2020 Global Collect Services B.V
 */
public class DetailInputViewCreditCardImpl extends DetailInputViewImpl implements DetailInputViewCreditCard {

    public static final String CREDIT_CARD_NUMBER_FIELD_ID = "cardNumber";

    private EditText creditCardField;
    private RenderIinCoBranding coBrandRenderer;

    // To keep track of the original account on file value, if there is one
    private String accountOnFileValue;


    public DetailInputViewCreditCardImpl(Activity activity, @IdRes int id) {
        super (activity, id);

        coBrandRenderer = new RenderIinCoBranding();
    }

    @Override
    public void initializeCreditCardField(IinLookupTextWatcher iinLookupTextWatcher, InputDataPersister inputDataPersister, IinDetailsPersister iinDetailsPersister, boolean hasAccountOnFile) {
        creditCardField = rootView.findViewWithTag(CREDIT_CARD_NUMBER_FIELD_ID);
        if (creditCardField == null) {
            throw new ViewNotInitializedException("CreditCardField has not been found, did you forget to render the input fields?");
        }

        // Only set account on file text changed watcher & focus listener when the product contains account on file
        if (hasAccountOnFile) {
            // Only set accountOnFileValue, if no value was previously set
            if (accountOnFileValue == null) { accountOnFileValue = creditCardField.getText().toString(); }
            AccountOnFileCardTextWatcher accountOnFileCardTextWatcher = new AccountOnFileCardTextWatcher(inputDataPersister, CREDIT_CARD_NUMBER_FIELD_ID, creditCardField, accountOnFileValue, iinLookupTextWatcher);
            creditCardField.addTextChangedListener(accountOnFileCardTextWatcher);
            creditCardField.setHint(accountOnFileValue);

            View.OnFocusChangeListener accountOnFileFocusListener = (v, hasFocus) -> {
                String creditCardFieldInput = creditCardField.getText().toString();

                // Only clear input when creditCardFieldInput is accountOnFileValue
                if (hasFocus && creditCardFieldInput.equals(accountOnFileValue)) {
                   creditCardField.getText().clear();
                } else if (!hasFocus && creditCardFieldInput.equals("")) {
                    accountOnFileCardTextWatcher.removeIinLookupTextWatcher();
                    iinDetailsPersister.setIinDetailsResponse(null);

                    // Only re-set account on file value if nothing was entered
                    creditCardField.setText(accountOnFileValue);

                    // Handle when the original account on file value was reset
                    handleResetOriginalAccountOnFileValue();
                }
            };
            creditCardField.setOnFocusChangeListener(accountOnFileFocusListener);
        } else {
            // If no account on file, immediately set the iinLookupTextWatcher
            creditCardField.addTextChangedListener(iinLookupTextWatcher);
        }
    }

    @Override
    public void renderLuhnValidationMessage() {
        renderValidationHelper.renderValidationMessage(new ValidationErrorMessage("luhn", CREDIT_CARD_NUMBER_FIELD_ID, null), null);
    }

    @Override
    public void renderNotAllowedInContextValidationMessage() {
        renderValidationHelper.renderValidationMessage(new ValidationErrorMessage("allowedInContext", CREDIT_CARD_NUMBER_FIELD_ID, null), null);
    }

    @Override
    public void removeCreditCardValidationMessage() {
        renderValidationHelper.removeValidationMessage(renderInputFieldsLayout, CREDIT_CARD_NUMBER_FIELD_ID);
    }

    @Override
    public void renderPaymentProductLogoByIdInCreditCardField(PaymentItem paymentItem) {
        // Get the logo from its payment product
        if (!paymentItem.getDisplayHintsList().isEmpty()) {
            DisplayHintsPaymentItem displayHints = paymentItem.getDisplayHintsList().get(0);

            Picasso.get()
                    .load(displayHints.getLogoUrl())
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            int scaledHeight = (int) creditCardField.getTextSize();
                            int scaledWidth = (int) (bitmap.getWidth() * ((double) scaledHeight / (double) bitmap.getHeight()));

                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
                            Drawable resizedDrawable = new BitmapDrawable(rootView.getContext().getResources(), resizedBitmap);

                            // Set compoundDrawables allow you to place a image at a certain position
                            creditCardField.setCompoundDrawablesWithIntrinsicBounds(null, null, resizedDrawable, null);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {}

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {}
                    });
        }
    }

    @Override
    public void removeDrawableInEditText() {
        creditCardField.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    @Override
    public void renderCoBrandNotification(List<BasicPaymentItem> paymentProductsAllowedInContext, View.OnClickListener listener) {
        // Show the user he can choose another cobrand if there are indeed more cobrands available
        if (paymentProductsAllowedInContext.size() > 1) {
            // Retrieve the logo from the top most PaymentProduct
            coBrandRenderer.renderIinCoBrandNotification(rootView.getContext(),
                    paymentProductsAllowedInContext,
                    renderInputFieldsLayout,
                    CREDIT_CARD_NUMBER_FIELD_ID,
                    listener);
        }
    }

    @Override
    public void removeCoBrandNotification() {
        coBrandRenderer.removeIinCoBrandNotification(renderInputFieldsLayout, CREDIT_CARD_NUMBER_FIELD_ID);
    }

    private void handleResetOriginalAccountOnFileValue() {
        removeCreditCardValidationMessage();
        activatePayButton();
    }
}
