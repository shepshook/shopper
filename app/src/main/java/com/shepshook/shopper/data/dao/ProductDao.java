package com.shepshook.shopper.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.google.gson.annotations.SerializedName;
import com.shepshook.shopper.data.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("select * from product")
    public List<Product> getAllProducts();

    @Query("select * from product where categoryID = :categoryID")
    public LiveData<List<Product>> getProductsLiveData(int categoryID);

    @Query("select * from product where categoryID = :categoryID")
    public List<Product> getProducts(int categoryID);

    @Query("select * from product where id = :id")
    public Product getProduct(int id);

    @Query("select * from product where accessTime is not null order by accessTime desc")
    public LiveData<List<Product>> getRecentProductsLiveData();

    @Query("delete from product")
    public void deleteAll();

    @Insert
    public void insertProducts(Product ... items);

    @Update
    public void updateProduct(Product item);
}
