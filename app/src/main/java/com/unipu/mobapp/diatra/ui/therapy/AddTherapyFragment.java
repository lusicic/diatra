package com.unipu.mobapp.diatra.ui.therapy;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.data.therapy.Therapy;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class AddTherapyFragment extends Fragment {

    private DayViewModel dayViewModel;

    private EditText editTextTherapyTime;
    private EditText editTextDose;
    private EditText editTextTherapyDate;

    private Spinner spinnerType;
    private String therapyType;

    private Button buttonAddTherapy;

    private String date;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private DatabaseReference mDatabase;

    private ArrayAdapter<CharSequence> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_therapy, container, false);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidgets(view);
        initObservers();

        date = dayViewModel.getDate().getValue();
        editTextTherapyDate.setText(date);

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance("https://diatra2-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        initListeners();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void initViewModel(){
        dayViewModel = new ViewModelProvider(requireActivity()).get(DayViewModel.class);

    }

    public void initWidgets(View view){
        buttonAddTherapy = view.findViewById(R.id.button_save_therapy);

        editTextTherapyTime = view.findViewById(R.id.edit_text_therapy_time);
        editTextDose = view.findViewById(R.id.edit_text_dose);
        editTextTherapyDate = view.findViewById(R.id.edit_text_therapy_date);

        spinnerType = view.findViewById(R.id.type_spinner);
    }

    private void initListeners() {

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
                String newTime = CalendarUtils.convertDate(hourOfDay) + ":" + CalendarUtils.convertDate(minute);
                editTextTherapyTime.setText(newTime);
            }
        };

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String newDate = CalendarUtils.convertDate(day) + "/" + CalendarUtils.convertDate(month+1) + "/" + CalendarUtils.convertDate(year);
                editTextTherapyDate.setText(newDate);
            }
        };

        buttonAddTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTherapy();
                Navigation.findNavController(view).popBackStack();
            }
        });
    }

    public void initObservers(){
            dayViewModel.getSTherapy().observe(this, new Observer<Therapy>() {
                @Override
                public void onChanged(Therapy therapy) {
                    if (null != therapy) setSTherapy(therapy);
                }
            });
    }

    public void setSTherapy(Therapy therapy) {
        editTextTherapyTime.setText(therapy.getTime());
        editTextTherapyDate.setText(date);
        buttonAddTherapy.setText("Save edit");

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
                Navigation.findNavController(view).popBackStack();
            }
        });
    }

    private void saveTherapy() {
        String date = editTextTherapyDate.getText().toString();
        String therapyTime = editTextTherapyTime.getText().toString();
        String type = therapyType;
        Double dose = Double.parseDouble(editTextDose.getText().toString());

        if(therapyTime.trim().isEmpty() || type.trim().isEmpty()){
            Toast.makeText(getActivity(), "time or type is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = (int) (Math.random() * 10000);
        Therapy therapy = new Therapy(id, type, dose, therapyTime, date);
        dayViewModel.insertTherapy(therapy);

        dayViewModel.insertFirebaseTherapy(String.valueOf(id), therapy);
    }

    private void editTherapy(int id) {
        String date = editTextTherapyDate.getText().toString();
        String therapyTime = editTextTherapyTime.getText().toString();
        String type = therapyType;
        Double dose = Double.parseDouble(editTextDose.getText().toString());

        if(therapyTime.trim().isEmpty() || type.trim().isEmpty()){
            Toast.makeText(getActivity(), "time or type is empty", Toast.LENGTH_LONG).show();
            return;
        }

        Therapy therapy = new Therapy(id, type, dose, therapyTime, date);
        therapy.setId(id);
        dayViewModel.updateTherapy(therapy);

        dayViewModel.insertFirebaseTherapy(String.valueOf(id), therapy);
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