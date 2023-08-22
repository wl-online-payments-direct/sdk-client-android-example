package com.onlinepayments.client.android.exampleapp.render.field;

import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;
import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentProductField;


/** 
 * This class implements the RenderLabelInterface and 
 * handles the rendering of the label for one paymentproductfield
 * 
 * Copyright 2020 Global Collect Services B.V
 *
 */
public class RenderLabel implements RenderLabelInterface {

	@Override
	public TextView renderLabel(PaymentProductField field, BasicPaymentItem selectedPaymentProduct, ViewGroup rowView) {

		TextView label = new TextView(rowView.getContext());

		// Get the labeltext
		String labelText = field.getDisplayHints().getLabel();

		// Create new label
		label.setTextAppearance(rowView.getContext(), R.style.ListViewTextView);
		label.setText(Html.fromHtml(labelText));
		rowView.addView(label);

		return label;
	}
	
}
