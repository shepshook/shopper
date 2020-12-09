package com.shepshook.shopper.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "cartItem",
        foreignKeys = @ForeignKey(
                entity = Product.class,
                parentColumns = "id",
                childColumns = "productID",
                onDelete = ForeignKey.CASCADE
        )
)
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int productID;
    public int quantity;
}
