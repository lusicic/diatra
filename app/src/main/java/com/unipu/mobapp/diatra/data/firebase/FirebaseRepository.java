package com.unipu.mobapp.diatra.data.firebase;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unipu.mobapp.diatra.data.therapy.Therapy;

import java.time.LocalDate;

public class FirebaseRepository {

    private Application application;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

    public FirebaseRepository(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userLiveData = new MutableLiveData<>();
        this.loggedOutLiveData = new MutableLiveData<>();
        this.databaseReference = FirebaseDatabase.getInstance("https://diatra2-default-rtdb.europe-west1.firebasedatabase.app/").getReference();


        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(application.getApplicationContext()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Login Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(application.getApplicationContext()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(application.getApplicationContext(), "Registration Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    public void insertTherapy(String id, Therapy therapy){

        if (firebaseAuth.getCurrentUser() != null) {
            databaseReference
                    .child("users")
                    .child(firebaseAuth.getCurrentUser().getUid())
                    .child("therapies")
                    .child(LocalDate.now().toString())
                    .child(String.valueOf(id))
                    .setValue(therapy);
        }

    }

    public void deleteTherapy(String id){

        if(firebaseAuth.getCurrentUser() != null){
            databaseReference
                    .child("users")
                    .child(firebaseAuth.getCurrentUser().getUid())
                    .child("therapies")
                    .child(LocalDate.now().toString())
                    .child(String.valueOf(id))
                    .removeValue();
        }
    }

    public void insertSteps(String date, int steps){

        if (firebaseAuth.getCurrentUser() != null) {
            databaseReference
                    .child("users")
                    .child(firebaseAuth.getCurrentUser().getUid())
                    .child("steps")
                    .child(date)
                    .setValue(steps);
        }

    }
}
