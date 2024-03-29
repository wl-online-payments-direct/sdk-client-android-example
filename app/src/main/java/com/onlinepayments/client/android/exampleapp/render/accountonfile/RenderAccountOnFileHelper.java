package com.onlinepayments.client.android.exampleapp.render.accountonfile;

import java.security.InvalidParameterException;

import android.view.ViewGroup;

import com.onlinepayments.sdk.client.android.model.paymentproduct.AccountOnFile;
import com.onlinepayments.sdk.client.android.model.paymentproduct.BasicPaymentItem;


/**
 * Helper class for rendering the accounts on file on screen
 * 
 * Copyright 2020 Global Collect Services B.V
 *
 */
public class RenderAccountOnFileHelper {
	
	
	// Default renderer for accountsonfile
	private RenderAccountOnFileInterface renderer = new RenderAccountOnFile();
	
	
	/**
	 * Registers a custom accountonfile renderer
	 * This renderer must implement the RenderAccountOnFileInterface interface
	 * 
	 * @param renderer, the custom renderer which will handle the show of accountonfiles
	 */
	public void registerCustomRenderer(RenderAccountOnFileInterface renderer) {
		
		if (renderer == null) {
			throw new InvalidParameterException("Error setting custom renderer, renderer may not be null");
		}
		this.renderer = renderer;
	}
	
	
	/**
	 * Renders the account on file with the default renderer, or with the custom renderer when that is set.
	 */
	public void renderAccountOnFile(AccountOnFile accountOnFile, BasicPaymentItem paymentItem, ViewGroup parent) {
		renderer.renderAccountOnFile(accountOnFile, paymentItem, parent);
	}
	
}
