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

package com.onlinepayments.client.android.exampleapp.render.field;

import android.view.View;
import android.view.ViewGroup;

import com.onlinepayments.client.android.exampleapp.render.persister.InputDataPersister;
import com.onlinepayments.sdk.client.android.domain.PaymentContextWithAmount;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.productField.PaymentProductField;
import com.onlinepayments.sdk.client.android.infrastructure.apiModels.paymentProduct.displayHints.FormElementDto;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Delegate which handles the dynamic rendering of fields
 */
public class RenderInputDelegate {

    // Map containing all custom renderers per FormElement type
    private final HashMap<FormElementDto.Type, RenderInputFieldInterface> customRenderers;

    private final RenderInputFieldHelper renderField;

    /**
     * Constructor
     *
     * @param fieldsView, the ViewGroup to which all the rendered fields are added
     */
    public RenderInputDelegate(ViewGroup fieldsView) {
        customRenderers = new HashMap<>();
        renderField = new RenderInputFieldHelper(fieldsView);
    }

    /**
     * Registers a custom renderer for the given FormElement type
     * This overrides the default rendering of the FormElement type
     *
     * @param type,     the FormElement for which the custom renderer is registered.
     * @param renderer, the renderer that is called when the fields are rendered.
     * @noinspection unused
     */
    public void registerCustomFieldRenderer(
        FormElementDto.Type type,
        RenderInputFieldInterface renderer
    ) {

        if (type == null) {
            throw new InvalidParameterException(
                "Error registering CustomRenderer, type may not be null");
        }
        if (renderer == null) {
            throw new InvalidParameterException(
                "Error registering CustomRenderer, renderer may not be null");
        }
        customRenderers.put(type, renderer);
    }

    /**
     * Registers a custom tooltip renderer which is used for showing tooltips
     *
     * @param renderer, the renderer that is called when the tooltips are rendered.
     * @noinspection unused
     */
    public void registerCustomTooltipRenderer(RenderTooltipInterface renderer) {
        renderField.registerCustomTooltipRenderer(renderer);
    }

    /**
     * Renders all PaymentProductField fields that are given in the list fields
     * This is a delegate who defers the rendering to the specific RenderInputField implementation
     *
     * @param inputDataPersister, class that holds information about the paymentItem that will be rendered
     */
    public void renderPaymentInputFields(
        InputDataPersister inputDataPersister,
        PaymentContextWithAmount paymentContext
    ) {

        renderPaymentInputFields(
            inputDataPersister,
            inputDataPersister.getPaymentProduct().getFields(),
            paymentContext
        );
    }

    public void renderPaymentInputFields(
        InputDataPersister inputDataPersister,
        List<PaymentProductField> paymentProductFields,
        PaymentContextWithAmount paymentContext
    ) {

        if (inputDataPersister == null) {
            throw new InvalidParameterException(
                "Error rendering PaymentInputFields, inputDataPersister may not be null");
        }

        if (paymentProductFields == null) {
            throw new InvalidParameterException(
                "Error rendering PaymentInputFields, paymentProductFields may not be null");
        }

        // Render all fields
        RenderInputRegistry registry = new RenderInputRegistry(customRenderers);

        for (PaymentProductField field : paymentProductFields) {

            RenderInputFieldInterface renderer =
                registry.getRenderInputFieldForFieldType(Objects.requireNonNull(field.getDisplayHints()
                    .getFormElementType()));
            if (renderer != null) {
                renderField.renderField(renderer, field, inputDataPersister, paymentContext);
            }
        }
    }

    /**
     * Hides all tooltipTexts
     */
    public void hideTooltipTexts(ViewGroup parentView) {
        // Loop trough all the children to find a tooltip view
        for (int childIndex = 0; childIndex < parentView.getChildCount(); childIndex++) {

            View child = parentView.getChildAt(childIndex);
            if (child.getTag() != null && child.getTag() instanceof String tag) {

                if (tag.startsWith(RenderTooltipInterface.TOOLTIP_TAG)) {

                    // Remove the view
                    ViewGroup removeViewParent = ((ViewGroup) child.getParent());
                    removeViewParent.removeView(child);
                }
            }
        }
    }
}
