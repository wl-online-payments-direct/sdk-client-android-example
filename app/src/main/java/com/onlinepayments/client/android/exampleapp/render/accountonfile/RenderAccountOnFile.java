/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.render.accountonfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.sdk.client.android.domain.accountOnFile.AccountOnFile;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.BasicPaymentProduct;
import com.squareup.picasso.Picasso;

import java.security.InvalidParameterException;

/**
 * Renders the accounts on file on screen
 */
public class RenderAccountOnFile implements RenderAccountOnFileInterface {

    @Override
    public void renderAccountOnFile(
        AccountOnFile accountOnFile,
        BasicPaymentProduct basicPaymentProduct,
        ViewGroup parent
    ) {

        if (accountOnFile == null) {
            throw new InvalidParameterException(
                "Error renderingAccountOnFile, accountOnFile may not be null");
        }

        if (basicPaymentProduct == null) {
            throw new InvalidParameterException(
                "Error renderingAccountOnFile, paymentProduct may not be null");
        }

        if (parent == null) {
            throw new InvalidParameterException(
                "Error renderingAccountOnFile, parent may not be null");
        }

        // Inflate the activity_select_payment_product_render layout
        LayoutInflater inflater =
            (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View paymentProductLayout =
            inflater.inflate(R.layout.activity_render_payment_product, parent, false);

        // Set the belonging accountOnFile on the tag of the row so we can retrieve it when clicked
        paymentProductLayout.findViewById(R.id.paymentProductRow).setTag(accountOnFile);

        // Get the TextView and ImageView which will be filled
        TextView accountOnFileTextView = paymentProductLayout.findViewById(R.id.paymentProductName);
        ImageView accountOnFileLogoImageView =
            paymentProductLayout.findViewById(R.id.paymentProductLogo);

        String formattedValue = accountOnFile.getLabel();

        accountOnFileTextView.setText(formattedValue);

        // Set the logo
        String logoUrl = basicPaymentProduct.getLogo();

        if (logoUrl != null && !logoUrl.isEmpty()) {
            Picasso.get().load(logoUrl).into(accountOnFileLogoImageView);
        } else {
            accountOnFileLogoImageView.setImageDrawable(null);
        }

        parent.addView(paymentProductLayout);
    }
}
