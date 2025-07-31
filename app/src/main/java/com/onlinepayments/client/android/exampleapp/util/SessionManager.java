package com.onlinepayments.client.android.exampleapp.util;

import android.content.Context;

import com.onlinepayments.sdk.client.android.session.Session;

public class SessionManager {
    private static SessionManager instance;
    private final Session session;

    private SessionManager(
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
        session = new Session(
            clientSessionId,
            customerId,
            clientApiUrl,
            assetBaseUrl,
            environmentIsProduction,
            appIdentifier,
            loggingEnabled,
            appContext
        );
    }

    public static Session initSession(
        String clientSessionId,
        String customerId,
        String clientApiUrl,
        String assetBaseUrl,
        boolean environmentIsProduction,
        String appIdentifier,
        boolean loggingEnabled,
        Context context
    ) {
        instance = new SessionManager(
            clientSessionId,
            customerId,
            clientApiUrl,
            assetBaseUrl,
            environmentIsProduction,
            appIdentifier,
            loggingEnabled,
            context
        );

        return instance.session;
    }

    public static Session getSession() {
        if (instance == null) {
            throw new IllegalStateException("SessionManager is not initialized");
        }

        return instance.session;
    }
}