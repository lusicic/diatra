package com.unipu.mobapp.diatra.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unipu.mobapp.diatra.data.user.User;
import com.unipu.mobapp.diatra.data.user.UserRepository;

import org.jetbrains.annotations.NotNull;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repo;
    private LiveData<User> user;

    public UserViewModel(@NonNull @NotNull Application application) {
        super(application);
        repo = new UserRepository(application);
        user = repo.getUser();
    }

    public void insert(User user) {repo.insertUser(user);}

    public void update(User user) {repo.updateUser(user);}

    public void delete(User user) {repo.deleteUser(user);}

    public LiveData<User> getUser() {return user;}

}
