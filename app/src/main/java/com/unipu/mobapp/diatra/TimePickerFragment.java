package com.unipu.mobapp.diatra;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.unipu.mobapp.diatra.viewmodel.TimePickerViewModel;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    TimePickerViewModel timePickerViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        timePickerViewModel = new ViewModelProvider(requireActivity()).get(TimePickerViewModel.class);

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String newTime = hourOfDay + ":" + minute;

        timePickerViewModel.setDatum(newTime);

    }
}
