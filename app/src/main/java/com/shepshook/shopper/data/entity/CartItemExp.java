package com.shepshook.shopper.data.entity;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

public class CartItemExp {
    @Embedded
    public CartItem cartItem;

    @Relation(
            parentColumn = "productID",
            entityColumn = "id"
    )
    public Product product;

    @Ignore
    public float getTotalPrice() {
        return product.price * cartItem.quantity;
    }
}
