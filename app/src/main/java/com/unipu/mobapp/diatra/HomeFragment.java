package com.unipu.mobapp.diatra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.unipu.mobapp.diatra.adapter.CalendarAdapter;
import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;

import static com.unipu.mobapp.diatra.utils.CalendarUtils.formattedDate;
import static com.unipu.mobapp.diatra.utils.CalendarUtils.monthYear;
import static com.unipu.mobapp.diatra.utils.CalendarUtils.showDaysInWeek;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private DayViewModel dayViewModel;

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    private TextView textViewTest;
    private TextView textViewTotalSteps;

    ImageButton btnBack;
    ImageButton btnNext;

    CardView therapyCardView;
    CardView physicalActivityCardView;
    CardView foodCardView;
    CardView newTherapyCardView;
    CardView newActivityCardView;
    CardView newFoodCardView;
    CardView stepsCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidgets(view);

        setUpCalendar();

        dayViewModel.setDate(String.valueOf(formattedDate(CalendarUtils.selectedDate)));

        btnBack.setOnClickListener(this::previousWeekAction);

        btnNext.setOnClickListener(this::nextWeekAction);

        therapyCardView.setOnClickListener(this::toTherapies);
        physicalActivityCardView.setOnClickListener(this::toActivities);
        foodCardView.setOnClickListener(this::toFood);
        newTherapyCardView.setOnClickListener(this::newTherapy);
        newActivityCardView.setOnClickListener(this::newActivity);
        newFoodCardView.setOnClickListener(this::newFood);
        stepsCardView.setOnClickListener(this::toSteps);

        dayViewModel.getDayTherapies().observe(getActivity(), new Observer<List<Therapy>>() {
            @Override
            public void onChanged(List<Therapy> therapies) {
                textViewTest.setText(String.valueOf(therapies.size()));
            }
        });

        dayViewModel.getTotalSteps().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textViewTotalSteps.setText(String.valueOf(integer));
            }
        });
    }

    private void initViewModel(){
        dayViewModel = new ViewModelProvider(requireActivity()).get(DayViewModel.class);
    }

    private void initWidgets(View view){
        therapyCardView = view.findViewById(R.id.therapyCard);
        physicalActivityCardView = view.findViewById(R.id.physicalActivityCard);
        foodCardView = view.findViewById(R.id.mealCard);
        newTherapyCardView = view.findViewById(R.id.newTherapyCard);
        newActivityCardView = view.findViewById(R.id.newActivityCard);
        newFoodCardView = view.findViewById(R.id.newFoodCard);
        stepsCardView = view.findViewById(R.id.stepsCard);

        btnBack = view.findViewById(R.id.button_calendar_back);
        btnNext = view.findViewById(R.id.button_calendar_next);

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);

        textViewTest = view.findViewById(R.id.text_view_test);
        textViewTotalSteps = view.findViewById(R.id.text_view_number_of_steps);
    }


    private void setUpCalendar(){
        monthYearText.setText(monthYear(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = showDaysInWeek(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);

        String selectedDate = formattedDate(CalendarUtils.selectedDate);
        dayViewModel.setDate(selectedDate);

        setUpCalendar();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);

        String selectedDate = formattedDate(CalendarUtils.selectedDate);
        dayViewModel.setDate(selectedDate);

        setUpCalendar();
    }

    public void toTherapies(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_therapyFragment);
    }

    public void toActivities(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_physicalActivityFragment);
    }

    public void toFood(View view){
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_foodFragment);
    }

    public void newTherapy(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addTherapyFragment);
    }

    public void newActivity(View view){
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addPhysicalActivityFragment);
    }

    public void newFood(View view){
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addNewFoodFragment);
    }

    public void toSteps(View view){
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_pedometerFragment);
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        dayViewModel.setDate(formattedDate(date));
        setUpCalendar();
    }
}