package com.shepshook.shopper.data.entity;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity(
        tableName = "category"
)
public class Category {

//    @SerializedName("id")
    @PrimaryKey
    public int id;

//    @SerializedName("name")
    public String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    @Ignore
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Ignore
    private static MutableLiveData<List<Category>> categories = new MutableLiveData<>(
        Stream.of(
            new Category(1, "Cookies"),
            new Category(2, "Also Cookies"))
        .collect(Collectors.toList()));

    @Ignore
    public static LiveData<List<Category>> getCategories() {
        return categories;
    }
}
