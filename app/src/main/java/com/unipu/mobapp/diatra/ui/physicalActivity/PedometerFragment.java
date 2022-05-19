package com.unipu.mobapp.diatra.ui.physicalActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.services.StepsService;
import com.unipu.mobapp.diatra.ui.MainActivity;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.utils.PreferencesUtils;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;

public class PedometerFragment extends Fragment {


    private DayViewModel dayViewModel;
    private TextView textViewTotalSteps;
    private TextView textViewDate;

    private Button buttonStartCounting;
    private Button buttonStopCounting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pedometer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidgets(view);
        initClickListeners();

        String date = CalendarUtils.dayMonth(dayViewModel.getDate().getValue());
        textViewDate.setText(date);

        dayViewModel.getDaySteps().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer!=null){
                    textViewTotalSteps.setText(String.valueOf(integer));
                }
            }
        });

        String checkDate = dayViewModel.getDate().getValue();
        if(!checkDate.equals(CalendarUtils.today())){
            buttonStartCounting.setVisibility(View.GONE);
            buttonStopCounting.setVisibility(View.GONE);
        }
    }

    private void initViewModel(){
        dayViewModel = new ViewModelProvider(requireActivity()).get(DayViewModel.class);
    }

    private void initWidgets(View view) {
        textViewTotalSteps = view.findViewById(R.id.text_view_total_steps);
        textViewDate = view.findViewById(R.id.text_view_steps_date);
        buttonStartCounting = view.findViewById(R.id.button_start);
        buttonStopCounting = view.findViewById(R.id.button_stop);
    }

    private void initClickListeners() {

        buttonStartCounting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startService(new Intent(getActivity(), StepsService.class));
            }
        });

        buttonStopCounting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().stopService(new Intent(getContext(), StepsService.class));
            }
        });
    }


}