/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.configuration;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.security.InvalidParameterException;

public class CheckCommunication {

    /**
     * Checks if there is internet connectivity on this device
     *
     * @param activity Activity
     * @return true if there is internet connectivity
     */
    public static boolean isOnline(Activity activity) {

        // Null check
        if (activity == null) {
            throw new InvalidParameterException(
                "Error checking connection capabilities, activity may not be null");
        }

        // Check whether internet connection
        ConnectivityManager cm =
            (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
