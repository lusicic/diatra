package com.unipu.mobapp.diatra;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.unipu.mobapp.diatra.data.PhysicalActivity;
import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;

import java.util.Calendar;

public class AddPhysicalActivityFragment extends Fragment {

    private DayViewModel dayViewModel;

    private EditText editTextPhyActDate;
    private EditText editTextPhyActTime;
    private EditText editTextPhyActDuration;
    private EditText editTextPhyActDistance;
    private EditText editTextPhyActBurntCalories;

    private Spinner spinnerPhyActType;
    private String phyActType;

    private Button buttonAddPhyAct;

    private String date;

    private ArrayAdapter<CharSequence> adapterActivities;

    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_physical_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidgets(view);
        initObservers();

        date = dayViewModel.getDate().getValue();
        editTextPhyActDate.setText(date);

        editTextPhyActDate.setOnClickListener(this::showDatePickerDialog);
        editTextPhyActTime.setOnClickListener(this::showTimePickerDialog);

        spinnerPhyActType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Choose activity")){
                    return;
                }
                else{
                    phyActType = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonAddPhyAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePhysicalActivity();
                Navigation.findNavController(view).popBackStack();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String newDate = CalendarUtils.convertDate(day) + "/" + CalendarUtils.convertDate(month+1) + "/" + CalendarUtils.convertDate(year);
                editTextPhyActDate.setText(newDate);
            }
        };

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String newTime = CalendarUtils.convertDate(hourOfDay) + ":" + CalendarUtils.convertDate(minute);
                editTextPhyActTime.setText(newTime);
            }
        };

    }

    private void initObservers() {
        dayViewModel.getSPhysicalActivity().observe(this, new Observer<PhysicalActivity>() {
            @Override
            public void onChanged(PhysicalActivity physicalActivity) {
                if(physicalActivity != null) setSPhysicalActivity(physicalActivity);
            }
        });
    }

    private void setSPhysicalActivity(PhysicalActivity physicalActivity) {
        editTextPhyActDate.setText(physicalActivity.getDate());
        editTextPhyActTime.setText(physicalActivity.getTime());
        editTextPhyActDuration.setText(String.valueOf(physicalActivity.getDuration()));
        editTextPhyActDistance.setText(String.valueOf(physicalActivity.getDistance()));
        editTextPhyActBurntCalories.setText(String.valueOf(physicalActivity.getBurntCalories()));

        spinnerPhyActType.setSelection(adapterActivities.getPosition(physicalActivity.getTypeOfActivity()));

        buttonAddPhyAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = physicalActivity.getId();
                editPhysicalActivity(id);
                Navigation.findNavController(view).popBackStack();
            }
        });
    }

    private void initViewModel() {
        dayViewModel = new ViewModelProvider(requireActivity()).get(DayViewModel.class);
    }

    private void initWidgets(View view) {
        editTextPhyActDate = view.findViewById(R.id.edit_text_physical_activity_date);
        editTextPhyActTime = view.findViewById(R.id.edit_text_physical_activity_time);
        editTextPhyActDuration = view.findViewById(R.id.edit_text_physical_activity_duration);
        editTextPhyActDistance = view.findViewById(R.id.edit_text_physical_activity_distance);
        editTextPhyActBurntCalories = view.findViewById(R.id.edit_text_physical_activity_burnt_calories);

        spinnerPhyActType = view.findViewById(R.id.spinner_physical_activity_type);

        buttonAddPhyAct = view.findViewById(R.id.button_save_physical_activity);

        adapterActivities = ArrayAdapter.createFromResource(getActivity(), R.array.array_physical_activity, android.R.layout.simple_spinner_item);
        adapterActivities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhyActType.setAdapter(adapterActivities);
    }

    private void savePhysicalActivity() {
        String date = editTextPhyActDate.getText().toString();
        String time = editTextPhyActTime.getText().toString();
        String type = phyActType;
        Double duration = Double.parseDouble(editTextPhyActDuration.getText().toString());
        Double distance = Double.parseDouble(editTextPhyActDistance.getText().toString());
        Double burntCalories = Double.parseDouble(editTextPhyActBurntCalories.getText().toString());

        if(time.trim().isEmpty() || type.trim().isEmpty()){
            Toast.makeText(getActivity(), "time or type is empty", Toast.LENGTH_SHORT);
            return;
        }

        PhysicalActivity physicalActivity = new PhysicalActivity(type, duration, distance, burntCalories, date, time);
        dayViewModel.insertPhysicalActivity(physicalActivity);
    }

    private void editPhysicalActivity(int id) {
        String date = editTextPhyActDate.getText().toString();
        String time = editTextPhyActTime.getText().toString();
        String type = phyActType;
        Double duration = Double.parseDouble(editTextPhyActDuration.getText().toString());
        Double distance = Double.parseDouble(editTextPhyActDistance.getText().toString());
        Double burntCalories = Double.parseDouble(editTextPhyActBurntCalories.getText().toString());

        if(time.trim().isEmpty() || type.trim().isEmpty()){
            Toast.makeText(getActivity(), "time or type is empty", Toast.LENGTH_SHORT);
            return;
        }

        PhysicalActivity physicalActivity = new PhysicalActivity(type, duration, distance, burntCalories, date, time);
        physicalActivity.setId(id);
        dayViewModel.updatePhysicalActivity(physicalActivity);
    }

    public void showTimePickerDialog(View v) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, onTimeSetListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        timePicker.show();
    }


    public void showDatePickerDialog(View v) {
        final Calendar c = Calendar.getInstance();

        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, onDateSetListener, year, month, day);
        datePicker.show();
    }

}