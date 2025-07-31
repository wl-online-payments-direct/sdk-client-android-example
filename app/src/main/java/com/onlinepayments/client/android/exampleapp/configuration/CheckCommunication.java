package com.onlinepayments.client.android.exampleapp.configuration;

import java.security.InvalidParameterException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Copyright 2020 Global Collect Services B.V
 */
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
