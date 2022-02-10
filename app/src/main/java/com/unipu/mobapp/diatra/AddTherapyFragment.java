package com.unipu.mobapp.diatra;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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
import com.unipu.mobapp.diatra.viewmodel.TherapyViewModel;

import org.jetbrains.annotations.NotNull;

public class AddTherapyFragment extends Fragment {

    TherapyViewModel therapyViewModel;

    private EditText editTextTherapyTime;
    private EditText editTextType;
    private EditText editTextDose;

    private Button buttonAddTherapy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_therapy, container, false);


    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        therapyViewModel = new ViewModelProvider(requireActivity()).get(TherapyViewModel.class);

        buttonAddTherapy = view.findViewById(R.id.button_save_therapy);

        editTextTherapyTime = view.findViewById(R.id.edit_text_therapy_time);
        editTextType = view.findViewById(R.id.edit_text_type);
        editTextDose = view.findViewById(R.id.edit_text_dose);

        buttonAddTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTherapy();
                //Navigation.findNavController(view).navigate(R.id.action_addTherapyFragment_to_therapyFragment);
            }
        });

        therapyViewModel.getOneTherapy().observe(getViewLifecycleOwner(), new Observer<Therapy>() {
            @Override
            public void onChanged(Therapy therapy) {
                editTextTherapyTime.setText(therapy.getTime());
                editTextType.setText(therapy.getType());

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
                        therapyViewModel.setOneTherapy(new Therapy("", 0.0, ""));
                        Navigation.findNavController(view).navigate(R.id.action_addTherapyFragment_to_therapyFragment);
                    }
                });
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_addTherapyFragment_to_therapyFragment);
                therapyViewModel.setOneTherapy(new Therapy("", 0.0, ""));
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

    }

    private void saveTherapy() {
        String therapyTime = editTextTherapyTime.getText().toString();
        String type = editTextType.getText().toString();
        Double dose = Double.parseDouble(editTextDose.getText().toString());

        if(therapyTime.trim().isEmpty() || type.trim().isEmpty()){
            Toast.makeText(getActivity(), "time or type is empty", Toast.LENGTH_SHORT);
            return;
        }

        Therapy therapy = new Therapy(type, dose, therapyTime);
        therapyViewModel.insert(therapy);
    }

    private void editTherapy(int id) {
        String therapyTime = editTextTherapyTime.getText().toString();
        String type = editTextType.getText().toString();
        Double dose = Double.parseDouble(editTextDose.getText().toString());

        if(therapyTime.trim().isEmpty() || type.trim().isEmpty()){
            Toast.makeText(getActivity(), "time or type is empty", Toast.LENGTH_SHORT);
            return;
        }

        Therapy therapy = new Therapy(type, dose, therapyTime);
        therapy.setId(id);
        therapyViewModel.update(therapy);
    }

}