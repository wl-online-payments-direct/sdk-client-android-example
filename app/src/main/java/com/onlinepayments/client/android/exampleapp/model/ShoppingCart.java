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
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains shoppingCart items
 */
public class ShoppingCart implements Serializable {

    @Serial
    private static final long serialVersionUID = 7301578426204735531L;

    /**
     * List with all ShoppingCartItems
     **/
    private final List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

    /**
     * Add a shoppingCartItem to the shoppingCart
     *
     * @param item Shopping cart item
     */
    public void addItemToShoppingCart(ShoppingCartItem item) {

        if (item == null) {
            throw new InvalidParameterException("Error adding ShoppingCartItem, it may not be null");
        }

        shoppingCartItems.add(item);
    }

    /**
     * Get all shoppingCart items
     *
     * @return Shopping card items
     */
    public List<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    /**
     * Gets the total amount of all items in the shoppingCart
     *
     * @return Total amount
     */
    public Long getTotalAmount() {

        Long totalAmount = 0L;
        for (ShoppingCartItem item : shoppingCartItems) {
            totalAmount += item.getAmountInCents();
        }

        return totalAmount;
    }
}
