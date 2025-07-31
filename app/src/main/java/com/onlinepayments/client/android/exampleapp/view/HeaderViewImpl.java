package com.onlinepayments.client.android.exampleapp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.configuration.Constants;
import com.onlinepayments.client.android.exampleapp.model.ShoppingCart;
import com.onlinepayments.client.android.exampleapp.model.ShoppingCartItem;
import com.onlinepayments.client.android.exampleapp.util.CurrencyUtil;
import com.onlinepayments.sdk.client.android.model.PaymentContext;

import java.util.Locale;
import java.util.Objects;

/**
 * View for the Header that shows the merchant logo and shoppingCart
 * <p>
 * Copyright 2020 Global Collect Services B.V
 * </p>
 */
public class HeaderViewImpl implements HeaderView {

    private final View headerView;
    private boolean rendered = false;

    public boolean isRendered() {
        return rendered;
    }

    public HeaderViewImpl(Activity activity, @IdRes int id) {
        headerView = activity.findViewById(id);
        Intent intent = activity.getIntent();

        ShoppingCart shoppingCart =
            (ShoppingCart) intent.getSerializableExtra(Constants.INTENT_SHOPPINGCART);
        PaymentContext paymentContext =
            (PaymentContext) intent.getSerializableExtra(Constants.INTENT_PAYMENT_CONTEXT);

        if (headerView == null) {
            throw new RuntimeException(
                "Exception initializing HeaderView - Could not find view with provided header ID: " + id);
        }

        if (shoppingCart == null) {
            throw new RuntimeException(
                "Exception initializing HeaderView - ShoppingCart was not present on activity intent");
        }

        if (paymentContext == null) {
            throw new RuntimeException(
                "Exception initializing HeaderView - PaymentContext was not present on activity intent");
        }

        if (!rendered) {
            renderShoppingCart(shoppingCart, paymentContext);
            rendered = true;
        }
    }

    private void renderShoppingCart(ShoppingCart shoppingCart, PaymentContext paymentContext) {

        // Set the totalCost text on the header
        TextView totalCost = headerView.findViewById(R.id.totalCost);
        TextView totalCostDetail = headerView.findViewById(R.id.totalCostDetail);

        String formattedTotalAmount = CurrencyUtil.formatAmount(shoppingCart.getTotalAmount(),
            paymentContext.getCountryCode(),
            Objects.requireNonNull(paymentContext.getAmountOfMoney()).getCurrencyCode()
        );
        totalCost.setText(formattedTotalAmount);
        totalCostDetail.setText(formattedTotalAmount);

        renderOrderDetails(shoppingCart, paymentContext);
    }

    private void renderOrderDetails(ShoppingCart cart, PaymentContext paymentContext) {

        Context context = headerView.getContext();

        // Get the shoppingCartView
        LinearLayout totalCostDetailsLayout = headerView.findViewById(R.id.totalCostDetailsLayout);

        // Add all shoppingCartItems to the totalCostDetailsLayout

        // Set layout params
        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
        LinearLayout.LayoutParams descriptionParams =
            new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 4f);
        LinearLayout.LayoutParams quantityParams =
            new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        LinearLayout.LayoutParams costParams =
            new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3f);

        for (ShoppingCartItem item : cart.getShoppingCartItems()) {

            //Add the relative layout which contains the texts
            LinearLayout layout = new LinearLayout(context);
            layout.setLayoutParams(params);

            //Show the description
            TextView label = new TextView(context);
            label.setText(item.getDescription());
            label.setTextAppearance(context, R.style.TotalCostLayoutSmallText);
            label.setGravity(Gravity.START);
            layout.addView(label, descriptionParams);

            //Show the quantity
            TextView quantity = new TextView(context);
            quantity.setText(String.format(Locale.ROOT, "%d", item.getQuantity()));
            quantity.setTextAppearance(context, R.style.TotalCostLayoutSmallText);
            quantity.setGravity(Gravity.START);
            layout.addView(quantity, quantityParams);

            //Show the amount formatted
            TextView cost = new TextView(context);
            cost.setText(CurrencyUtil.formatAmount(item.getAmountInCents(),
                paymentContext.getCountryCode(),
                Objects.requireNonNull(paymentContext.getAmountOfMoney()).getCurrencyCode()
            ));
            cost.setTextAppearance(context, R.style.TotalCostLayoutSmallText);
            cost.setGravity(Gravity.END);
            layout.addView(cost, costParams);

            totalCostDetailsLayout.addView(layout, 1, params);
        }
    }

    @Override
    public void showDetailView() {
        headerView.findViewById(R.id.totalCostLayout).setVisibility(View.GONE);
        headerView.findViewById(R.id.totalCostDetailsLayout).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDetailView() {
        headerView.findViewById(R.id.totalCostLayout).setVisibility(View.VISIBLE);
        headerView.findViewById(R.id.totalCostDetailsLayout).setVisibility(View.GONE);
    }

    @Override
    public View getRootView() {
        return headerView;
    }
}
