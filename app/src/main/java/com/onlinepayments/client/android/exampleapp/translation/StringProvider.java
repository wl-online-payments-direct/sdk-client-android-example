package com.onlinepayments.client.android.exampleapp.translation;

import android.annotation.SuppressLint;
import android.content.Context;

import java.security.InvalidParameterException;

/**
 * Handles all translation related functionality
 * Copyright 2024 Global Collect Services B.V
 */
public class StringProvider {

    private static final String TRANSLATION_PREFIX_VALIDATION = "validationErrors_";
    private static final String TRANSLATION_PREFIX_COBRAND = "cobrands_";

    // Marker for keys that could not be found
    public static final String BAD_TRANSLATION_KEY_MARKER = "???";

    // We are storing the ApplicationContext, which is no memory leak as it lives for as long as the
    // application is around anyways.
    @SuppressLint("StaticFieldLeak")
    private static StringProvider INSTANCE;

    // Context used for loading resources from strings.xml
    private final Context context;

    public static synchronized StringProvider getInstance(Context context) {
        if (context == null) {
            throw new InvalidParameterException(
                "Error creating Translator, context may not be null.");
        }

        if (INSTANCE == null) {
            INSTANCE = new StringProvider(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private StringProvider(Context context) {
        this.context = context;
    }

    /**
     * Gets Validation message from the SDK resources file
     *
     * @param errorMessageId, the String which is to be retrieved
     * @return the retrieved value
     */
    public String getValidationMessage(String errorMessageId) {

        if (errorMessageId == null) {
            throw new InvalidParameterException(
                "Error retrieving validation message, errorMessageId may not be null");
        }
        return retrieveString(TRANSLATION_PREFIX_VALIDATION + errorMessageId);
    }

    /**
     * Gets the co-brand notification/tooltip value.
     *
     * @param coBrandMessageId The identifier of the coBrand message that is requested
     * @return The retrieved value
     */
    public String getCoBrandNotificationText(String coBrandMessageId) {
        if (coBrandMessageId == null) {
            throw new InvalidParameterException(
                "Error retrieving coBrand description, paymentProductId may not be null");
        }
        return retrieveString(TRANSLATION_PREFIX_COBRAND + coBrandMessageId);
    }

    /**
     * Retrieves a String based on a String representation of the resource rather than an @StringRes Integer
     *
     * @param identifier, the string identifier to be retrieved
     * @return the retrieved value or the identifier used for retrieval
     */
    @SuppressWarnings({"DiscouragedApi", "deprecated"})
    public String retrieveString(String identifier) {

        if (identifier == null) {
            throw new InvalidParameterException(
                "Error translating string, identifier may not be null");
        }
        if (context == null) {
            throw new InvalidParameterException("Error translating string, context may not be null");
        }

        int resourceId =
            context.getResources().getIdentifier(identifier, "string", context.getPackageName());
        if (resourceId == 0) {

            // If the String could not be found, return the key, marked with question marks to show that the String could not be found
            return identifier;
        } else {
            return context.getResources().getString(resourceId);
        }
    }

    public static boolean isBadTranslationKey(String key) {
        return key.startsWith(BAD_TRANSLATION_KEY_MARKER);
    }
}
