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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.unipu.mobapp.diatra.data.User;
import com.unipu.mobapp.diatra.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class AddUserFragment extends Fragment {

    UserViewModel userViewModel;

    private EditText editTextName;
    private EditText editTextDateOfBirth;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private EditText editTextTypeOfDiabetes;

    private Button buttonAddUser;

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidgets(view);

        editTextDateOfBirth.setOnClickListener(this::showDate);

        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });

        userViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user!=null) {
                    editTextName.setText(user.getName());
                    editTextDateOfBirth.setText(user.getDateOfBirth());
                    editTextHeight.setText(String.valueOf(user.getHeight()));
                    editTextWeight.setText(String.valueOf(user.getWeight()));
                    editTextTypeOfDiabetes.setText(user.getTypeOfDiabetes());

                    int id = user.getUserId();
                    buttonAddUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editUser(id);
                            Navigation.findNavController(view).navigate(R.id.action_addUserFragment_to_profileFragment);
                        }
                    });
                }
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String newDate = convertDate(day) + "/" + convertDate(month+1) + "/" + convertDate(year);
                editTextDateOfBirth.setText(newDate);
            }
        };
    }

    private void initViewModel() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    private void initWidgets(View view){
        buttonAddUser = view.findViewById(R.id.button_save_edit_user);

        editTextName = view.findViewById(R.id.edit_text_name);
        editTextDateOfBirth = view.findViewById(R.id.edit_text_date_of_birth);
        editTextHeight = view.findViewById(R.id.edit_text_height);
        editTextWeight = view.findViewById(R.id.edit_text_weight);
        editTextTypeOfDiabetes = view.findViewById(R.id.edit_text_type_of_diabetes);
    }

    private void saveUser() {
        String name = editTextName.getText().toString();
        String dateOfBirth = editTextDateOfBirth.getText().toString();
        Double height = Double.parseDouble(editTextHeight.getText().toString());
        Double weight = Double.parseDouble(editTextWeight.getText().toString());
        String typeOfDiabetes = editTextTypeOfDiabetes.getText().toString();

        User user = new User(name, dateOfBirth, height, weight, typeOfDiabetes);
        userViewModel.insert(user);
    }

    private void editUser(int id) {
        String name = editTextName.getText().toString();
        String dateOfBirth = editTextDateOfBirth.getText().toString();
        Double height = Double.parseDouble(editTextHeight.getText().toString());
        Double weight = Double.parseDouble(editTextWeight.getText().toString());
        String typeOfDiabetes = editTextTypeOfDiabetes.getText().toString();

        User user = new User(name, dateOfBirth, height, weight, typeOfDiabetes);
        user.setUserId(id);
        userViewModel.update(user);
    }

    public void showDate(View v) {
        final Calendar c = Calendar.getInstance();

        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, onDateSetListener, year, month, day);
        datePicker.show();
    }

    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }
}