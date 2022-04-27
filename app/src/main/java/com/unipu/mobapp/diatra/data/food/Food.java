package com.unipu.mobapp.diatra.data.food;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "food")
public class Food{

    @ColumnInfo(name = "food_id")
    @PrimaryKey (autoGenerate = true)
    private int id;

    private String name;
    private Integer amount;
    private Double calories;
    private Double carbs;

    private String date;
    private String time;

    public Double totalCalories;
    public Double totalCarbs;

    public Food(String name, Integer amount, Double calories, Double carbs, String date, String time) {
        this.name = name;
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

    public String getName() { return name; }

    public Integer getAmount() {
        return amount;
    }

    public Double getCalories() {
        return calories;
    }

    public Double getCarbs() {
        return carbs;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Double getTotalCalories() {
        totalCalories = amount*calories;
        return totalCalories;
    }

    public Double getTotalCarbs() {
        totalCarbs = amount*carbs;
        return totalCarbs;
    }
}
