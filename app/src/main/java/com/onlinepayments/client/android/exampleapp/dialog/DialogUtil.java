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

package com.onlinepayments.client.android.exampleapp.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

public class DialogUtil {

    private DialogUtil() {
    }

    /**
     * Shows an AlertDialog with the given parameters
     *
     * @param context,           Context where the AlertDialog is added to
     * @param titleId,           the String resource identifier for the title of the AlertDialog
     * @param fallbackMessageId, the String resource identifier for the message of the AlertDialog
     * @param buttonTextId,      the String resource identifier for the button text of the AlertDialog
     * @param messageString,     the String message from SDK
     * @param listener,          the optional listener for the dialog
     */
    public static AlertDialog showAlertDialog(
        Context context,
        @StringRes Integer titleId,
        @StringRes Integer fallbackMessageId,
        @StringRes Integer buttonTextId,
        @Nullable String messageString,
        @Nullable OnClickListener listener
    ) {

        String title = context.getString(titleId);
        String message =
            messageString != null ? messageString : context.getString(fallbackMessageId);
        String buttonText = context.getString(buttonTextId);

        // Create a new AlertDialog and set all texts
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        // If there is no listener, add empty one
        if (listener == null) {
            alertDialog.setButton(
                DialogInterface.BUTTON_NEGATIVE, buttonText, (dialog, which) -> {
                }
            );
        } else {
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, buttonText, listener);
        }

        alertDialog.show();

        return alertDialog;
    }

    /**
     * Shows a ProgressDialog with the given parameters
     *
     * @param context, Context where the ProgressDialog is added to
     * @param title,   the title of the ProgressDialog
     * @param message, the message of the ProgressDialog
     * @return ProgressDialog which you can use to hide it when necessary
     */
    public static ProgressDialog showProgressDialog(Context context, String title, String message) {

        // Create a new ProgressDialog and set all texts
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        return progressDialog;
    }

    /**
     * Shows a ParseJsonDialog with the given parameters
     *
     * @param context,  Context where the ProgressDialog is added to
     * @param listener, the listener that will receive the parsed results
     */
    public static void showJsonAlertDialog(
        Context context,
        ParseJsonDialog.JsonParsedListener listener
    ) {
        ParseJsonDialog dialog = new ParseJsonDialog(listener, context);
        dialog.setTitle("Paste JSON");
        dialog.show();
    }
}
