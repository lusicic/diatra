package com.unipu.mobapp.diatra.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.data.TherapyRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TherapyViewModel extends AndroidViewModel {

    private TherapyRepository repo;
    private LiveData<List<Therapy>> allTherapies;

    private MutableLiveData<Therapy> oneTherapy = new MutableLiveData<>();

    public TherapyViewModel(@NonNull @NotNull Application application) {
        super(application);
        repo = new TherapyRepository(application);
        allTherapies = repo.getAllTherapies();
    }

    public void insert(Therapy therapy){
        repo.insertTherapy(therapy);
    }

    public void update(Therapy therapy){
        repo.updateTherapy(therapy);
    }

    public void delete(Therapy therapy){
        repo.deleteTherapy(therapy);
    }

    public LiveData<List<Therapy>> getAllTherapies() {
        return allTherapies;
    }

    public LiveData<Therapy> getOneTherapy() {
        return oneTherapy;
    }

    public void setOneTherapy (Therapy therapy) { oneTherapy.setValue(therapy); };

}
