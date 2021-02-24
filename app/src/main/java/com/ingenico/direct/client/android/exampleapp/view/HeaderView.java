package com.ingenico.direct.client.android.exampleapp.view;

import com.ingenico.direct.client.android.exampleapp.model.ShoppingCart;
import com.ingenico.direct.sdk.client.android.model.PaymentContext;

/**
 * ViewInterface for the Header ShoppinCartView
 *
 * Copyright 2020 Global Collect Services B.V
 */
public interface HeaderView extends GeneralView {

    void renderShoppingCart(ShoppingCart shoppingCart, PaymentContext paymentContext);

    void showDetailView();

    void hideDetailView();
}
