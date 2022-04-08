package com.unipu.mobapp.diatra.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FoodRepository {

    private FoodDao foodDao;
    private LiveData<List<Food>> dayFood;

    public FoodRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        foodDao = database.foodDao();
    }

    public void insertFood(Food food){
        new InsertFoodAsyncTask(foodDao).execute(food);
    }

    public void updateFood(Food food){
        new UpdateFoodAsyncTask(foodDao).execute(food);
    }

    public void deleteFood(Food food){
        new DeleteFoodAsyncTask(foodDao).execute(food);
    }

    public LiveData<List<Food>> getDayFood(String date){
        return foodDao.getDayFood(date);
    }

    public static class InsertFoodAsyncTask extends AsyncTask<Food, Void, Void>{
        private FoodDao foodDao;

        private InsertFoodAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.insert(foods[0]);
            return null;
        }
    }

    public static class UpdateFoodAsyncTask extends AsyncTask<Food, Void, Void> {
        private FoodDao foodDao;

        private UpdateFoodAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.update(foods[0]);
            return null;
        }
    }

    public static class DeleteFoodAsyncTask extends AsyncTask<Food, Void, Void>{
        private FoodDao foodDao;

        private DeleteFoodAsyncTask(FoodDao foodDao){
            this.foodDao = foodDao;
        }


        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.delete(foods[0]);
            return null;
        }
    }
}
