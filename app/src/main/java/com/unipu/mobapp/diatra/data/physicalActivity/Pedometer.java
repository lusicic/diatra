package com.unipu.mobapp.diatra.data.physicalActivity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pedometer")
public class Pedometer{

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;
    private int steps;

    public Pedometer(String date, int steps) {
        this.date = date;
        this.steps = steps;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getSteps() {
        return steps;
    }
}
