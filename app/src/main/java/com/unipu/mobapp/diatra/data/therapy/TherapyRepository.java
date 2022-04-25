package com.unipu.mobapp.diatra.data.therapy;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.unipu.mobapp.diatra.data.AppDatabase;
import com.unipu.mobapp.diatra.data.therapy.Therapy;
import com.unipu.mobapp.diatra.data.therapy.TherapyDao;

import java.util.List;

public class TherapyRepository {

    private TherapyDao therapyDao;
    private LiveData<List<Therapy>> allTherapies;

    public TherapyRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        therapyDao = database.therapyDao();
        allTherapies = therapyDao.getAllTherapies();
    }

    public void insertTherapy(Therapy therapy){
        new InsertTherapyAsyncTask(therapyDao).execute(therapy);
    }

    public void updateTherapy(Therapy therapy){
        new UpdateTherapyAsyncTask(therapyDao).execute(therapy);
    }

    public void deleteTherapy(Therapy therapy){
        new DeleteTherapyAsyncTask(therapyDao).execute(therapy);
    }

    public LiveData<List<Therapy>> getAllTherapies(){
        return allTherapies;
    }

    public LiveData<List<Therapy>> getDayTherapies(String date) {
        return therapyDao.getDayTherapies(date);
    }

    public static class InsertTherapyAsyncTask extends AsyncTask<Therapy, Void, Void>{
        private TherapyDao therapyDao;

        private InsertTherapyAsyncTask(TherapyDao therapyDao){
            this.therapyDao = therapyDao;
        }

        @Override
        protected Void doInBackground(Therapy... therapies) {
            therapyDao.insert(therapies[0]);
            return null;
        }
    }

    public static class UpdateTherapyAsyncTask extends AsyncTask<Therapy, Void, Void>{
        private TherapyDao therapyDao;

        private UpdateTherapyAsyncTask(TherapyDao therapyDao){
            this.therapyDao = therapyDao;
        }

        @Override
        protected Void doInBackground(Therapy... therapies) {
            therapyDao.update(therapies[0]);
            return null;
        }
    }

    public static class DeleteTherapyAsyncTask extends AsyncTask<Therapy, Void, Void>{
        private TherapyDao therapyDao;

        private DeleteTherapyAsyncTask(TherapyDao therapyDao){
            this.therapyDao = therapyDao;
        }

        @Override
        protected Void doInBackground(Therapy... therapies) {
            therapyDao.delete(therapies[0]);
            return null;
        }
    }

}
