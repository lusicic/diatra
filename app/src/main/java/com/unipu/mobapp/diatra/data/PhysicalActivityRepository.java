package com.unipu.mobapp.diatra.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhysicalActivityRepository {

    private PhysicalActivityDao physicalActivityDao;
    private LiveData<List<PhysicalActivity>> dayPhysicalActivities;

    public PhysicalActivityRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        physicalActivityDao = database.physicalActivityDao();
    }

    public void insertPhysicalActivity(PhysicalActivity physicalActivity){
        new InsertPhysicalActivityAsyncTask(physicalActivityDao).execute(physicalActivity);
    }

    public void updatePhysicalActivity(PhysicalActivity physicalActivity){
        new UpdatePhysicalActivityAsyncTask(physicalActivityDao).execute(physicalActivity);
    }

    public void deletePhysicalActivity(PhysicalActivity physicalActivity){
        new DeletePhysicalActivityAsyncTask(physicalActivityDao).execute(physicalActivity);
    }

    public LiveData<List<PhysicalActivity>> getDayPhysicalActivities(String date) {
        return physicalActivityDao.getDayPhysicalActivities(date);
    }

    public static class InsertPhysicalActivityAsyncTask extends AsyncTask<PhysicalActivity, Void, Void> {
        private PhysicalActivityDao physicalActivityDao;

        private InsertPhysicalActivityAsyncTask(PhysicalActivityDao physicalActivityDao){
            this.physicalActivityDao = physicalActivityDao;
        }

        @Override
        protected Void doInBackground(PhysicalActivity... physicalActivities) {
            physicalActivityDao.insert(physicalActivities[0]);
            return null;
        }
    }

    public static class UpdatePhysicalActivityAsyncTask extends AsyncTask<PhysicalActivity, Void, Void> {
        private PhysicalActivityDao physicalActivityDao;

        private UpdatePhysicalActivityAsyncTask(PhysicalActivityDao physicalActivityDao){
            this.physicalActivityDao = physicalActivityDao;
        }

        @Override
        protected Void doInBackground(PhysicalActivity... physicalActivities) {
            physicalActivityDao.update(physicalActivities[0]);
            return null;
        }
    }

    public static class DeletePhysicalActivityAsyncTask extends AsyncTask<PhysicalActivity, Void, Void> {
        private PhysicalActivityDao physicalActivityDao;

        private DeletePhysicalActivityAsyncTask(PhysicalActivityDao physicalActivityDao){
            this.physicalActivityDao = physicalActivityDao;
        }

        @Override
        protected Void doInBackground(PhysicalActivity... physicalActivities) {
            physicalActivityDao.delete(physicalActivities[0]);
            return null;
        }
    }

}
