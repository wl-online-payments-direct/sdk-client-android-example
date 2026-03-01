/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.render.persister;

import com.onlinepayments.client.android.exampleapp.util.AttributeUtil;
import com.onlinepayments.sdk.client.android.domain.accountOnFile.AccountOnFile;
import com.onlinepayments.sdk.client.android.domain.accountOnFile.AccountOnFileAttribute;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.PaymentProduct;
import com.onlinepayments.sdk.client.android.domain.paymentProduct.productField.PaymentProductField;
import com.onlinepayments.sdk.client.android.domain.paymentRequest.PaymentRequest;
import com.onlinepayments.sdk.client.android.domain.validation.ValidationErrorMessage;

import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Helper class that takes care of validating the input and making sure that the data ends up
 * in the PaymentRequest
 * Also helps to persist information about what fields are currently showing error messages
 */
public class InputValidationPersister implements Serializable {

    @Serial
    private static final long serialVersionUID = 4862313783204104738L;

    private final PaymentRequest paymentRequest;

    private List<ValidationErrorMessage> errorMessages = new ArrayList<>();

    public InputValidationPersister(PaymentProduct paymentProduct, AccountOnFile accountOnFile) {
        paymentRequest = new PaymentRequest(paymentProduct, accountOnFile, false);
    }

    public List<ValidationErrorMessage> storeAndValidateInput(InputDataPersister inputDataPersister) {

        // Get current field information and store it in the Request
        storeInputFieldDataInPaymentRequest(inputDataPersister);

        // Validate the input in the Request and store possible validation messages
        errorMessages = paymentRequest.validate().getErrors();
        return errorMessages;
    }

    private void storeInputFieldDataInPaymentRequest(InputDataPersister inputDataPersister) {
        PaymentProduct paymentProduct = inputDataPersister.getPaymentProduct();
        AccountOnFile accountOnFile = inputDataPersister.getAccountOnFile();
        for (PaymentProductField field : paymentProduct.getFields()) {
            String value = inputDataPersister.getValue(field.getId());
            // Don't add the value if it has not changed and is available in the provided Account on File
            if (accountOnFile != null && isFieldInAccountOnFile(
                field.getId(),
                accountOnFile
            ) && valueIsNotAltered(value, field, accountOnFile)) {
                // The value was not altered with regards to the accountOnFile, but there may still
                // be an altered value in the PaymentRequest, which is not correct
                paymentRequest.getField(field.getId()).clearValue();
                continue;
            }

            // Don't add empty field values
            if (StringUtils.isEmpty(value)) {
                // Also remove any data that may possibly have already been added to the Payment Request
                paymentRequest.getField(field.getId()).clearValue();
                continue;
            }

            paymentRequest.setValue(field.getId(), Objects.requireNonNull(field.removeMask(value)));
        }

        paymentRequest.setTokenize(inputDataPersister.isRememberMe());
    }

    private boolean isFieldInAccountOnFile(String fieldId, AccountOnFile accountOnFile) {
        for (AccountOnFileAttribute attribute : AttributeUtil.getAttributes(accountOnFile)) {
            if (attribute.getKey().equals(fieldId)) {
                return true;
            }
        }

        return false;
    }

    private boolean valueIsNotAltered(
        String value,
        PaymentProductField field,
        AccountOnFile accountOnFile
    ) {
        // Assume the value is not altered if it is null in the inputDataPersister
        if (value == null) {
            return true;
        }

        for (AccountOnFileAttribute attribute : AttributeUtil.getAttributes(accountOnFile)) {
            if (attribute.getKey().equals(field.getId())) {
                return Objects.equals(field.removeMask(value), attribute.getValue());
            }
        }

        throw new IllegalStateException("No value found in Account on File for the provided FieldId");
    }

    public PaymentRequest getPaymentRequest() {
        return paymentRequest;
    }

    public List<ValidationErrorMessage> getErrorMessages() {
        return errorMessages;
    }
}
