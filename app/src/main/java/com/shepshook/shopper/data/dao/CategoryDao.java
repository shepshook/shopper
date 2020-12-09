package com.shepshook.shopper.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.shepshook.shopper.data.entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("select * from category")
    public LiveData<List<Category>> getCategoriesLiveData();

    @Query("select * from category where id = :id")
    public LiveData<Category> getCategoryLiveData(int id);

    @Query("delete from category")
    public void deleteAll();

    @Insert
    public void insertCategories(Category ... items);
}
