package com.shepshook.shopper.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://5fcbf18f51f70e00161f2201.mockapi.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    public static MockApi getApi() {
        return retrofit.create(MockApi.class);
    }
}
