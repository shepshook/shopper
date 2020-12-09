package com.shepshook.shopper.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.shepshook.shopper.data.ShopperDatabase;
import com.shepshook.shopper.data.entity.CartItem;
import com.shepshook.shopper.data.entity.Product;

import java.util.Date;
import java.util.List;

public class ProductsViewModel extends AndroidViewModel {
    private final ShopperDatabase db = ShopperDatabase.getInstance(getApplication());

    public LiveData<List<Product>> productsLiveData;
    public List<Product> products;
    public Product startProduct;

    public ProductsViewModel(@NonNull Application application) {
        super(application);
    }

    public void InitFromCategoryID(int categoryID) {
        productsLiveData = db.productDao().getProductsLiveData(categoryID);
    }

    public void InitFromProductID(int productID) {
        startProduct = db.productDao().getProduct(productID);
        products = db.productDao().getProducts(startProduct.categoryID);
    }

    public void addRecent(Product product) {
        product.accessTime = new Date();
        db.productDao().updateProduct(product);
    }

    public void addToCart(Product product) {
        CartItem item = new CartItem();
        item.productID = product.id;
        item.quantity = 1;

        List<CartItem> list = db.cartItemDao().getCartItems();
        if (list == null) {
            db.cartItemDao().insertCartItems(item);
            return;
        }

        CartItem existingItem = list.stream().filter(x -> x.productID == product.id).findFirst().orElse(null);
        if (existingItem == null) {
            db.cartItemDao().insertCartItems(item);
            return;
        }

        existingItem.quantity++;
        db.cartItemDao().updateCartItem(existingItem);
    }
}
