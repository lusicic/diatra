package com.unipu.mobapp.diatra.ui.physicalActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.utils.PreferencesUtils;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;

public class PedometerFragment extends Fragment {


    private DayViewModel dayViewModel;
    private TextView textViewTotalSteps;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedometer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewTotalSteps = view.findViewById(R.id.totalsteps);

        initViewModel();

    }

    private void initViewModel(){
        dayViewModel = new ViewModelProvider(requireActivity()).get(DayViewModel.class);
    }
}