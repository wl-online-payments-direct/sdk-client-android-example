package com.onlinepayments.client.android.exampleapp.render.validation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.onlinepayments.client.android.exampleapp.R;

import java.security.InvalidParameterException;


/** 
 * This class implements the RenderValidationMessage and 
 * handles the rendering of the validation message for one paymentproductfield
 * Copyright 2020 Global Collect Services B.V
 *
 */
public class RenderValidationMessage implements RenderValidationMessageInterface {

	// Prefix for validationmessage views
	public static final String VALIDATION_MESSAGE_TAG_PREFIX = "validation_message_";

    @Override
	public void renderValidationMessage(String validationMessage, ViewGroup rowView, String fieldId) {
		
		if (validationMessage == null ) {
			throw new InvalidParameterException("Error rendering ValidationMessage, validationMessage may not be null");
		}
		if (rowView == null ) {
			throw new InvalidParameterException("Error rendering ValidationMessage, rowView may not be null");
		}
		if (fieldId == null ) {
			throw new InvalidParameterException("Error rendering ValidationMessage, fieldId may not be null");
		}

		// Create a new TextView and add it to the rowView
		TextView validationMessageView = new TextView(rowView.getContext());
		validationMessageView.setText(validationMessage);
		validationMessageView.setTag(VALIDATION_MESSAGE_TAG_PREFIX + fieldId);
		validationMessageView.setTextAppearance(rowView.getContext(), R.style.ErrorMessage);
		
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // Errormessage layout dimensions
        int TOOLTIP_TEXT_MARGIN = 9;
        layoutParams.setMargins(TOOLTIP_TEXT_MARGIN, TOOLTIP_TEXT_MARGIN, TOOLTIP_TEXT_MARGIN, TOOLTIP_TEXT_MARGIN);
		
		ViewGroup parentViewGroup = (ViewGroup)rowView.getParent();
		parentViewGroup.addView(validationMessageView, parentViewGroup.indexOfChild(rowView) +1, layoutParams);
	}
	
	@Override
	public void removeValidationMessage(ViewGroup rowView, String fieldId) {
		if (fieldId == null ) {
			throw new InvalidParameterException("Error removing ValidationMessage, fieldId may not be null");
		}

		// If the field does not exist we can not remove the error message, however we do also not
		// want to fail
		if (rowView == null ) {
			return;
		}

		ViewGroup parentViewGroup = (ViewGroup)rowView.getParent();
		if (parentViewGroup != null) {
			// Get the to be removed view and remove it
			View removeView = parentViewGroup.findViewWithTag(VALIDATION_MESSAGE_TAG_PREFIX + fieldId);
			if (removeView != null) {
				ViewGroup removeViewParent = (ViewGroup)removeView.getParent();
				removeViewParent.removeView(removeView);
			}
		}
	}
}
