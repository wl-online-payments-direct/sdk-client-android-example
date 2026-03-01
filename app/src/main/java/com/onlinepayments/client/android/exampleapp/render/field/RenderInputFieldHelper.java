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

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.domain.PaymentContextWithAmount;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.PaymentProduct;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.productField.PaymentProductField;

import java.security.InvalidParameterException;

/**
 * Helper class that takes care of rendering the general layout of an input field, that all fields
 * share.
 */
public class RenderInputFieldHelper {

    // The parent view to which all fields are added
    private final ViewGroup parentView;

    // Default renderer for tooltips on all fields
    private RenderTooltipInterface tooltipRenderer = new RenderTooltip();

    // Default renderer for tooltips on all fields
    private final RenderLabelInterface labelRenderer = new RenderLabel();

    /**
     * Constructor
     *
     * @param parentView, the ViewGroup to which all fields are added when rendered
     */
    public RenderInputFieldHelper(ViewGroup parentView) {

        if (parentView == null) {
            throw new InvalidParameterException(
                "Error initialising RenderField, parentView may not be null");
        }
        this.parentView = parentView;
    }

    /**
     * Registers a custom tooltip renderer which is used for showing tooltips
     *
     * @param renderer, the renderer that is called when the tooltips are rendered.
     */
    public void registerCustomTooltipRenderer(RenderTooltipInterface renderer) {

        if (renderer == null) {
            throw new InvalidParameterException(
                "Error setting TooltipRenderer, renderer may not be null");
        }
        this.tooltipRenderer = renderer;
    }

    /**
     * Renders a given field with the given renderer
     *
     * @param renderer, the RenderInputFieldInterface which determines how the PaymentProductField is rendered
     * @param field,    the PaymentProductField which is rendered
     */
    public void renderField(
        RenderInputFieldInterface renderer,
        PaymentProductField field,
        InputDataPersister inputDataPersister,
        PaymentContextWithAmount paymentContext
    ) {

        PaymentProduct paymentProduct = inputDataPersister.getPaymentProduct();

        if (renderer != null) {

            // Create new row
            LinearLayout rowView = new LinearLayout(parentView.getContext());

            rowView.setOrientation(LinearLayout.VERTICAL);
            rowView.setPadding(0, 20, 0, 0);

            // Create new row entry
            LinearLayout rowContentView = new LinearLayout(parentView.getContext());
            rowContentView.setOrientation(LinearLayout.HORIZONTAL);

            // Render label, if a checkBox (Boolean field) is being rendered, we want the label to
            // be in the row content view
            if (renderer instanceof RenderBoolean) {
                labelRenderer.renderLabel(field, paymentProduct, rowContentView);
            } else {
                labelRenderer.renderLabel(field, paymentProduct, rowView);
            }

            // Render field in row and set a tag so we can look it up later
            View view =
                renderer.renderField(field, inputDataPersister, rowContentView, paymentContext);
            view.setTag(field.getId());

            // Render tooltip in row
            if (view.isEnabled()) {
                tooltipRenderer.renderTooltip(field, paymentProduct, rowContentView);
            }

            rowView.addView(rowContentView);

            // Add row to parentView
            LayoutParams rowParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            parentView.addView(rowView, rowParams);
        }
    }
}
