package com.unipu.mobapp.diatra;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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

import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.viewmodel.TherapyViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class AddTherapyFragment extends Fragment {

    TherapyViewModel therapyViewModel;

    private EditText editTextTherapyTime;
    private EditText editTextDose;
    private EditText editTextTherapyDate;

    Spinner spinnerType;
    String therapyType;

    private Button buttonAddTherapy;

    Observer<String> dateObserver;

    String date;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_therapy, container, false);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        therapyViewModel = new ViewModelProvider(requireActivity()).get(TherapyViewModel.class);

        initWidgets(view);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        date = therapyViewModel.getDate().getValue();
        editTextTherapyDate.setText(date);

        editTextTherapyDate.setOnClickListener(this::showDatePickerDialog);
        editTextTherapyTime.setOnClickListener(this::showTimePickerDialog);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Choose category")){

                }
                else{
                    therapyType = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String newTime = hourOfDay + ":" + minute;
                editTextTherapyTime.setText(newTime);
            }
        };

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String newMonth="";

                if ((month+1)<10){
                    newMonth = "0"+ (month+1);
                }

                String newDate = day + "/" + newMonth + "/" + year;
                editTextTherapyDate.setText(newDate);
            }
        };

        buttonAddTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTherapy();
                resetAll();
                Navigation.findNavController(view).navigate(R.id.action_addTherapyFragment_to_therapyFragment);
            }
        });

        therapyViewModel.getSingleTherapy().observe(getViewLifecycleOwner(), new Observer<Therapy>() {
            @Override
            public void onChanged(Therapy therapy) {
                editTextTherapyTime.setText(therapy.getTime());
                editTextTherapyDate.setText(therapy.getDate());

                if(therapy.getDosage() == 0.0){
                    editTextDose.setText("");
                }
                else {
                    editTextDose.setText(String.valueOf(therapy.getDosage()));
                }

                spinnerType.setSelection(adapter.getPosition(therapy.getType()));

                buttonAddTherapy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = therapy.getId();
                        editTherapy(id);
                        resetAllExceptDate();
                        Navigation.findNavController(view).navigate(R.id.action_addTherapyFragment_to_therapyFragment);
                    }
                });
            }
        });



        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                resetAllExceptDate();
                Navigation.findNavController(view).navigate(R.id.action_addTherapyFragment_to_therapyFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

    }

    public void resetAll(){
        therapyViewModel.setSingleTherapy(new Therapy("", 0.0, "", ""));
    }

    public void resetAllExceptDate(){
        therapyViewModel.setSingleTherapy(new Therapy("", 0.0, "", date));
    }

    public void initWidgets(View view){
        buttonAddTherapy = view.findViewById(R.id.button_save_therapy);

        editTextTherapyTime = view.findViewById(R.id.edit_text_therapy_time);
        editTextDose = view.findViewById(R.id.edit_text_dose);
        editTextTherapyDate = view.findViewById(R.id.edit_text_therapy_date);

        spinnerType = view.findViewById(R.id.type_spinner);

    }

    private void saveTherapy() {
        String date = editTextTherapyDate.getText().toString();
        String therapyTime = editTextTherapyTime.getText().toString();
        String type = therapyType;
        Double dose = Double.parseDouble(editTextDose.getText().toString());

        if(therapyTime.trim().isEmpty() || type.trim().isEmpty()){
            Toast.makeText(getActivity(), "time or type is empty", Toast.LENGTH_SHORT);
            return;
        }

        Therapy therapy = new Therapy(type, dose, therapyTime, date);
        therapyViewModel.insert(therapy);
    }

    private void editTherapy(int id) {
        String date = editTextTherapyDate.getText().toString();
        String therapyTime = editTextTherapyTime.getText().toString();
        String type = therapyType;
        Double dose = Double.parseDouble(editTextDose.getText().toString());

        if(therapyTime.trim().isEmpty() || type.trim().isEmpty()){
            Toast.makeText(getActivity(), "time or type is empty", Toast.LENGTH_SHORT);
            return;
        }

        Therapy therapy = new Therapy(type, dose, therapyTime, date);
        therapy.setId(id);
        therapyViewModel.update(therapy);
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