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

package com.onlinepayments.client.android.exampleapp.configuration;

public class Constants {
    /**
     * Application Identifier, used for identifying the application in network calls
     **/
    public static String APPLICATION_IDENTIFIER =
        "OnlinePayments Android Java Example Application/v2.4.2";

    /**
     * Intent data keys
     **/
    public final static String INTENT_PAYMENT_CONTEXT = "paymentContext";
    public final static String INTENT_SHOPPINGCART = "shoppingcart";
    public final static String INTENT_SELECTED_ITEM = "selected_item";
    public final static String INTENT_SELECTED_ACCOUNT_ON_FILE = "selected_account_on_file";
    public final static String INTENT_ERRORMESSAGE = "errorMessage";

    /**
     * Bundle data keys
     **/
    public final static String BUNDLE_PAYMENT_PRODUCTS = "bundle_paymentproducts";
    public final static String BUNDLE_SHOPPING_CART = "bundle_shoppingcart";
    public final static String BUNDLE_INPUTDATAPERSISTER = "bundle_inputdatapersister";
    public final static String BUNDLE_INPUTVALIDATIONPERSISTER = "bundle_inputvalidationpersister";
    public final static String BUNDLE_IINDETAILSPERSISTER = "bundle_iindetailspersister";
    public final static String BUNDLE_RENDERED = "bundle_rendered";

    /**
     * Information from the merchant
     **/
    public final static String MERCHANT_CLIENT_SESSION_IDENTIFIER =
        "merchant_client_session_identifier";
    public final static String MERCHANT_CUSTOMER_IDENTIFIER = "merchant_customer_identifier";
    public final static String MERCHANT_MERCHANT_IDENTIFIER = "merchant_merchant_identifier";
    public final static String MERCHANT_NAME = "merchant_name";
    public final static String MERCHANT_CLIENT_API_URL = "merchant_client_api_url";
    public final static String MERCHANT_ASSET_URL = "merchant_asset_url";
    public final static String MERCHANT_ENVIRONMENT_IS_PRODUCTION =
        "merchant_environment_is_production";

    /**
     * Google Pay
     **/
    public static final int PAYMENTPRODUCTID_GOOGLEPAY = 320;
    public static final int GOOGLE_API_VERSION = 2;
    public final static String GOOGLE_PAY_TOKEN_FIELD_ID = "encryptedPaymentData";
}
