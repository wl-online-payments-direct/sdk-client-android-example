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

package com.onlinepayments.client.android.exampleapp.util;

import android.content.Context;

import com.onlinepayments.sdk.client.android.domain.configuration.SdkConfiguration;
import com.onlinepayments.sdk.client.android.domain.configuration.SessionData;
import com.onlinepayments.sdk.client.android.facade.OnlinePaymentsSdk;

public class SdkManager {
    private static SdkManager instance;
    private final OnlinePaymentsSdk sdk;

    private SdkManager(
        String clientSessionId,
        String customerId,
        String clientApiUrl,
        String assetBaseUrl,
        boolean environmentIsProduction,
        String appIdentifier,
        boolean loggingEnabled,
        Context context
    ) {
        Context appContext = context.getApplicationContext();
        SessionData sessionData =
            new SessionData(clientSessionId, customerId, clientApiUrl, assetBaseUrl);
        SdkConfiguration sdkConfiguration =
            new SdkConfiguration(environmentIsProduction, appIdentifier, null, loggingEnabled);

        sdk = new OnlinePaymentsSdk(sessionData, appContext, sdkConfiguration);
    }

    public static OnlinePaymentsSdk initSdk(
        String clientSessionId,
        String customerId,
        String clientApiUrl,
        String assetBaseUrl,
        boolean environmentIsProduction,
        String appIdentifier,
        boolean loggingEnabled,
        Context context
    ) {
        instance = new SdkManager(
            clientSessionId,
            customerId,
            clientApiUrl,
            assetBaseUrl,
            environmentIsProduction,
            appIdentifier,
            loggingEnabled,
            context
        );

        return instance.sdk;
    }

    public static OnlinePaymentsSdk getSdk() {
        if (instance == null) {
            throw new IllegalStateException("SdkManager is not initialized");
        }

        return instance.sdk;
    }
}