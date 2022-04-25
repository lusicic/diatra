package com.unipu.mobapp.diatra.data.user;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.unipu.mobapp.diatra.data.AppDatabase;
import com.unipu.mobapp.diatra.data.user.User;
import com.unipu.mobapp.diatra.data.user.UserDao;

public class UserRepository {

    private UserDao userDao;
    private LiveData<User> user;

    public UserRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        userDao = database.userDao();
        user = userDao.getUser();
    }

    public void insertUser(User user){
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void updateUser(User user){
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void deleteUser(User user){
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    public LiveData<User> getUser(){
        return user;
    }

    public static class InsertUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {this.userDao = userDao;}

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    public static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao){this.userDao = userDao;}

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    public static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userdao;

        private DeleteUserAsyncTask(UserDao userDao){this.userdao=userDao;}

        @Override
        protected Void doInBackground(User... users) {
            userdao.delete(users[0]);
            return null;
        }
    }
}
