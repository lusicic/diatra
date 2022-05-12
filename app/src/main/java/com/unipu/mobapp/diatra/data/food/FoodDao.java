package com.unipu.mobapp.diatra.data.food;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insert(Food food);

    @Update
    void update(Food food);

    @Delete
    void delete(Food food);

    @Query("SELECT * FROM food WHERE date==:date ORDER BY time ASC")
    LiveData<List<Food>> getDayFood(String date);

    @Query("SELECT * FROM food_type WHERE language==:language ORDER BY name ASC")
    LiveData<List<FoodType>> getAllFoodTypes(String language);

}
