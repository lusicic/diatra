package com.unipu.mobapp.diatra.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.data.TherapyRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TherapyViewModel extends AndroidViewModel {

    private TherapyRepository repo;
    private LiveData<List<Therapy>> allTherapies;

    private MutableLiveData<Therapy> oneTherapy = new MutableLiveData<>();

    //za new MutableLiveData<String>()
    private MutableLiveData<String> datum = new MutableLiveData<>();
    private LiveData<List<Therapy>> dayTherapies;

    public TherapyViewModel(@NonNull @NotNull Application application) {
        super(application);
        repo = new TherapyRepository(application);
        allTherapies = repo.getAllTherapies();
        dayTherapies = Transformations.switchMap(datum,
                datum -> repo.getDayTherapies(datum));
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

    ///// filtriranje

    public void setDatum(String date) { datum.setValue(date);}

    public LiveData<List<Therapy>> getDayTherapies() {
        return dayTherapies;
    }


}
