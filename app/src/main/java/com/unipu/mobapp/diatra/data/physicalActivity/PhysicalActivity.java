package com.unipu.mobapp.diatra.data.physicalActivity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "physical_activity")
public class PhysicalActivity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String typeOfActivity;
    private String duration;
    private Double distance;

    private String date;
    private String time;

    public PhysicalActivity(String typeOfActivity, String duration, Double distance, String date, String time) {
        this.typeOfActivity = typeOfActivity;
        this.duration = duration;
        this.distance = distance;
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

    public Double getDistance() {
        return distance;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
