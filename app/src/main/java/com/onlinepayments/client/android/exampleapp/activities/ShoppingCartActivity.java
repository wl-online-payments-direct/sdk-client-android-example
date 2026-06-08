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

package com.onlinepayments.client.android.exampleapp.activities;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.view.HeaderViewImpl;

/**
 * Toggles the shoppingCart details when its clicked
 */
public class ShoppingCartActivity extends FragmentActivity {

    private HeaderViewImpl header;

    @Override
    public void onStart() {
        super.onStart();
        if (header == null) {
            // Prevent re-init after coming back from the background
            header = new HeaderViewImpl(this, R.id.header_layout);
        }
    }

    public void showShoppingCartDetailView(View v) {
        header.showDetailView();
    }

    public void hideShoppingCartDetailView(View v) {
        header.hideDetailView();
    }
}
