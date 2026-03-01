/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.render.field;

import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.BasicPaymentProduct;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.productField.PaymentProductField;

/**
 * This class implements the RenderLabelInterface and
 * handles the rendering of the label for one payment product field
 */
public class RenderLabel implements RenderLabelInterface {

    @Override
    public TextView renderLabel(
        PaymentProductField field,
        BasicPaymentProduct selectedPaymentProduct,
        ViewGroup rowView
    ) {
        TextView label = new TextView(rowView.getContext());

        // Get the label text
        String labelText = field.getLabel();

        // Create new label
        label.setTextAppearance(R.style.ListViewTextView);
        label.setText(Html.fromHtml(labelText));
        rowView.addView(label);

        return label;
    }
}
