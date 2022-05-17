package com.unipu.mobapp.diatra.data.physicalActivity;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.unipu.mobapp.diatra.data.AppDatabase;
import com.unipu.mobapp.diatra.data.therapy.Therapy;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PhysicalActivityRepository {

    private PhysicalActivityDao physicalActivityDao;
    private LiveData<List<PhysicalActivity>> dayPhysicalActivities;

    private PedometerDao pedometerDao;

    private String latest;

    private Executor executor = Executors.newSingleThreadExecutor();

    public PhysicalActivityRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        physicalActivityDao = database.physicalActivityDao();
        pedometerDao = database.pedometerDao();
    }

    public void insertPhysicalActivity(PhysicalActivity physicalActivity){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                physicalActivityDao.insert(physicalActivity);
            }
        });
    }

    public void updatePhysicalActivity(PhysicalActivity physicalActivity){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                physicalActivityDao.update(physicalActivity);
            }
        });
    }

    public void deletePhysicalActivity(PhysicalActivity physicalActivity){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                physicalActivityDao.delete(physicalActivity);
            }
        });
    }

    public LiveData<List<PhysicalActivity>> getDayPhysicalActivities(String date) {
        return physicalActivityDao.getDayPhysicalActivities(date);
    }


    public void insertSteps(Pedometer pedometer){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                pedometerDao.insert(pedometer);
            }
        });
    }

    public void updateSteps(int steps){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                pedometerDao.update(steps);
            }
        });
    }

    public LiveData<Integer> getDaySteps(String date){
        return pedometerDao.getDaySteps(date);
    }

    public void returnLatest() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                latest = pedometerDao.getLatest();
            }
        });
    }

    public String getLatest(){
        return latest;
    }
}
