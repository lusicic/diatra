package com.unipu.mobapp.diatra.data;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

@Database(entities = {Therapy.class, User.class, PhysicalActivity.class, Food.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TherapyDao therapyDao();
    public abstract UserDao userDao();
    public abstract PhysicalActivityDao physicalActivityDao();
    public abstract FoodDao foodDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
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
            foodDao = db.foodDao();
            //userDao = db.userDao();
        }

        Date time = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        Date date = new Date();
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");

        @Override
        protected Void doInBackground(Void... voids) {
            therapyDao.insert(new Therapy("orally", 15.00, formatter.format(time), formatter2.format(date)));
            therapyDao.insert(new Therapy("orally", 20.00, "14:15", formatter2.format(date)));
            therapyDao.insert(new Therapy("continuously", 10.00, "18:00", "03/04/2022"));

            physicalActivityDao.insert(new PhysicalActivity("walking", 2.0, 15.00, 300.0, "03/04/2022", "14:45"));
            physicalActivityDao.insert(new PhysicalActivity("running", 1.0, 20.00, 189.0, formatter2.format(date), formatter.format(time)));
            physicalActivityDao.insert(new PhysicalActivity("walking", 2.0, 15.00, 340.0, formatter2.format(date), formatter.format(time)));
            physicalActivityDao.insert(new PhysicalActivity("yoga", 1.0, 0.00, 100.0, formatter2.format(date), "18:55"));


            foodDao.insert(new Food("chicken", 150, 100, 50, "03/04/2022", "14:45"));
            foodDao.insert(new Food("chicken", 120, 100, 20, formatter2.format(date), formatter.format(time)));
            foodDao.insert(new Food("salad", 200, 30, 5, formatter2.format(date), formatter.format(time)));
            foodDao.insert(new Food("pasta", 120, 250, 150, formatter2.format(date), "18:22"));
            foodDao.insert(new Food("banana", 120, 250, 150, formatter2.format(date), "12:01"));


            //userDao.insert(new User("", "", 0.00, 0.00, ""));
            return null;
        }
    }
}
