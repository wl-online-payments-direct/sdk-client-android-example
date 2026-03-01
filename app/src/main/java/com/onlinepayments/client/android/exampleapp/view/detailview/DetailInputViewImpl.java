/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.view.detailview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.IdRes;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.dialog.DialogUtil;
import com.onlinepayments.client.android.exampleapp.render.field.RenderInputDelegate;
import com.onlinepayments.client.android.exampleapp.render.field.RenderTooltip;
import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.client.android.exampleapp.render.persister.InputValidationPersister;
import com.onlinepayments.client.android.exampleapp.render.validation.RenderValidationHelper;
import com.onlinepayments.sdk.client.android.domain.PaymentContextWithAmount;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.PaymentProduct;
import com.onlinepayments.sdk.client.android.domain.validation.ValidationErrorMessage;

/**
 * View for the DetailInputActivity
 */
public class DetailInputViewImpl implements DetailInputView {

    // The root of the entire View
    protected View rootView;

    // The View in which the payment product fields will be rendered
    protected ViewGroup renderInputFieldsLayout;

    // RendererHelpers for the dynamic content
    protected RenderInputDelegate fieldRenderer;
    protected RenderValidationHelper renderValidationHelper;

    private ProgressDialog progressDialog;

    public DetailInputViewImpl(Activity activity, @IdRes int id) {
        rootView = activity.findViewById(id);

        renderInputFieldsLayout = rootView.findViewById(R.id.render_input_fields_layout);
        fieldRenderer = new RenderInputDelegate(renderInputFieldsLayout);
        renderValidationHelper = new RenderValidationHelper((ViewGroup) rootView);
    }

    @Override
    public void renderDynamicContent(
        InputDataPersister inputDataPersister,
        PaymentContextWithAmount paymentContext,
        InputValidationPersister inputValidationPersister
    ) {
        fieldRenderer.renderPaymentInputFields(inputDataPersister, paymentContext);
        renderValidationHelper.renderValidationMessages(inputValidationPersister);
    }

    @Override
    public void renderRememberMeCheckBox(boolean isChecked) {
        ViewGroup rememberLayout = rootView.findViewById(R.id.rememberLayout);

        // Remove the rememberMe tooltip popup that is potentially already in the view
        View v = rememberLayout.findViewWithTag("rememberMe");
        rememberLayout.removeView(v);

        rememberLayout.setVisibility(View.VISIBLE);

        RenderTooltip renderTooltip = new RenderTooltip();
        renderTooltip.renderRememberMeTooltip(
            rootView.getContext(),
            rootView.findViewById(R.id.rememberLayout)
        );
    }

    @Override
    public void removeAllFieldViews() {
        renderInputFieldsLayout.removeAllViews();
    }

    @Override
    public void activatePayButton() {
        Button payButton = rootView.findViewById(R.id.payNowButton);
        payButton.setVisibility(View.VISIBLE);

        payButton = rootView.findViewById(R.id.payNowButtonDisabled);
        payButton.setVisibility(View.GONE);
    }

    @Override
    public void deactivatePayButton() {
        Button payButton = rootView.findViewById(R.id.payNowButtonDisabled);
        payButton.setVisibility(View.VISIBLE);

        payButton = rootView.findViewById(R.id.payNowButton);
        payButton.setVisibility(View.GONE);
    }

    @Override
    public void renderValidationMessage(
        ValidationErrorMessage validationResult,
        PaymentProduct paymentProduct
    ) {
        renderValidationHelper.renderValidationMessage(validationResult);
    }

    @Override
    public void renderValidationMessages(
        InputValidationPersister inputValidationPersister,
        PaymentProduct paymentProduct
    ) {
        renderValidationHelper.renderValidationMessages(inputValidationPersister);
    }

    @Override
    public void hideTooltipAndErrorViews(InputValidationPersister inputValidationPersister) {
        // Hide all dynamic rendered toolTipTexts
        fieldRenderer.hideTooltipTexts(renderInputFieldsLayout);

        // Hide the hardcoded rendered toolTipTexts
        fieldRenderer.hideTooltipTexts(rootView.findViewById(R.id.rememberLayoutParent));

        // Hide all
        renderValidationHelper.hideValidationMessages(inputValidationPersister);
    }

    @Override
    public void showLoadDialog() {
        String title = rootView.getContext().getString(R.string.app_loading_title);
        String msg = rootView.getContext().getString(R.string.app_loading_body);
        progressDialog = DialogUtil.showProgressDialog(rootView.getContext(), title, msg);
    }

    @Override
    public void hideLoadDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void setFocusAndCursorPosition(String fieldId, int cursorPosition) {
        View v = rootView.findViewWithTag(fieldId);
        if (v != null) {
            v.requestFocus();
            if (v instanceof EditText && cursorPosition >= 0) {
                ((EditText) v).setSelection(cursorPosition);
            }
        }
    }

    @Override
    public View getViewWithFocus() {
        return rootView.findFocus();
    }

    @Override
    public View getRootView() {
        return rootView;
    }
}
