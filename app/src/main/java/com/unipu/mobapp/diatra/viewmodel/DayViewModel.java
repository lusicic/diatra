package com.unipu.mobapp.diatra.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.unipu.mobapp.diatra.data.PhysicalActivity;
import com.unipu.mobapp.diatra.data.PhysicalActivityDao;
import com.unipu.mobapp.diatra.data.PhysicalActivityRepository;
import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.data.TherapyRepository;
import com.unipu.mobapp.diatra.utils.SingleLiveEvent;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DayViewModel extends AndroidViewModel {

    private TherapyRepository repo;
    private PhysicalActivityRepository physicalActivityRepository;

    private MutableLiveData<String> date = new MutableLiveData<>();

    private LiveData<List<Therapy>> dayTherapies;
    private LiveData<List<PhysicalActivity>> dayPhysicalActivities;

    private SingleLiveEvent<Therapy> sTherapy = new SingleLiveEvent<>();
    private SingleLiveEvent<PhysicalActivity> sPhysicalActivity = new SingleLiveEvent<>();

    public DayViewModel(@NonNull @NotNull Application application) {
        super(application);
        repo = new TherapyRepository(application);
        physicalActivityRepository = new PhysicalActivityRepository(application);
        dayTherapies = Transformations.switchMap(date,
                date -> repo.getDayTherapies(date));
        dayPhysicalActivities = Transformations.switchMap(date,
                date -> physicalActivityRepository.getDayPhysicalActivities(date));
    }

    // za dohvat terapije na odredeni datum
    // (promjena oznacenog datuma trigera promijenu liste terapije)
    public void setDate(String newDate) { date.setValue(newDate);}
    public LiveData<String> getDate() { return date;}

    // dohvat liste po danu
    public LiveData<List<Therapy>> getDayTherapies() { return dayTherapies; }
    public LiveData<List<PhysicalActivity>> getDayPhysicalActivities() { return dayPhysicalActivities; }

    // za edit pojedinacne stavke
    public SingleLiveEvent<Therapy> getSTherapy() {
        return sTherapy;
    }
    public void setSTherapy(Therapy therapy) {
        sTherapy.setValue(therapy);
    }

    public SingleLiveEvent<PhysicalActivity> getSPhysicalActivity() {return sPhysicalActivity;}
    public void setSPhysicalActivity(PhysicalActivity physicalActivity){sPhysicalActivity.setValue(physicalActivity);}

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

    public void insertPhysicalActivity(PhysicalActivity physicalActivity) {physicalActivityRepository.insertPhysicalActivity(physicalActivity);}
    public void updatePhysicalActivity(PhysicalActivity physicalActivity) {physicalActivityRepository.updatePhysicalActivity(physicalActivity);}
    public void deletePhysicalActivity(PhysicalActivity physicalActivity) {physicalActivityRepository.deletePhysicalActivity(physicalActivity);}
}
