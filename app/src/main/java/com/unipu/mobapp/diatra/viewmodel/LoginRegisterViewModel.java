package com.unipu.mobapp.diatra.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.unipu.mobapp.diatra.data.firebase.FirebaseRepository;

public class LoginRegisterViewModel extends AndroidViewModel {

    private FirebaseRepository firebaseRepository;
    private MutableLiveData<FirebaseUser> userLiveData;

    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);
        firebaseRepository = new FirebaseRepository(application);
        userLiveData = firebaseRepository.getUserLiveData();
    }

    public void login(String email, String password) {
        firebaseRepository.login(email, password);
    }

    public void register(String email, String password) {
        firebaseRepository.register(email, password);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}
