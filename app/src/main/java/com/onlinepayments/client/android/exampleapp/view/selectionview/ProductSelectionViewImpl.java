package com.onlinepayments.client.android.exampleapp.view.selectionview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.dialog.DialogUtil;
import com.onlinepayments.client.android.exampleapp.render.accountonfile.RenderAccountOnFile;
import com.onlinepayments.client.android.exampleapp.render.product.RenderPaymentItem;
import com.onlinepayments.sdk.client.android.model.paymentproduct.AccountOnFile;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItems;

/**
 * View for the Payment Product Selection Activity
 * Copyright 2020 Global Collect Services B.V
 */
public class ProductSelectionViewImpl implements ProductSelectionView {

    // The root of the entire View
    private final View rootView;

    // The Views in which the products/accounts that can be selected from will be rendered
    private final ViewGroup renderAccountOnFilesLayout;
    private final ViewGroup renderProductsLayout;

    // Renderhelpers for the dynamic content
    private final RenderPaymentItem paymentItemRenderer;
    private final RenderAccountOnFile accountOnFileRenderer;

    // Alert- and progressDialog that might be showing. We keep a reference so we will be able to
    // easily close them again.
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;

    public ProductSelectionViewImpl(Activity activity, @IdRes int id) {
        rootView = activity.findViewById(id);

        renderAccountOnFilesLayout = rootView.findViewById(R.id.listAccountsOnFile);
        renderProductsLayout = rootView.findViewById(R.id.listPaymentProducts);

        paymentItemRenderer = new RenderPaymentItem();
        accountOnFileRenderer = new RenderAccountOnFile();
    }

    @Override
    public void renderDynamicContent(BasicPaymentItems paymentItems) {
        // Render all basic paymentitems and accounts on file
        for (BasicPaymentItem basicPaymentItem : paymentItems.getBasicPaymentItems()) {
            paymentItemRenderer.renderPaymentItem(basicPaymentItem, renderProductsLayout);
        }

        // Check if there are accountsOnFile, then set their container and header to visible
        if (!paymentItems.getAccountsOnFile().isEmpty()) {

            rootView.findViewById(R.id.listAccountsOnFileHeader).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.listAccountsOnFile).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.listAccountsOnFileDivider).setVisibility(View.VISIBLE);

            // Render all accountsOnFile
            for (AccountOnFile accountOnFile : paymentItems.getAccountsOnFile()) {
                BasicPaymentItem correspondingPaymentItem = paymentItems.getBasicPaymentItemById(accountOnFile.getPaymentProductId());
                accountOnFileRenderer.renderAccountOnFile(accountOnFile, correspondingPaymentItem, renderAccountOnFilesLayout);
            }
        }
    }

    @Override
    public void showLoadingIndicator() {
        Context c = rootView.getContext();
        String title = c.getString(R.string.app_loading_title);
        String msg = c.getString(R.string.app_loading_body);
        progressDialog = DialogUtil.showProgressDialog(c, title, msg);
    }

    @Override
    public void hideLoadingIndicator() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showTechnicalErrorDialog(OnClickListener listener) {
        Context context = rootView.getContext();
        alertDialog = DialogUtil.showAlertDialog(
                context,
                R.string.errors_title,
                R.string.errors_technicalProblem,
                R.string.app_errors_general_button,
                null
        );
    }

    @Override
    public void showNoInternetDialog(OnClickListener listener) {
        Context context = rootView.getContext();
        alertDialog = DialogUtil.showAlertDialog(
                context,
                R.string.app_errors_noInternetConnection_title,
                R.string.app_errors_noInternetConnection_bodytext,
                R.string.app_errors_general_button,
                listener
        );
    }

    @Override
    public void showSpendingLimitExceededErrorDialog(OnClickListener positiveListener, OnClickListener negativeListener) {
        Context c = rootView.getContext();
        String title = c.getString(R.string.app_errors_spendingLimitExceeded_title);
        String msg = c.getString(R.string.app_errors_spendingLimitExceeded_bodyText);
        String posButton = c.getString(R.string.app_errors_spendingLimitExceeded_button_changeOrder);
        String negButton = c.getString(R.string.app_errors_spendingLimitExceeded_button_tryOtherMethod);
        alertDialog = new AlertDialog.Builder(c)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(posButton, positiveListener)
                .setNegativeButton(negButton, negativeListener)
                .create();
        alertDialog.show();
    }

    @Override
    public void hideAlertDialog() {
        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public View getRootView() {
        return rootView;
    }
}
