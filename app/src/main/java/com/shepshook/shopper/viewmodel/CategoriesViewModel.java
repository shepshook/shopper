package com.shepshook.shopper.viewmodel;

import android.app.Application;
import android.app.NotificationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.shepshook.shopper.NotificationWorker;
import com.shepshook.shopper.data.ShopperDatabase;
import com.shepshook.shopper.data.entity.Category;
import com.shepshook.shopper.data.entity.Product;
import com.shepshook.shopper.data.network.MockApi;
import com.shepshook.shopper.data.network.NetworkService;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesViewModel extends AndroidViewModel {
    private final String TAG = getClass().getCanonicalName();

    private final ShopperDatabase db = ShopperDatabase.getInstance(getApplication());

    public LiveData<List<Category>> categories;
    public LiveData<List<Product>> recentProducts;

    public CategoriesViewModel(@NonNull Application application) {
        super(application);

        categories = db.categoryDao().getCategoriesLiveData();
        recentProducts = db.productDao().getRecentProductsLiveData();
    }

    public void reloadDataFromApi() {
        MockApi api = NetworkService.getApi();

        Log.i(TAG, "Loading categories...");
        api.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> list = response.body();
                if (list == null) {
                    Log.w(TAG, "Categories list was empty");
                    return;
                }

                Category[] array = new Category[list.size()];
                array = list.toArray(array);

                db.productDao().deleteAll();
                db.categoryDao().deleteAll();
                db.categoryDao().insertCategories(array);
                Log.i(TAG, String.format("%d categories were loaded", list.size()));

                list.forEach(x -> api.getProducts(x.id).enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        List<Product> list = response.body();
                        if (list == null) {
                            Log.w(TAG, String.format("Products list for category %s was empty", x.name));
                            return;
                        }

                        Random rand = new Random();
                        // setting up images myself because MockApi returns only http links which are forbidden
                        // also my pics are prettier :D
                        list.forEach(x -> x.imageUrl = images[rand.nextInt(images.length)]);

                        Product[] array = new Product[list.size()];
                        array = list.toArray(array);

                        db.productDao().insertProducts(array);
                        Log.i(TAG, String.format("%d products were loaded from category %s", list.size(), x.name));
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                }));
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private final String[] images = {
            "https://i.imgur.com/gVhQ5rS.jpeg",
            "https://i.imgur.com/oE0ZEjK.jpeg",
            "https://i.imgur.com/Ostea5d.jpeg",
            "https://i.imgur.com/uhS17LK.jpeg",
            "https://i.imgur.com/ioUqiIC.jpeg",
            "https://i.imgur.com/QKHXkGT.jpeg",
            "https://i.imgur.com/sNkjWGO.jpeg",
            "https://i.imgur.com/b1qiWuB.jpeg",
            "https://i.imgur.com/z5d6fDL.jpg",
            "https://i.imgur.com/ibhWVgp.jpg",
            "https://i.imgur.com/s1JPGYT.jpg",
            "https://i.imgur.com/hYXCl4d.jpg",
            "https://i.imgur.com/R7YvDER.jpg"
    };
}
