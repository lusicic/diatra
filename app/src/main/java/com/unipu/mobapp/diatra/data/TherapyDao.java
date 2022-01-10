package com.unipu.mobapp.diatra.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TherapyDao {

    @Insert
    void insert(Therapy therapy);

    @Update
    void update(Therapy therapy);

    @Delete
    void delete(Therapy therapy);

    @Query("DELETE from therapy_table")
    void deleteAllTherapies();

    @Query("SELECT * FROM therapy_table ORDER BY time DESC")
    LiveData<List<Therapy>> getAllTherapies();
}