package com.unipu.mobapp.diatra.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "physical_activity")
public class PhysicalActivity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String typeOfActivity;
    private Double duration;
    private Double distance;
    private Double burntCalories;

    private String date;
    private String time;

    public PhysicalActivity(String typeOfActivity, Double duration, Double distance,
                            Double burntCalories, String date, String time) {
        this.typeOfActivity = typeOfActivity;
        this.duration = duration;
        this.distance = distance;
        this.burntCalories = burntCalories;
        this.date = date;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTypeOfActivity() {
        return typeOfActivity;
    }

    public Double getDuration() {
        return duration;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getBurntCalories() {
        return burntCalories;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
