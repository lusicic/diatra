package com.unipu.mobapp.diatra.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "food")
public class Food{

    @PrimaryKey (autoGenerate = true)
    private int id;

    private String name;
    private Integer amount;
    private Integer totalCalories;
    private Integer totalCarbs;

    private String date;
    private String time;

    public Food(String name, Integer amount, Integer totalCalories, Integer totalCarbs, String date, String time) {
        this.name = name;
        this.amount = amount;
        this.totalCalories = totalCalories;
        this.totalCarbs = totalCarbs;
        this.date = date;
        this.time = time;
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

    public Integer getAmount() {
        return amount;
    }

    public Integer getTotalCalories() {
        return totalCalories;
    }

    public Integer getTotalCarbs() {
        return totalCarbs;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
