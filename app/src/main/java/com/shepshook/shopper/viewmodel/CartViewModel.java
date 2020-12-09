package com.shepshook.shopper.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shepshook.shopper.data.ShopperDatabase;
import com.shepshook.shopper.data.entity.CartItem;
import com.shepshook.shopper.data.entity.CartItemExp;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private final ShopperDatabase db = ShopperDatabase.getInstance(getApplication());

    public LiveData<List<CartItemExp>> cartItems;

    public CartViewModel(@NonNull Application application) {
        super(application);
        cartItems = db.cartItemDao().getCartItemsExpLiveData();
    }

    public void removeOneFrom(CartItem item) {
        item.quantity--;
        db.cartItemDao().updateCartItem(item);
    }

    public void addOneTo(CartItem item) {
        item.quantity++;
        db.cartItemDao().updateCartItem(item);
    }

    public void deleteItem(CartItem item) {
        db.cartItemDao().deleteCartItem(item);
    }

    public float getTotalPrice() {
        if (cartItems.getValue() == null)
            return 0;

        return (float) cartItems.getValue().stream().mapToDouble(x -> x.getTotalPrice()).sum();
    }
}
