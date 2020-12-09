package com.shepshook.shopper.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.shepshook.shopper.data.entity.CartItem;
import com.shepshook.shopper.data.entity.CartItemExp;

import java.util.List;

@Dao
public interface CartItemDao {
    @Transaction
    @Query("select * from cartItem")
    public LiveData<List<CartItemExp>> getCartItemsExpLiveData();

    @Transaction
    @Query("select * from cartItem where id = :id")
    public LiveData<List<CartItemExp>> getCartItemExpLiveData(int id);

    @Query("select * from CartItem")
    public LiveData<List<CartItem>> getCartItemsLiveData();

    @Query("select * from CartItem where id = :id")
    public LiveData<CartItem> getCartItemLiveData(int id);

    @Query("select * from CartItem")
    public List<CartItem> getCartItems();

    @Query("delete from CartItem")
    public void deleteAll();

    @Delete
    public void deleteCartItem(CartItem item);

    @Insert
    public void insertCartItems(CartItem... items);

    @Update
    public void updateCartItem(CartItem item);
}
