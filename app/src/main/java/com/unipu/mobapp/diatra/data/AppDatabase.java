package com.unipu.mobapp.diatra.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.unipu.mobapp.diatra.data.food.Food;
import com.unipu.mobapp.diatra.data.food.FoodDao;
import com.unipu.mobapp.diatra.data.food.FoodType;
import com.unipu.mobapp.diatra.data.physicalActivity.PhysicalActivity;
import com.unipu.mobapp.diatra.data.physicalActivity.PhysicalActivityDao;
import com.unipu.mobapp.diatra.data.therapy.Therapy;
import com.unipu.mobapp.diatra.data.therapy.TherapyDao;
import com.unipu.mobapp.diatra.data.user.User;
import com.unipu.mobapp.diatra.data.user.UserDao;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

@Database(entities = {Therapy.class, User.class, PhysicalActivity.class, Food.class, FoodType.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TherapyDao therapyDao();
    public abstract UserDao userDao();
    public abstract PhysicalActivityDao physicalActivityDao();
    public abstract FoodDao foodDao();

    private static AppDatabase instance;

    // ovo makla .fallbackToDestructiveMigration()
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
                    .createFromAsset("database/diatra.db")
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TherapyDao therapyDao;
        private UserDao userDao;
        private PhysicalActivityDao physicalActivityDao;
        private FoodDao foodDao;

        private PopulateDbAsyncTask(AppDatabase db){
            therapyDao = db.therapyDao();
            physicalActivityDao = db.physicalActivityDao();
            //foodDao = db.foodDao();
            //userDao = db.userDao();
        }

        Date time = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        Date date = new Date();
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");

        @Override
        protected Void doInBackground(Void... voids) {
            //therapyDao.insert(new Therapy("orally", 15.00, formatter.format(time), formatter2.format(date)));
            //therapyDao.insert(new Therapy("orally", 20.00, "14:15", formatter2.format(date)));
            //therapyDao.insert(new Therapy("continuously", 10.00, "18:00", "03/04/2022"));

            physicalActivityDao.insert(new PhysicalActivity("walking", "02:00", 15.00,"03/04/2022", "14:45"));
            physicalActivityDao.insert(new PhysicalActivity("running", "01:00", 20.00, formatter2.format(date), formatter.format(time)));
            physicalActivityDao.insert(new PhysicalActivity("walking", "00:35", 15.00, formatter2.format(date), formatter.format(time)));
            physicalActivityDao.insert(new PhysicalActivity("yoga", "00:15", 0.00, formatter2.format(date), "18:55"));

            /*foodDao.insert(new Food("chicken", 150, 100, 50, "03/04/2022", "14:45"));
            foodDao.insert(new Food("chicken", 120, 100, 20, formatter2.format(date), formatter.format(time)));
            foodDao.insert(new Food("salad", 200, 30, 5, formatter2.format(date), formatter.format(time)));
            foodDao.insert(new Food("pasta", 120, 250, 150, formatter2.format(date), "18:22"));
            foodDao.insert(new Food("banana", 120, 250, 150, formatter2.format(date), "12:01"));*/

            return null;
        }
    }
}
