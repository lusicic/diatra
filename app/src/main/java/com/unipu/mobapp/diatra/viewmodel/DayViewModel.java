package com.unipu.mobapp.diatra.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.unipu.mobapp.diatra.data.food.Food;
import com.unipu.mobapp.diatra.data.food.FoodRepository;
import com.unipu.mobapp.diatra.data.food.FoodType;
import com.unipu.mobapp.diatra.data.physicalActivity.PhysicalActivity;
import com.unipu.mobapp.diatra.data.physicalActivity.PhysicalActivityRepository;
import com.unipu.mobapp.diatra.data.therapy.Therapy;
import com.unipu.mobapp.diatra.data.therapy.TherapyRepository;
import com.unipu.mobapp.diatra.utils.SingleLiveEvent;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DayViewModel extends AndroidViewModel {

    private TherapyRepository therapyRepo;
    private PhysicalActivityRepository physicalActivityRepository;
    private FoodRepository foodRepo;

    private MutableLiveData<String> date = new MutableLiveData<>();

    private LiveData<List<Therapy>> dayTherapies;
    private LiveData<List<PhysicalActivity>> dayPhysicalActivities;
    private LiveData<List<Food>> dayFood;
    private LiveData<List<FoodType>> allFoodTypes;

    private MutableLiveData<Integer> totalDayCalories = new MutableLiveData<>();
    private MutableLiveData<Integer> totalDayCarbs = new MutableLiveData<>();

    private SingleLiveEvent<Therapy> sTherapy = new SingleLiveEvent<>();
    private SingleLiveEvent<PhysicalActivity> sPhysicalActivity = new SingleLiveEvent<>();
    private SingleLiveEvent<Food> sFood = new SingleLiveEvent<>();

    public DayViewModel(@NonNull @NotNull Application application) {
        super(application);
        therapyRepo = new TherapyRepository(application);
        physicalActivityRepository = new PhysicalActivityRepository(application);
        foodRepo = new FoodRepository(application);
        allFoodTypes = foodRepo.getAllFoodTypes();

        dayTherapies = Transformations.switchMap(date,
                date -> therapyRepo.getDayTherapies(date));
        dayPhysicalActivities = Transformations.switchMap(date,
                date -> physicalActivityRepository.getDayPhysicalActivities(date));
        dayFood = Transformations.switchMap(date,
                date -> foodRepo.getDayFood(date));
    }

    // za dohvat terapije na odredeni datum
    // (promjena oznacenog datuma trigera promijenu liste terapije)
    public void setDate(String newDate) { date.setValue(newDate);}
    public LiveData<String> getDate() { return date;}

    // dohvat liste po danu
    public LiveData<List<Therapy>> getDayTherapies() { return dayTherapies; }
    public LiveData<List<PhysicalActivity>> getDayPhysicalActivities() { return dayPhysicalActivities; }
    public LiveData<List<Food>> getDayFood() { return dayFood;}
    public LiveData<List<FoodType>> getAllFoodTypes() { return allFoodTypes; }

    public void setTotalDayCalories(Integer calories) { totalDayCalories.setValue(calories); }
    public LiveData<Integer> getTotalDayCalories() { return totalDayCalories; }

    public void setTotalDayCarbs(Integer carbs) { totalDayCarbs.setValue(carbs); }
    public LiveData<Integer> getTotalDayCarbs() { return totalDayCarbs; }

    // za edit pojedinacne stavke
    public SingleLiveEvent<Therapy> getSTherapy() { return sTherapy; }
    public SingleLiveEvent<PhysicalActivity> getSPhysicalActivity() {return sPhysicalActivity;}
    public SingleLiveEvent<Food> getsFood() { return sFood;}

    public void setSTherapy(Therapy therapy) { sTherapy.setValue(therapy); }
    public void setSPhysicalActivity(PhysicalActivity physicalActivity){sPhysicalActivity.setValue(physicalActivity);}
    public void setSFood(Food food) { sFood.setValue(food);}

    // za bazu
    public void insertTherapy(Therapy therapy) { therapyRepo.insertTherapy(therapy); }
    public void updateTherapy(Therapy therapy){ therapyRepo.updateTherapy(therapy); }
    public void deleteTherapy(Therapy therapy){ therapyRepo.deleteTherapy(therapy); }

    public void insertPhysicalActivity(PhysicalActivity physicalActivity) {physicalActivityRepository.insertPhysicalActivity(physicalActivity);}
    public void updatePhysicalActivity(PhysicalActivity physicalActivity) {physicalActivityRepository.updatePhysicalActivity(physicalActivity);}
    public void deletePhysicalActivity(PhysicalActivity physicalActivity) {physicalActivityRepository.deletePhysicalActivity(physicalActivity);}

    public void insertFood(Food food) { foodRepo.insertFood(food); }
    public void updateFood(Food food) { foodRepo.updateFood(food); }
    public void deleteFood(Food food) { foodRepo.deleteFood(food);}

}
