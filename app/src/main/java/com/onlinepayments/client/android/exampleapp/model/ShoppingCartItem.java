package com.onlinepayments.client.android.exampleapp.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Pojo which contains a shoppingCartItem
 * <p>
 * Copyright 2020 Global Collect Services B.V
 * </p>
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
