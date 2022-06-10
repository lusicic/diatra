package com.unipu.mobapp.diatra.ui.food;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.data.food.Food;
import com.unipu.mobapp.diatra.data.food.FoodType;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.utils.PreferencesUtils;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNewFoodFragment extends Fragment {

    private DayViewModel dayViewModel;

    private EditText editTextTime;
    private EditText editTextDate;
    private EditText editTextAmount;

    private TextView textViewFoodType;

    private ArrayList<FoodType> foodTypeList;
    private Double calories;
    private Double carbs;

    private Dialog searchDialog;

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

        initListeners();

        foodTypeList=new ArrayList<>();
        initObservers();

        date = dayViewModel.getDate().getValue();
        editTextDate.setText(date);

    }

    private void initViewModel() {
        dayViewModel = new ViewModelProvider(requireActivity()).get(DayViewModel.class);
    }

    private void initWidgets(View view) {
        buttonAdd = view.findViewById(R.id.button_save_food);

        editTextDate = view.findViewById(R.id.edit_text_food_date);
        editTextTime = view.findViewById(R.id.edit_text_food_time);
        editTextAmount = view.findViewById(R.id.edit_text_amount);

        textViewFoodType = view.findViewById(R.id.text_view_type);
    }

    private void initListeners() {
        editTextDate.setOnClickListener(this::showDatePickerDialog);
        editTextTime.setOnClickListener(this::showTimePickerDialog);

        textViewFoodType.setOnClickListener(this::showSearchDialog);

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String newDate = CalendarUtils.convertDate(day) + "-" + CalendarUtils.convertDate(month+1) + "-" + CalendarUtils.convertDate(year);
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
                if(checkEntry()) {
                    saveFood();
                    Navigation.findNavController(view).popBackStack();
                }
            }
        });
    }

    private void initObservers() {

        dayViewModel.getAllFoodTypes(whichLanguage()).observe(getViewLifecycleOwner(), new Observer<List<FoodType>>() {
            @Override
            public void onChanged(List<FoodType> foodTypes) {
                foodTypeList.addAll(foodTypes);
                //dayViewModel.getAllFoodTypes(whichLanguage()).removeObserver(this::onChanged);
            }

        });

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

        textViewFoodType.setTextColor(Color.parseColor("#000000"));
        textViewFoodType.setText(food.getName());

        calories = food.getCalories();
        carbs = food.getCarbs();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = food.getId();
                if(checkEntry()) {
                    editFood(id);
                    Navigation.findNavController(view).popBackStack();
                }
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

    public void showSearchDialog(View view){
        searchDialog = new Dialog(getActivity());
        searchDialog.setContentView(R.layout.dialog_searchable_spinner);
        searchDialog.getWindow().setLayout(800,1000);

        searchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchDialog.show();

        EditText editText= searchDialog.findViewById(R.id.edit_text_search);
        ListView listView= searchDialog.findViewById(R.id.search_list_view);

        ArrayAdapter<FoodType> adapter = new ArrayAdapter<FoodType>(getActivity(), android.R.layout.simple_list_item_1, foodTypeList);

        listView.setAdapter(adapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textViewFoodType.setTextColor(Color.parseColor("#000000"));
                textViewFoodType.setText(adapter.getItem(position).getName());
                calories = adapter.getItem(position).getCalories();
                carbs = adapter.getItem(position).getCarbs();
                searchDialog.dismiss();
            }
        });

    }


    private void saveFood() {
        String date = editTextDate.getText().toString();
        String time = editTextTime.getText().toString();
        String type = textViewFoodType.getText().toString();
        String sAmount = editTextAmount.getText().toString();

        Integer amount = Integer.parseInt(sAmount);

        int id = (int) (Math.random() * 100000);
        Food food = new Food(type, amount, calories, carbs, date, time);
        food.setId(id);
        dayViewModel.insertFood(food);

        dayViewModel.insertFirebaseFood(String.valueOf(id), food);

    }

    private void editFood(int id) {
        String date = editTextDate.getText().toString();
        String time = editTextTime.getText().toString();
        String type = textViewFoodType.getText().toString();
        Integer amount = Integer.parseInt(editTextAmount.getText().toString());

        Food food = new Food(type, amount, calories, carbs, date, time);
        food.setId(id);
        dayViewModel.updateFood(food);

        dayViewModel.insertFirebaseFood(String.valueOf(id), food);

    }

    private String whichLanguage(){
        String language = PreferencesUtils.getStr(getContext(), "Language");

        if(language.isEmpty()){
            language="en";
        }
        return language;
    }

    private boolean checkEntry(){

        String date = editTextDate.getText().toString();
        String time = editTextTime.getText().toString();
        String type = textViewFoodType.getText().toString();
        String amount = editTextAmount.getText().toString();

        if(date.trim().isEmpty() || time.trim().isEmpty()
                || type.trim().isEmpty() || amount.trim().isEmpty()){
            Toast.makeText(getContext(), getString(R.string.enterAllData), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }
}