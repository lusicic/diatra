package com.unipu.mobapp.diatra;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.unipu.mobapp.diatra.data.User;
import com.unipu.mobapp.diatra.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class AddUserFragment extends Fragment {

    UserViewModel userViewModel;

    private EditText editTextAge;
    private EditText editTextHeight;
    private EditText editTextWeight;

    private Button buttonAddUser;

    private Spinner spinnerGender;
    private ArrayAdapter<CharSequence> genderAdapter;
    private String gender;

    private Spinner spinnerTypeOfDiabetes;
    private ArrayAdapter<CharSequence> typeAdapter;
    private String diabetesType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidgets(view);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Choose category")){
                    return;
                }
                else{
                    gender = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTypeOfDiabetes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Choose category")){

                }
                else{
                    diabetesType = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveUser();
                Navigation.findNavController(view).popBackStack();
            }
        });

        userViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user!=null) {
                    spinnerGender.setSelection(genderAdapter.getPosition(user.getGender()));
                    editTextAge.setText(String.valueOf(user.getAge()));
                    editTextHeight.setText(String.valueOf(user.getHeight()));
                    editTextWeight.setText(String.valueOf(user.getWeight()));
                    spinnerTypeOfDiabetes.setSelection(typeAdapter.getPosition(user.getTypeOfDiabetes()));

                    int id = user.getUserId();
                    buttonAddUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editUser(id);
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
                }
            }
        });

    }

    private void initViewModel() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    private void initWidgets(View view){
        buttonAddUser = view.findViewById(R.id.button_save_edit_user);

        editTextAge = view.findViewById(R.id.edit_text_age);
        editTextHeight = view.findViewById(R.id.edit_text_height);
        editTextWeight = view.findViewById(R.id.edit_text_weight);

        spinnerTypeOfDiabetes = view.findViewById(R.id.spinner_diabetes_type);
        typeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.diabetes_type_array, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeOfDiabetes.setAdapter(typeAdapter);

        spinnerGender = view.findViewById(R.id.spinner_gender);
        genderAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.array_gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

    }

    private void saveUser() {
        String ggender = gender;
        Integer age = Integer.parseInt(editTextAge.getText().toString());
        Double height = Double.parseDouble(editTextHeight.getText().toString());
        Double weight = Double.parseDouble(editTextWeight.getText().toString());
        String typeOfDiabetes = diabetesType;

        User user = new User(ggender, age, height, weight, typeOfDiabetes);
        userViewModel.insert(user);
    }

    private void editUser(int id) {
        String ggender = gender;
        Integer age = Integer.parseInt(editTextAge.getText().toString());
        Double height = Double.parseDouble(editTextHeight.getText().toString());
        Double weight = Double.parseDouble(editTextWeight.getText().toString());
        String typeOfDiabetes = diabetesType;

        User user = new User(gender, age, height, weight, typeOfDiabetes);
        user.setUserId(id);
        userViewModel.update(user);
    }

}