package com.shepshook.shopper.data.entity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.shepshook.shopper.data.TimestampConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity(
        tableName = "product",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "id",
                childColumns = "categoryID",
                onDelete = ForeignKey.CASCADE
        )
)
public class Product {

    @PrimaryKey
    public int id;
    public String name;
    public String description;
    public float price;
    public String imageUrl;

    @TypeConverters(TimestampConverter.class)
    public Date accessTime = null;

    public int categoryID;

    public Product() { }

    @Ignore
    public Product(int id, String name, String description, float price, String imageUrl, int categoryID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryID = categoryID;
    }

    @Ignore
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Float.compare(product.price, price) == 0 &&
                categoryID == product.categoryID &&
                name.equals(product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(imageUrl, product.imageUrl);
    }

    @Ignore
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, imageUrl, categoryID);
    }

    @Ignore
    private static MutableLiveData<List<Product>> products = new MutableLiveData<>(
            Stream.of(
                new Product(1, "Cookies 1", "They are delicious", 1.99f, "https://i.imgur.com/z5d6fDL.jpg", 1),
                new Product(2, "Cookies 2", "They are delicious", 2.99f, "https://i.imgur.com/ibhWVgp.jpg", 1),
                new Product(3, "Cookies 3", "They are delicious", 3.99f, "https://i.imgur.com/s1JPGYT.jpg", 2),
                new Product(4, "Cookies 4", "They are delicious", 4.99f, "https://i.imgur.com/hYXCl4d.jpg", 2),
                new Product(5, "Cookies 5", "They are delicious", 33.99f, "https://i.imgur.com/R7YvDER.jpg", 2))
            .collect(Collectors.toList()));

    @Ignore
    private static MutableLiveData<List<Product>> recentProducts = new MutableLiveData<>(new ArrayList<>());

    @Ignore
    public static LiveData<List<Product>> getProducts() {
        return products;
    }

    @Ignore
    public static LiveData<List<Product>> getRecentProducts() {
        return recentProducts;
    }

    @Ignore
    public static void addRecent(Product product) {
        List<Product> list = recentProducts.getValue();
        if (!list.contains(product)) {
            list.add(product);
            recentProducts.setValue(list);
        }
    }
}
