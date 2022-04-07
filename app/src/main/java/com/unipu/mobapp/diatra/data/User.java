package com.unipu.mobapp.diatra.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;

    private String gender;
    private Integer age;
    private Double height;
    private Double weight;
    private String typeOfDiabetes;

    public User(String gender, Integer age, Double height, Double weight, String typeOfDiabetes) {
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.typeOfDiabetes = typeOfDiabetes;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getGender() { return gender; }

    public Integer getAge() { return age; }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public String getTypeOfDiabetes() {
        return typeOfDiabetes;
    }
}
