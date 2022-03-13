package com.unipu.mobapp.diatra.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimePickerViewModel extends ViewModel {

    private MutableLiveData<String> datum = new MutableLiveData<>();

    public void setDatum(String datumic) { datum.setValue(datumic);}

    public LiveData<String> getDatum() { return datum; }

}
