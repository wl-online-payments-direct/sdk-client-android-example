package com.onlinepayments.client.android.exampleapp.view.selectionview;

import android.content.DialogInterface.OnClickListener;

import com.onlinepayments.client.android.exampleapp.view.GeneralView;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItems;

/**
 * Interface for the Product selection view
 * <p>
 * Copyright 2020 Global Collect Services B.V
 */
public interface ProductSelectionView extends GeneralView {

    void renderDynamicContent(BasicPaymentItems paymentItems);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showTechnicalErrorDialog(OnClickListener listener);

    void showNoInternetDialog(OnClickListener listener);

    void showSpendingLimitExceededErrorDialog(
        OnClickListener positiveListener, OnClickListener negativeListener
    );

    /**
     * @noinspection unused
     */
    void hideAlertDialog();
}
