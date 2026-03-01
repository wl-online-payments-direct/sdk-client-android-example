/*
 * Do not remove or alter the notices in this preamble.
 *
 * Copyright © 2026 Worldline and/or its affiliates.
 *
 * All rights reserved. License grant and user rights and obligations according to the applicable license agreement.
 *
 * Please contact Worldline for questions regarding license and user rights.
 */

package com.onlinepayments.client.android.exampleapp.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Pojo which contains a shoppingCartItem
 */
public class ShoppingCartItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 8101681369595663857L;

    private String description;
    private Long amountInCents;
    private Integer quantity;

    /**
     * @noinspection unused
     */
    public ShoppingCartItem() {
    }

    public ShoppingCartItem(String description, Long amountInCents, Integer quantity) {
        this.description = description;
        this.amountInCents = amountInCents;
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public Long getAmountInCents() {
        return amountInCents;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
