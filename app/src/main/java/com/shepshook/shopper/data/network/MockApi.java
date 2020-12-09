package com.shepshook.shopper.data.network;

import com.shepshook.shopper.data.entity.Category;
import com.shepshook.shopper.data.entity.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MockApi {
    @GET("/categories")
    public Call<List<Category>> getCategories();

    @GET("/categories/{id}/products")
    public Call<List<Product>> getProducts(@Path("id") int id);
}
