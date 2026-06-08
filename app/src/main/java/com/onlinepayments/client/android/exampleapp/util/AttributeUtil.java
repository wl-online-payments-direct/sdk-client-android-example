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

import com.onlinepayments.sdk.client.android.domain.accountOnFile.AccountOnFile;
import com.onlinepayments.sdk.client.android.domain.accountOnFile.AccountOnFileAttribute;

import java.util.ArrayList;
import java.util.List;

public class AttributeUtil {
    private AttributeUtil() {
    }

    public static List<AccountOnFileAttribute> getAttributes(AccountOnFile accountOnFile) {
        List<AccountOnFileAttribute> attributes = new ArrayList<>();

        if (accountOnFile == null) {
            return attributes;
        }

        attributes.addAll(accountOnFile.getWritableAttributes());
        attributes.addAll(accountOnFile.getRequiredAttributes());

        return attributes;
    }
}
