package com.onlinepayments.client.android.exampleapp.render.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentProduct;
import com.onlinepayments.sdk.client.android.model.paymentproduct.displayhints.DisplayHintsPaymentItem;
import com.squareup.picasso.Picasso;

import java.security.InvalidParameterException;

/**
 * Renders payment products
 * Copyright 2020 Global Collect Services B.V
 */
public class RenderPaymentItem implements RenderPaymentItemInterface {

    @Override
    public void renderPaymentItem(BasicPaymentItem product, ViewGroup parent) {

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
        if (product instanceof BasicPaymentProduct) {
            if (!product.getDisplayHintsList().isEmpty()) {
                DisplayHintsPaymentItem displayHints = product.getDisplayHintsList().get(0);

                paymentProductNameTextView.setText(displayHints.getLabel());
                String logoUrl = displayHints.getLogoUrl();
                if (logoUrl != null && !logoUrl.isEmpty()) {
                    Picasso.get().load(logoUrl).into(paymentProductNameLogoImageView);
                } else {
                    paymentProductNameLogoImageView.setImageDrawable(null);
                }
            } else {
                paymentProductNameTextView.setText(parent.getContext()
                    .getString(R.string.errors_generalError));
            }
        } else {
            paymentProductNameTextView.setText(product.getId());
        }

        parent.addView(paymentProductLayout);
    }
}
