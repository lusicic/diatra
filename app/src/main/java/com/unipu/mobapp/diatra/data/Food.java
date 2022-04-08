package com.unipu.mobapp.diatra.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "food_table")
public class Food {

    @PrimaryKey (autoGenerate = true)
    private int id;

    private String foodType;
    private Integer amount;
    private Integer calories;
    private Integer carbs;

    private String date;
    private String time;

    public Food(String foodType, Integer amount, Integer calories, Integer carbs, String date, String time) {
        this.foodType = foodType;
        this.amount = amount;
        this.calories = calories;
        this.carbs = carbs;
        this.date = date;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFoodType() {
        return foodType;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getCalories() {
        return calories;
    }

    public Integer getCarbs() {
        return carbs;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
