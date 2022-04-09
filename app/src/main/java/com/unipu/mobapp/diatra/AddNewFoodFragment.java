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

import com.unipu.mobapp.diatra.data.Food;
import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;

import java.util.Calendar;

public class AddNewFoodFragment extends Fragment {

    private DayViewModel dayViewModel;

    private EditText editTextTime;
    private EditText editTextDate;
    private EditText editTextAmount;

    private Spinner spinnerType;
    private String foodType;
    private ArrayAdapter<CharSequence> adapter;

    private Button buttonAdd;

    private String date;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidgets(view);
        initObservers();

        date = dayViewModel.getDate().getValue();
        editTextDate.setText(date);

        editTextDate.setOnClickListener(this::showDatePickerDialog);
        editTextTime.setOnClickListener(this::showTimePickerDialog);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Choose type")){

                }
                else{
                    foodType = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String newDate = CalendarUtils.convertDate(day) + "/" + CalendarUtils.convertDate(month+1) + "/" + CalendarUtils.convertDate(year);
                editTextDate.setText(newDate);
            }
        };

        onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String newTime = CalendarUtils.convertDate(hourOfDay) + ":" + CalendarUtils.convertDate(minute);
                editTextTime.setText(newTime);
            }
        };

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFood();
                Navigation.findNavController(view).popBackStack();
            }
        });

    }

    private void initViewModel() {
        dayViewModel = new ViewModelProvider(requireActivity()).get(DayViewModel.class);
    }

    private void initWidgets(View view) {
        buttonAdd = view.findViewById(R.id.button_save_food);

        editTextDate = view.findViewById(R.id.edit_text_food_date);
        editTextTime = view.findViewById(R.id.edit_text_food_time);
        editTextAmount = view.findViewById(R.id.edit_text_amount);

        spinnerType = view.findViewById(R.id.spinner_food_type);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.array_food, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
    }

    private void initObservers() {
        dayViewModel.getsFood().observe(this, new Observer<Food>() {
            @Override
            public void onChanged(Food food) {
                if(food != null) setSFood(food);
            }
        });
    }

    private void setSFood(Food food) {
        editTextTime.setText(food.getTime());
        editTextDate.setText(date);
        buttonAdd.setText("Save edit");

        if(food.getAmount() == 0.0){
            editTextAmount.setText("");
        }
        else {
            editTextAmount.setText(String.valueOf(food.getAmount()));
        }

        spinnerType.setSelection(adapter.getPosition(food.getFoodType()));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = food.getId();
                editFood(id);
                Navigation.findNavController(view).popBackStack();
            }
        });

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

    private void saveFood() {
        String date = editTextDate.getText().toString();
        String time = editTextTime.getText().toString();
        String type = foodType;
        Integer amount = Integer.parseInt(editTextAmount.getText().toString());

        if(time.trim().isEmpty() || type.trim().isEmpty()){
            Toast.makeText(getActivity(), "time or type is empty", Toast.LENGTH_SHORT);
            return;
        }

        Food food = new Food(type, amount, 100, 100, date, time);
        dayViewModel.insertFood(food);
    }

    private void editFood(int id) {
        String date = editTextDate.getText().toString();
        String time = editTextTime.getText().toString();
        String type = foodType;
        Integer amount = Integer.parseInt(editTextAmount.getText().toString());

        if(time.trim().isEmpty() || type.trim().isEmpty()){
            Toast.makeText(getActivity(), "time or type is empty", Toast.LENGTH_SHORT);
            return;
        }

        Food food = new Food(type, amount, 100, 100, date, time);
        food.setId(id);
        dayViewModel.updateFood(food);
    }
}