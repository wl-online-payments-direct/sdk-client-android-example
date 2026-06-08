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

package com.onlinepayments.client.android.exampleapp.render.persister;

import com.onlinepayments.sdk.client.android.domain.iin.IinDetailsResponse;

import java.io.Serial;
import java.io.Serializable;

/**
 * Will persist IinDetails information
 */
public class IinDetailsPersister implements Serializable {

    @Serial
    private static final long serialVersionUID = -5586773582374607503L;

    private IinDetailsResponse iinDetailsResponse;

    public void setIinDetailsResponse(IinDetailsResponse iinDetailsResponse) {
        this.iinDetailsResponse = iinDetailsResponse;
    }

    public IinDetailsResponse getIinDetailsResponse() {
        return iinDetailsResponse;
    }
}
