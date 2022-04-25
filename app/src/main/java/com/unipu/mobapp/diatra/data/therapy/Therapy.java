package com.unipu.mobapp.diatra.data.therapy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "therapy")
public class Therapy {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String type;
    private Double dosage;
    private String time;
    private String date;

    public Therapy(String type, Double dosage, String time, String date) {
        this.type = type;
        this.dosage = dosage;
        this.time = time;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Double getDosage() {
        return dosage;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {return date;}
}
