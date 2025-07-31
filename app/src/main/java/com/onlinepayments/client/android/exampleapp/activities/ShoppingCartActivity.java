package com.onlinepayments.client.android.exampleapp.activities;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.view.HeaderViewImpl;

/**
 * Toggles the shoppingCart details when its clicked
 * Copyright 2020 Global Collect Services B.V
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
