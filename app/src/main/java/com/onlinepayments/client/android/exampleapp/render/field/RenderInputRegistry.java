package com.onlinepayments.client.android.exampleapp.render.field;

import com.onlinepayments.sdk.client.android.model.paymentproduct.FormElement.Type;

import java.util.HashMap;

/**
 * Copyright 2020 Global Collect Services B.V
 */
public class RenderInputRegistry {

    // Map with custom renderers
    private final HashMap<Type, RenderInputFieldInterface> customRenderers;

    // Map with default renderers
    private final HashMap<Type, RenderInputFieldInterface> defaultRenderers;

    /**
     * Constructor
     *
     * @param customRenderers, this is the map with custom renderers
     */
    public RenderInputRegistry(HashMap<Type, RenderInputFieldInterface> customRenderers) {
        this.customRenderers = customRenderers;

        // Fill the default renderers map
        defaultRenderers = new HashMap<>();
        defaultRenderers.put(Type.TEXT, new RenderTextField());
        defaultRenderers.put(Type.LIST, new RenderList());
        defaultRenderers.put(Type.CURRENCY, new RenderCurrency());
        defaultRenderers.put(Type.DATE, new RenderDate());
        defaultRenderers.put(Type.BOOLEAN, new RenderBoolean());
    }

    /**
     * Gets the correct instance of RenderInputField implementation for the given fieldType
     *
     * @param fieldType, this determines what kind of RenderInputField implementation will be returned
     * @return RenderInputField implementation
     */
    public RenderInputFieldInterface getRenderInputFieldForFieldType(Type fieldType) {

        // Check the custom renderer map for entries for this fieldType
        if (customRenderers.containsKey(fieldType)) {
            return customRenderers.get(fieldType);
        }

        // Else render the default way
        if (defaultRenderers.containsKey(fieldType)) {
            return defaultRenderers.get(fieldType);
        }

        // Or return null when there is no renderer for this fieldType
        return null;
    }
}
