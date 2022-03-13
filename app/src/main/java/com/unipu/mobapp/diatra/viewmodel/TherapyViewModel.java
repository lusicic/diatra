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

    private MutableLiveData<String> date = new MutableLiveData<>();
    private LiveData<List<Therapy>> dayTherapies;

    // dohvat jedne instance
    private MutableLiveData<Therapy> oneTherapy = new MutableLiveData<>();

    public TherapyViewModel(@NonNull @NotNull Application application) {
        super(application);
        repo = new TherapyRepository(application);
        dayTherapies = Transformations.switchMap(date,
                date -> repo.getDayTherapies(date));
    }


    // za dohvat terapije na odredeni datum
    // (promjena oznacenog datuma trigera promijenu liste terapije)

    public void setDate(String newDate) { date.setValue(newDate);}

    public LiveData<String> getDate() { return date;}

    public LiveData<List<Therapy>> getDayTherapies() {
        return dayTherapies;
    }

    // dohvat/update jedne instance terapije

    public void setOneTherapy (Therapy therapy) { oneTherapy.setValue(therapy); };

    public LiveData<Therapy> getOneTherapy() {
        return oneTherapy;
    }

    // za bazu

    public void insert(Therapy therapy){
        repo.insertTherapy(therapy);
    }

    public void update(Therapy therapy){
        repo.updateTherapy(therapy);
    }

    public void delete(Therapy therapy){
        repo.deleteTherapy(therapy);
    }

}
