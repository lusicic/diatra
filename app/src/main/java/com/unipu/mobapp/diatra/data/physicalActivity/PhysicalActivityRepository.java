package com.unipu.mobapp.diatra.data.physicalActivity;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.unipu.mobapp.diatra.data.AppDatabase;

import java.util.List;

public class PhysicalActivityRepository {

    private PhysicalActivityDao physicalActivityDao;
    private LiveData<List<PhysicalActivity>> dayPhysicalActivities;

    private PedometerDao pedometerDao;

    public PhysicalActivityRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        physicalActivityDao = database.physicalActivityDao();
        pedometerDao = database.pedometerDao();
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

    public void insertSteps(Pedometer pedometer){
        new PhysicalActivityRepository.InsertStepsAsyncTask(pedometerDao).execute(pedometer);
    }

    public void updateSteps(int steps){
        new PhysicalActivityRepository.UpdateStepsAsyncTask(pedometerDao).execute(steps);
    }

    public LiveData<Integer> getDaySteps(String date){
        return pedometerDao.getDaySteps(date);
    }

    public static class InsertStepsAsyncTask extends AsyncTask<Pedometer, Void, Void> {
        private PedometerDao pedometerDao;

        private InsertStepsAsyncTask(PedometerDao pedometerDao){
            this.pedometerDao = pedometerDao;
        }

        @Override
        protected Void doInBackground(Pedometer... pedometers) {
            pedometerDao.insert(pedometers[0]);
            return null;
        }
    }

    public static class UpdateStepsAsyncTask extends AsyncTask<Integer, Void, Void>{

        private PedometerDao pedometerDao;

        private UpdateStepsAsyncTask(PedometerDao pedometerDao){
            this.pedometerDao = pedometerDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            pedometerDao.update(integers[0]);
            return null;
        }
    }

}
