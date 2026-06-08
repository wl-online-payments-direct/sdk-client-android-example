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

package com.onlinepayments.client.android.exampleapp.render.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.BasicPaymentProduct;
import com.squareup.picasso.Picasso;

import java.security.InvalidParameterException;

/**
 * Renders payment products
 */
public class RenderPaymentItem implements RenderPaymentItemInterface {

    @Override
    public void renderPaymentItem(BasicPaymentProduct product, ViewGroup parent) {

        if (product == null) {
            throw new InvalidParameterException(
                "Error renderingPaymentProduct, product may not be null");
        }

        if (parent == null) {
            throw new InvalidParameterException(
                "Error renderingPaymentProduct, parent may not be null");
        }

        // Inflate the activity_select_payment_product_render layout
        LayoutInflater inflater =
            (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View paymentProductLayout =
            inflater.inflate(R.layout.activity_render_payment_product, parent, false);

        // Set the belonging accountOnFile on the tag of the row so we can retrieve it when clicked
        paymentProductLayout.findViewById(R.id.paymentProductRow).setTag(product);

        // Get the TextView and ImageView which will be filled
        TextView paymentProductNameTextView =
            paymentProductLayout.findViewById(R.id.paymentProductName);
        ImageView paymentProductNameLogoImageView =
            paymentProductLayout.findViewById(R.id.paymentProductLogo);

        // Set payment item name & logo
        if (!Boolean.parseBoolean(product.getLogo())) {

            paymentProductNameTextView.setText(product.getLabel());
            String logoUrl = product.getLogo();
            if (logoUrl != null && !logoUrl.isEmpty()) {
                Picasso.get().load(logoUrl).into(paymentProductNameLogoImageView);
            } else {
                paymentProductNameLogoImageView.setImageDrawable(null);
            }
        } else {
            paymentProductNameTextView.setText(parent.getContext()
                .getString(R.string.errors_generalError));
        }

        parent.addView(paymentProductLayout);
    }
}
