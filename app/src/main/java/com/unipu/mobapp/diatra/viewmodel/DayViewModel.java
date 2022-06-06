package com.unipu.mobapp.diatra.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.google.firebase.auth.FirebaseUser;
import com.unipu.mobapp.diatra.data.firebase.FirebaseRepository;
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

    //Login

    private FirebaseRepository firebaseRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

    // Therapy
    private TherapyRepository therapyRepo;
    private LiveData<List<Therapy>> dayTherapies;
    private SingleLiveEvent<Therapy> sTherapy = new SingleLiveEvent<>();

    // Physical activity
    private PhysicalActivityRepository physicalActivityRepository;
    private LiveData<List<PhysicalActivity>> dayPhysicalActivities;
    private MutableLiveData<String> totalDayActive = new MutableLiveData<>();
    private SingleLiveEvent<PhysicalActivity> sPhysicalActivity = new SingleLiveEvent<>();

    //Steps
    private LiveData<Integer> daySteps;

    // Food
    private FoodRepository foodRepo;
    private LiveData<List<Food>> dayFood;
    private MutableLiveData<Integer> totalDayCalories = new MutableLiveData<>();
    private MutableLiveData<Integer> totalDayCarbs = new MutableLiveData<>();
    private SingleLiveEvent<Food> sFood = new SingleLiveEvent<>();

    private MutableLiveData<String> date = new MutableLiveData<>();

    public DayViewModel(@NonNull @NotNull Application application) {
        super(application);
        firebaseRepository = new FirebaseRepository(application);
        userLiveData = firebaseRepository.getUserLiveData();
        loggedOutLiveData = firebaseRepository.getLoggedOutLiveData();

        therapyRepo = new TherapyRepository(application);
        physicalActivityRepository = new PhysicalActivityRepository(application);
        foodRepo = new FoodRepository(application);

        dayTherapies = Transformations.switchMap(date,
                date -> therapyRepo.getDayTherapies(date));
        dayPhysicalActivities = Transformations.switchMap(date,
                date -> physicalActivityRepository.getDayPhysicalActivities(date));
        daySteps = Transformations.switchMap(date,
                date -> physicalActivityRepository.getDaySteps(date));
        dayFood = Transformations.switchMap(date,
                date -> foodRepo.getDayFood(date));
    }

    public void setDate(String newDate) { date.setValue(newDate);}
    public LiveData<String> getDate() { return date;}

    // Login

    public void logOut() {
        firebaseRepository.logOut();
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    // Therapy
    public LiveData<List<Therapy>> getDayTherapies() { return dayTherapies; }

    public void setSTherapy(Therapy therapy) { sTherapy.setValue(therapy); }
    public SingleLiveEvent<Therapy> getSTherapy() { return sTherapy; }

    public void insertTherapy(Therapy therapy) { therapyRepo.insertTherapy(therapy); }
    public void updateTherapy(Therapy therapy){ therapyRepo.updateTherapy(therapy); }
    public void deleteTherapy(Therapy therapy){ therapyRepo.deleteTherapy(therapy); }

    public void insertFirebaseTherapy(String id, Therapy therapy) {  firebaseRepository.insertTherapy(id, therapy); }
    public void deleteFirebaseTherapy(String tDate, String id) { firebaseRepository.deleteTherapy(tDate, id); }

    //Physical activity
    public LiveData<List<PhysicalActivity>> getDayPhysicalActivities() { return dayPhysicalActivities; }

    public LiveData<String> getTotalDayActive() { return totalDayActive; }
    public void setTotalDayActive(String totalActive) { totalDayActive.setValue(totalActive); }

    public void setSPhysicalActivity(PhysicalActivity physicalActivity){sPhysicalActivity.setValue(physicalActivity);}
    public SingleLiveEvent<PhysicalActivity> getSPhysicalActivity() {return sPhysicalActivity;}

    public void insertPhysicalActivity(PhysicalActivity physicalActivity) {physicalActivityRepository.insertPhysicalActivity(physicalActivity);}
    public void updatePhysicalActivity(PhysicalActivity physicalActivity) {physicalActivityRepository.updatePhysicalActivity(physicalActivity);}
    public void deletePhysicalActivity(PhysicalActivity physicalActivity) {physicalActivityRepository.deletePhysicalActivity(physicalActivity);}

    public void insertFirebasePhysicalActivity(String id, PhysicalActivity physicalActivity) {  firebaseRepository.insertPhysicalActivity(id, physicalActivity); }
    public void deleteFirebasePhysicalActivity(String paDate, String id) { firebaseRepository.deletePhysicalActivity(paDate, id); }


    // Steps

    public LiveData<Integer> getDaySteps() { return daySteps; }

    // Food
    public LiveData<List<Food>> getDayFood() { return dayFood;}
    public LiveData<List<FoodType>> getAllFoodTypes(String language) { return foodRepo.getAllFoodTypes(language); }

    public void setTotalDayCalories(Integer calories) { totalDayCalories.setValue(calories); }
    public LiveData<Integer> getTotalDayCalories() { return totalDayCalories; }

    public void setTotalDayCarbs(Integer carbs) { totalDayCarbs.setValue(carbs); }
    public LiveData<Integer> getTotalDayCarbs() { return totalDayCarbs; }

    public void setSFood(Food food) { sFood.setValue(food);}
    public SingleLiveEvent<Food> getsFood() { return sFood;}

    public void insertFood(Food food) { foodRepo.insertFood(food); }
    public void updateFood(Food food) { foodRepo.updateFood(food); }
    public void deleteFood(Food food) { foodRepo.deleteFood(food);}

    public void insertFirebaseFood(String id, Food food) {  firebaseRepository.insertFood(id, food); }
    public void deleteFirebaseFood(String fDate, String id) { firebaseRepository.deleteFood(fDate, id); }

}
