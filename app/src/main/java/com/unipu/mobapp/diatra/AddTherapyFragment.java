package com.unipu.mobapp.diatra;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.viewmodel.DatePickerViewModel;
import com.unipu.mobapp.diatra.viewmodel.TimePickerViewModel;
import com.unipu.mobapp.diatra.viewmodel.TherapyViewModel;

import org.jetbrains.annotations.NotNull;

public class AddTherapyFragment extends Fragment {

    TherapyViewModel therapyViewModel;
    TimePickerViewModel timePickerViewModel;
    DatePickerViewModel datePickerViewModel;

    private EditText editTextTherapyTime;
    private EditText editTextType;
    private EditText editTextDose;
    private EditText editTextTherapyDate;

    private Button buttonAddTherapy;

    Observer<String> timeObserver;
    Observer<String> dateObserver;

    String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_therapy, container, false);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        therapyViewModel = new ViewModelProvider(requireActivity()).get(TherapyViewModel.class);
        timePickerViewModel = new ViewModelProvider(requireActivity()).get(TimePickerViewModel.class);
        datePickerViewModel = new ViewModelProvider(requireActivity()).get(DatePickerViewModel.class);

        initWidgets(view);

        date = therapyViewModel.getDate().getValue();
        editTextTherapyDate.setText(date);

        editTextTherapyDate.setOnClickListener(this::showDatePickerDialog);
        editTextTherapyTime.setOnClickListener(this::showTimePickerDialog);

        buttonAddTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTherapy();
                resetAll();
                Navigation.findNavController(view).navigate(R.id.action_addTherapyFragment_to_therapyFragment);
            }
        });

        therapyViewModel.getOneTherapy().observe(getViewLifecycleOwner(), new Observer<Therapy>() {
            @Override
            public void onChanged(Therapy therapy) {
                editTextTherapyTime.setText(therapy.getTime());
                editTextType.setText(therapy.getType());
                editTextTherapyDate.setText(therapy.getDate());

                if(therapy.getDosage() == 0.0){
                    editTextDose.setText("");
                }
                else {
                    editTextDose.setText(String.valueOf(therapy.getDosage()));
                }

                buttonAddTherapy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = therapy.getId();
                        editTherapy(id);
                        therapyViewModel.setOneTherapy(new Therapy("", 0.0, "", ""));
                        timePickerViewModel.setDatum("");
                        datePickerViewModel.setDate(date);
                        Navigation.findNavController(view).navigate(R.id.action_addTherapyFragment_to_therapyFragment);
                    }
                });
            }
        });

        dateObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String date) {
                editTextTherapyDate.setText(date);
            }
        };

        datePickerViewModel.getDate().observe(getViewLifecycleOwner(), dateObserver);

        timeObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String date) {
                editTextTherapyTime.setText(date);
            }
        };

        timePickerViewModel.getDatum().observe(getViewLifecycleOwner(), timeObserver);


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                resetAll();
                Navigation.findNavController(view).navigate(R.id.action_addTherapyFragment_to_therapyFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

    }

    public void resetAll(){
        timePickerViewModel.setDatum("");
        datePickerViewModel.setDate(date);
        therapyViewModel.setOneTherapy(new Therapy("", 0.0, "", ""));
    }

    public void initWidgets(View view){
        buttonAddTherapy = view.findViewById(R.id.button_save_therapy);

        editTextTherapyTime = view.findViewById(R.id.edit_text_therapy_time);
        editTextType = view.findViewById(R.id.edit_text_type);
        editTextDose = view.findViewById(R.id.edit_text_dose);
        editTextTherapyDate = view.findViewById(R.id.edit_text_therapy_date);

    }

    private void saveTherapy() {
        String date = editTextTherapyDate.getText().toString();
        String therapyTime = editTextTherapyTime.getText().toString();
        String type = editTextType.getText().toString();
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
        String type = editTextType.getText().toString();
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
        DialogFragment timeFrag = new TimePickerFragment();
        timeFrag.show(getParentFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment dateFrag = new DatePickerFragment();
        dateFrag.show(getParentFragmentManager(), "datePicker");
    }


}