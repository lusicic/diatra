package com.unipu.mobapp.diatra;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.unipu.mobapp.diatra.viewmodel.DatePickerViewModel;
import com.unipu.mobapp.diatra.viewmodel.TimePickerViewModel;

import java.time.LocalDate;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    DatePickerViewModel datePickerViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        datePickerViewModel = new ViewModelProvider(requireActivity()).get(DatePickerViewModel.class);

        final Calendar c = Calendar.getInstance();

        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        String monthy="";

        if ((month+1)<10){
            monthy = "0"+ (month+1);
        }

        String newDate = day + "/" + monthy + "/" + year;
        datePickerViewModel.setDate(newDate);
    }

}
