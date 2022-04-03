package com.unipu.mobapp.diatra.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "physical_activity_table")
public class PhysicalActivity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String typeOfActivity;
    private String duration;
    private String distance;
    private String burntCalories;

    private String date;
    private String time;

    public PhysicalActivity(String typeOfActivity, String duration, String distance,
                            String burntCalories, String date, String time) {
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

    public String getDuration() {
        return duration;
    }

    public String getDistance() {
        return distance;
    }

    public String getBurntCalories() {
        return burntCalories;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
