package com.unipu.mobapp.diatra.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.data.User;
import com.unipu.mobapp.diatra.data.UserRepository;

import org.jetbrains.annotations.NotNull;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repo;
    private LiveData<User> user;

    private MutableLiveData<User> useric = new MutableLiveData<>();

    public UserViewModel(@NonNull @NotNull Application application) {
        super(application);
        repo = new UserRepository(application);
        user = repo.getUser();
    }

    public void insert(User user) {repo.insertUser(user);}

    public void update(User user) {repo.updateUser(user);}

    public void delete(User user) {repo.deleteUser(user);}

    public LiveData<User> getUser() {return user;}

    public LiveData<User> getUseric() {return useric;}

    public void setUseric (User user) { useric.setValue(user); };
}
