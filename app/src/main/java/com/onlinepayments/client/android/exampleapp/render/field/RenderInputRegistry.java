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

import com.onlinepayments.sdk.client.android.infrastructure.apiModels.paymentProduct.displayHints.FormElementDto;

import java.util.HashMap;

public class RenderInputRegistry {

    // Map with custom renderers
    private final HashMap<FormElementDto.Type, RenderInputFieldInterface> customRenderers;

    // Map with default renderers
    private final HashMap<FormElementDto.Type, RenderInputFieldInterface> defaultRenderers;

    /**
     * Constructor
     *
     * @param customRenderers, this is the map with custom renderers
     */
    public RenderInputRegistry(HashMap<FormElementDto.Type, RenderInputFieldInterface> customRenderers) {
        this.customRenderers = customRenderers;

        // Fill the default renderers map
        defaultRenderers = new HashMap<>();
        defaultRenderers.put(FormElementDto.Type.TEXT, new RenderTextField());
        defaultRenderers.put(FormElementDto.Type.LIST, new RenderList());
        defaultRenderers.put(FormElementDto.Type.CURRENCY, new RenderCurrency());
        defaultRenderers.put(FormElementDto.Type.DATE, new RenderDate());
        defaultRenderers.put(FormElementDto.Type.BOOLEAN, new RenderBoolean());
    }

    /**
     * Gets the correct instance of RenderInputField implementation for the given fieldType
     *
     * @param fieldType, this determines what kind of RenderInputField implementation will be returned
     * @return RenderInputField implementation
     */
    public RenderInputFieldInterface getRenderInputFieldForFieldType(String fieldType) {

        // try casting string to enum
        try {
            FormElementDto.Type type = FormElementDto.Type.valueOf(fieldType);

            if (customRenderers.containsKey(type)) {
                return customRenderers.get(FormElementDto.Type.valueOf(fieldType));
            }

            // Else render the default way
            if (defaultRenderers.containsKey(type)) {
                return defaultRenderers.get(FormElementDto.Type.valueOf(fieldType));
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        // Or return null when there is no renderer for this fieldType
        return null;
    }
}
