package com.unipu.mobapp.diatra.data.food;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_type")
public class FoodType {

    @PrimaryKey (autoGenerate = true)
    private int id;

    private String name;
    private Double calories;
    private Double carbs;
    private String language;

    public FoodType(String name, Double calories, Double carbs, String language) {
        this.name = name;
        this.calories = calories;
        this.carbs = carbs;
        this.language = language;
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

    public String getLanguage() { return language; }

    @Override
    public String toString() {
        return this.name.toString();
    }

}
