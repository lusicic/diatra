package com.unipu.mobapp.diatra.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_type")
public class FoodType {

    @PrimaryKey (autoGenerate = true)
    private int id;

    private String name;
    private Double calories;
    private Double carbs;

    public FoodType(String name, Double calories, Double carbs) {
        this.name = name;
        this.calories = calories;
        this.carbs = carbs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getCalories() {
        return calories;
    }

    public Double getCarbs() {
        return carbs;
    }

}
