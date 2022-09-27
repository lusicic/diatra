package com.unipu.mobapp.diatra.data.therapy;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.unipu.mobapp.diatra.data.AppDatabase;
import com.unipu.mobapp.diatra.data.therapy.Therapy;
import com.unipu.mobapp.diatra.data.therapy.TherapyDao;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TherapyRepository {

    private TherapyDao therapyDao;
    private LiveData<List<Therapy>> allTherapies;

    private Executor executor = Executors.newSingleThreadExecutor();

    public TherapyRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        therapyDao = database.therapyDao();
        allTherapies = therapyDao.getAllTherapies();
    }

    public void insertTherapy(Therapy therapy){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                therapyDao.insert(therapy);
            }
        });
    }


    public void updateTherapy(Therapy therapy){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                therapyDao.update(therapy);
            }
        });
    }

    public void deleteTherapy(Therapy therapy){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                therapyDao.delete(therapy);
            }
        });
    }

    public void deleteAllTherapies(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                therapyDao.deleteAllTherapies();
            }
        });
    }

    public LiveData<List<Therapy>> getAllTherapies(){
        return allTherapies;
    }

    public LiveData<List<Therapy>> getDayTherapies(String date) {
        return therapyDao.getDayTherapies(date);
    }

}
