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

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.onlinepayments.client.android.exampleapp.R;
import com.onlinepayments.client.android.exampleapp.model.SessionDetails;

import org.apache.commons.lang3.StringUtils;

/**
 * Dialog with the purpose of parsing session details from JSON to Pojo
 */
public class ParseJsonDialog extends Dialog {

    private final JsonParsedListener jsonParsedListener;

    public ParseJsonDialog(@NonNull JsonParsedListener listener, @NonNull Context context) {
        super(context);
        jsonParsedListener = listener;
    }

    /**
     * @noinspection unused
     */
    public ParseJsonDialog(
        @NonNull JsonParsedListener listener,
        @NonNull Context context,
        int themeResId
    ) {
        super(context, themeResId);
        jsonParsedListener = listener;
    }

    /**
     * @noinspection unused
     */
    protected ParseJsonDialog(
        @NonNull JsonParsedListener listener,
        @NonNull Context context,
        boolean cancelable,
        @Nullable OnCancelListener cancelListener
    ) {
        super(context, cancelable, cancelListener);
        jsonParsedListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_json);

        Button buttonParseJson = findViewById(R.id.parse_json_btn);
        Button buttonDismiss = findViewById(R.id.dismiss_btn);
        EditText editTextJson = findViewById(R.id.edit_json);

        buttonDismiss.setOnClickListener(v -> dismiss());
        buttonParseJson.setOnClickListener(v -> {
            try {
                String json = editTextJson.getText().toString();
                if (json.isEmpty()) {
                    return;
                }

                SessionDetails sessionDetails = new Gson().fromJson(json, SessionDetails.class);
                jsonParsedListener.onJsonParsed(sessionDetails);

                if (sessionDetails.isFullyParsed()) {
                    dismiss();
                } else {
                    String errorText =
                        "Not all values were parsed. Missing values:\n" + StringUtils.join(
                            sessionDetails.getMissingValues());
                    setErrorTextView(errorText);
                }
            } catch (Exception ex) {
                Log.d("ParseJsonDialog", "Error Parsing: " + ex);

                setErrorTextView("Error parsing JSON:\n" + getCleanMessage(ex));
            }
        });
    }

    private void setErrorTextView(String text) {
        TextView errorTextView = findViewById(R.id.text_parse_json_errors);

        int visibility;
        if (text == null || text.isEmpty()) {
            visibility = View.GONE;
        } else {
            visibility = View.VISIBLE;
        }

        errorTextView.setText(text);
        errorTextView.setVisibility(visibility);
    }

    private String getCleanMessage(Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            return "";
        }
        if (!message.contains(":")) {
            return message;
        }
        String[] splitMessage = message.split(":");

        // Change the first part which describes: com.xx.xx.SomeException to nothing
        splitMessage[0] = "";
        return StringUtils.join(splitMessage, "");
    }

    /**
     * Listener class used to report back the parsed SessionDetails object
     */
    public interface JsonParsedListener {
        void onJsonParsed(SessionDetails sessionDetails);
    }
}
