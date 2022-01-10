package com.unipu.mobapp.diatra.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "therapy_table")
public class Therapy {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String type;
    private Double dosage;
    private String time;

    public Therapy(String type, Double dosage, String time) {
        this.type = type;
        this.dosage = dosage;
        this.time = time;
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
}
