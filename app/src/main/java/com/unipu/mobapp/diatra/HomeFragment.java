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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.unipu.mobapp.diatra.adapter.CalendarAdapter;
import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.viewmodel.TherapyViewModel;

import static com.unipu.mobapp.diatra.utils.CalendarUtils.formattedDate;
import static com.unipu.mobapp.diatra.utils.CalendarUtils.monthYear;
import static com.unipu.mobapp.diatra.utils.CalendarUtils.showDaysInWeek;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private TherapyViewModel therapyViewModel;

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    private TextView textViewTest;

    ImageButton btnBack;
    ImageButton btnNext;

    CardView therapyCardView;
    CardView newTherapyCardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        therapyViewModel = new ViewModelProvider(requireActivity()).get(TherapyViewModel.class);

        initWidgets(view);

        CalendarUtils.selectedDate = LocalDate.now();
        String date = String.valueOf(formattedDate(CalendarUtils.selectedDate));

        therapyViewModel.setDate(date);
        setUpCalendar();

        btnBack.setOnClickListener(this::previousWeekAction);

        btnNext.setOnClickListener(this::nextWeekAction);

        therapyCardView.setOnClickListener(this::toTherapies);

        newTherapyCardView.setOnClickListener(this::newTherapy);

        therapyViewModel.getDayTherapies().observe(getActivity(), new Observer<List<Therapy>>() {
            @Override
            public void onChanged(List<Therapy> therapies) {
                textViewTest.setText(String.valueOf(therapies.size()));
            }
        });

    }

    private void initWidgets(View view){
        therapyCardView = view.findViewById(R.id.therapyCard);
        newTherapyCardView = view.findViewById(R.id.newTherapyCard);

        btnBack = view.findViewById(R.id.button_calendar_back);
        btnNext = view.findViewById(R.id.button_calendar_next);

        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);

        textViewTest = view.findViewById(R.id.text_view_test);
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

        String selectedDate = String.valueOf(formattedDate(CalendarUtils.selectedDate));
        therapyViewModel.setDate(selectedDate);

        setUpCalendar();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);

        String selectedDate = String.valueOf(formattedDate(CalendarUtils.selectedDate));
        therapyViewModel.setDate(selectedDate);

        setUpCalendar();
    }

    public void toTherapies(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_therapyFragment);
    }

    public void newTherapy(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addTherapyFragment);
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;

        String selectedDate = String.valueOf(formattedDate(CalendarUtils.selectedDate));
        therapyViewModel.setDate(selectedDate);

        setUpCalendar();
    }
}