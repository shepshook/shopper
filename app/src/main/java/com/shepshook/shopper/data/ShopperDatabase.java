package com.shepshook.shopper.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shepshook.shopper.data.dao.CartItemDao;
import com.shepshook.shopper.data.dao.CategoryDao;
import com.shepshook.shopper.data.dao.ProductDao;
import com.shepshook.shopper.data.entity.CartItem;
import com.shepshook.shopper.data.entity.Category;
import com.shepshook.shopper.data.entity.Product;

@Database(entities = {Category.class, CartItem.class, Product.class}, version = 3, exportSchema = false)
public abstract class ShopperDatabase extends RoomDatabase {
    public abstract CategoryDao categoryDao();
    public abstract ProductDao productDao();
    public abstract CartItemDao cartItemDao();

    private static ShopperDatabase instance;
    public static ShopperDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, ShopperDatabase.class, "database")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        return instance;
    }
}
