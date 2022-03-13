package com.unipu.mobapp.diatra.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DatePickerViewModel extends ViewModel {

    private MutableLiveData<String> date = new MutableLiveData<>();

    public void setDate(String currentDate) { date.setValue(currentDate);}

    public LiveData<String> getDate() { return date; }
}
