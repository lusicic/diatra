package com.unipu.mobapp.diatra.data.physicalActivity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unipu.mobapp.diatra.data.physicalActivity.Pedometer;

@Dao
public interface PedometerDao {

    @Insert
    void insert(Pedometer pedometer);

    @Query("UPDATE pedometer SET steps = :stepsToAdd WHERE id IN(SELECT id FROM pedometer ORDER BY date DESC LIMIT 1)")
    void update(int stepsToAdd);

    @Query("SELECT steps FROM pedometer WHERE date==:date")
    LiveData<Integer> getDaySteps(String date);

    @Query("SELECT * FROM pedometer ORDER BY date DESC LIMIT 1")
    Pedometer getLatest();

}
