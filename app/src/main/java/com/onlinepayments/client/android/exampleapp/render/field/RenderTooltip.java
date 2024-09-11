package com.onlinepayments.client.android.exampleapp.render.field;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.translation.StringProvider;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;
import com.onlinepayments.sdk.client.android.model.paymentproduct.PaymentProductField;

/**
 * This class implements the RenderTooltipInterface and
 * handles the rendering of the tooltip for one paymentproductfield
 * Copyright 2020 Global Collect Services B.V
 *
 */
public class RenderTooltip implements RenderTooltipInterface {

    public void renderTooltip(final PaymentProductField field, BasicPaymentItem selectedPaymentProduct, final ViewGroup rowView) {
		// Get the tooltip text & imageURL
		final String tooltipText = field.getDisplayHints().getTooltip().getLabel();

		// Set the tooltip text
		renderTooltip(field.getId(), tooltipText, rowView);
	}

	public void renderRememberMeTooltip(Context context, final ViewGroup rowView) {

		// Check if the translated tooltip text is in the translationsfile.
		// If not, don't show the tooltip
		final String tooltipText = context.getString(R.string.paymentProductDetails_rememberMe_tooltip);

		renderTooltip("rememberMe", tooltipText, rowView);
	}

	private void renderTooltip(final String fieldId, final String tooltipText, final ViewGroup rowView) {

		if (tooltipText != null && !tooltipText.isEmpty() && !StringProvider.isBadTranslationKey(tooltipText)) {
			// Add the questionmark tooltip image after the inputfield
			ImageView tooltipImage = new ImageView(rowView.getContext());
			tooltipImage.setBackgroundResource(R.drawable.ic_action_about);
			tooltipImage.setClickable(true);

			// Set layoutparams and add it to parentView
			LayoutParams tooltipLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tooltipLayoutParams.gravity = Gravity.CENTER_VERTICAL;

			// This is a renderfix for rendering after a checkbox.
			for (int childCount = 0; childCount < rowView.getChildCount(); childCount++) {
				View child = rowView.getChildAt(childCount);
				if (child instanceof CheckBox) {
                    // Tooltip layout dimensions
                    tooltipLayoutParams.topMargin = 10;
				}
			}

			// Add a tag, so we can find the view
			tooltipImage.setTag(fieldId);

			rowView.addView(tooltipImage, tooltipLayoutParams);

			// Add onClickListener for the tooltip image
			tooltipImage.setOnClickListener(view -> {

                // Check if the clicked tooltip is already showing it's text
                // If so remove it, or else show it
                View parentViewGroup = (ViewGroup)rowView.getParent();
                View tooltipTextView = parentViewGroup.findViewWithTag(TOOLTIP_TAG + fieldId);
                if (tooltipTextView == null) {
                    addTooltipTextView(tooltipText, fieldId, rowView);
                } else {
                    removeTooltipTextView(tooltipTextView);
                }
            });
		}
	}

	/**
	 * Removes the View tooltipTextView
	 * @param tooltipTextView, the view to be removed
	 */
	private void removeTooltipTextView(View tooltipTextView) {

		ViewGroup parentView = ((ViewGroup)tooltipTextView.getParent());
		parentView.removeView(tooltipTextView);
	}


	/**
	 * Creates a tooltip textview and adds it under the rowView.
	 *
	 * @param tooltipText, the text that is shown on the screen
	 * @param fieldId, the id of the belonging paymentproductfield, this is used for setting a unique tag on the textview
	 * @param rowView, the view under who the textview is added
	 */
	private void addTooltipTextView(String tooltipText, String fieldId, ViewGroup rowView) {

		// Create a new LinearLayout and add it under the rowView.
		LinearLayout tooltipLayout = new LinearLayout(rowView.getContext());
		tooltipLayout.setOrientation(LinearLayout.VERTICAL);
		tooltipLayout.setBackgroundResource(R.drawable.special_section_background);
		tooltipLayout.setTag(TOOLTIP_TAG + fieldId);
		LayoutParams tooltipLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // Tooltip text layout dimensions
        int TOOLTIP_TEXT_MARGIN = 9;
        tooltipLayoutParams.setMargins(TOOLTIP_TEXT_MARGIN, TOOLTIP_TEXT_MARGIN, TOOLTIP_TEXT_MARGIN, TOOLTIP_TEXT_MARGIN);

		// Create a new TextView and add it to the tooltipLayout
		TextView tooltipTextView = new TextView(rowView.getContext());
		tooltipTextView.setText(tooltipText);
		tooltipLayout.addView(tooltipTextView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		ViewGroup parentViewGroup = (ViewGroup)rowView.getParent();
		parentViewGroup.addView(tooltipLayout, parentViewGroup.indexOfChild(rowView) +1, tooltipLayoutParams);
	}
}
