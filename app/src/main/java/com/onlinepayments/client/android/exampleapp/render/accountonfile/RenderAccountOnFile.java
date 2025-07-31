package com.onlinepayments.client.android.exampleapp.render.accountonfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.sdk.client.android.model.paymentproduct.AccountOnFile;
import com.onlinepayments.sdk.client.android.model.paymentproduct.AccountOnFileAttribute;
import com.onlinepayments.sdk.client.android.model.paymentproduct.AccountOnFileDisplay;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;
import com.onlinepayments.sdk.client.android.model.paymentproduct.displayhints.DisplayHintsPaymentItem;
import com.squareup.picasso.Picasso;

import java.security.InvalidParameterException;

/**
 * Renders the accounts on file on screen
 * Copyright 2020 Global Collect Services B.V
 */
public class RenderAccountOnFile implements RenderAccountOnFileInterface {

    @Override
    public void renderAccountOnFile(
        AccountOnFile accountOnFile, BasicPaymentItem paymentItem, ViewGroup parent
    ) {

        if (accountOnFile == null) {
            throw new InvalidParameterException(
                "Error renderingAccountOnFile, accountOnFile may not be null");
        }
        if (paymentItem == null) {
            throw new InvalidParameterException(
                "Error renderingAccountOnFile, paymentItem may not be null");
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

        // Set the correct value
        String formattedValue = null;
        for (AccountOnFileAttribute attribute : accountOnFile.getAttributes()) {

            assert accountOnFile.getDisplayHints() != null;
            for (AccountOnFileDisplay displayEntry : accountOnFile.getDisplayHints()
                .getLabelTemplate()) {

                if (attribute.getKey().equals(displayEntry.getKey())) {
                    formattedValue = accountOnFile.getMaskedValue(attribute.getKey());
                }
            }
        }
        accountOnFileTextView.setText(formattedValue);

        // Set the logo
        if (!paymentItem.getDisplayHintsList().isEmpty()) {
            DisplayHintsPaymentItem displayHints = paymentItem.getDisplayHintsList().get(0);
            String logoUrl = displayHints.getLogoUrl();

            if (logoUrl != null && !logoUrl.isEmpty()) {
                Picasso.get().load(logoUrl).into(accountOnFileLogoImageView);
            } else {
                accountOnFileLogoImageView.setImageDrawable(null);
            }
        }

        parent.addView(paymentProductLayout);
    }
}
