package com.onlinepayments.client.android.exampleapp.render.field;
import android.view.ViewGroup;

import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;


/**
 * Defines the rendering of tooltip interface methods
 * 
 * Copyright 2020 Global Collect Services B.V
 *
 */
public interface RenderTooltipInterface {
	
	
	// Tag for identifying tooltips elements
    String TOOLTIP_TAG = "tooltip_";
	
	/**
	 * Renders a tooltip by the data in the PaymentProductField.
	 * This tooltip is added to the given ViewGroup
	 * @param fieldId, id of the PaymentProductField for the tooltip
	 * @param selectedPaymentProduct, the selected PaymentProduct, used for getting the correct translations
	 * @param rowView, the ViewGroup to which the rendered tooltip is added
	 */
    void renderTooltip(String fieldId, BasicPaymentItem selectedPaymentProduct, ViewGroup rowView);
	
}
