package com.unipu.mobapp.diatra.data.physicalActivity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.unipu.mobapp.diatra.data.physicalActivity.PhysicalActivity;

import java.util.List;

@Dao
public interface PhysicalActivityDao {

    @Insert
    void insert(PhysicalActivity physicalActivity);

    @Update
    void update(PhysicalActivity physicalActivity);

    @Delete
    void delete(PhysicalActivity physicalActivity);

    @Query("SELECT * FROM physical_activity WHERE date==:date ORDER BY time DESC")
    LiveData<List<PhysicalActivity>> getDayPhysicalActivities(String date);

}
