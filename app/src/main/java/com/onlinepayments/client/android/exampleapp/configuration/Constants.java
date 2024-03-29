package com.onlinepayments.client.android.exampleapp.configuration;

/**
 * Copyright 2020 Global Collect Services B.V
 *
 */
public class Constants {

	/** Application Identifier, used for identifying the application in network calls **/
	public static String APPLICATION_IDENTIFIER = "OnlinePayments Android Example Application/v2.1.2";

	/** Intent data keys **/
	public final static String INTENT_PAYMENT_CONTEXT 			 = "paymentContext";
	public final static String INTENT_PAYMENT_REQUEST			 = "paymentRequest";
	public final static String INTENT_SHOPPINGCART 			 	 = "shoppingcart";
	public final static String INTENT_MASKED_WALLET			 	 = "masked_wallet";
	public final static String INTENT_FULL_WALLET				 = "full_wallet";
	public final static String INTENT_GC_SESSION	 			 = "coresession";
	public final static String INTENT_LOADED_PRODUCTS 		 	 = "loaded_paymentproducts";
	public final static String INTENT_SELECTED_ITEM			 	 = "selected_item";
	public final static String INTENT_SELECTED_ACCOUNT_ON_FILE   = "selected_account_on_file";
	public final static String INTENT_SUCCESSFUL				 = "successful";
	public final static String INTENT_ERRORMESSAGE				 = "errorMessage";
	public final static String INTENT_URL_WEBVIEW				 = "url";
	public final static String INTENT_GROUP_PAYMENTPRODUCTS		 = "group_paymentproducts";

	/** Bundle data keys **/
	public final static String BUNDLE_PAYMENT_PRODUCTS 			= "bundle_paymentproducts";
	public final static String BUNDLE_GC_SESSION 				= "bundle_coresession";
	public final static String BUNDLE_SHOPPING_CART 			= "bundle_shoppingcart";
	public final static String BUNDLE_INPUTDATAPERSISTER = "bundle_inputdatapersister";
	public final static String BUNDLE_INPUTVALIDATIONPERSISTER	= "bundle_inputvalidationpersister";
	public final static String BUNDLE_IINDETAILSPERSISTER		= "bundle_iindetailspersister";
	public final static String BUNDLE_RENDERED					= "bundle_rendered";

	/** Information from the merchant **/
	public final static String MERCHANT_CLIENT_SESSION_IDENTIFIER	= "merchant_client_session_identifier";
	public final static String MERCHANT_CUSTOMER_IDENTIFIER			= "merchant_customer_identifier";
	public final static String MERCHANT_MERCHANT_IDENTIFIER			= "merchant_merchant_identifier";
	public final static String MERCHANT_NAME						= "merchant_name";
	public final static String MERCHANT_CLIENT_API_URL				= "merchant_client_api_url";
	public final static String MERCHANT_ASSET_URL					= "merchant_asset_url";
	public final static String MERCHANT_ENVIRONMENT_IS_PRODUCTION	= "merchant_environmnent_is_production";

	/** Google Pay **/
	public static final String PAYMENTPRODUCTID_GOOGLEPAY = "320";
	public static final int GOOGLE_API_VERSION = 2;
	public final static String GOOGLE_PAY_TOKEN_FIELD_ID = "encryptedPaymentData";
}
