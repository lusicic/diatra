package com.unipu.mobapp.diatra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ListView;
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
import java.util.Locale;

public class HomeFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private TherapyViewModel therapyViewModel;
    Button btnTherapy;

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView dayView;

    private TextView textViewTest;

    ImageButton btnBack;
    ImageButton btnNext;

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
        therapyViewModel.setDatum(String.valueOf(formattedDate(CalendarUtils.selectedDate)));
        setUpCalendar();

        btnBack.setOnClickListener(this::previousWeekAction);

        btnNext.setOnClickListener(this::nextWeekAction);

        btnTherapy.setOnClickListener(this::navigate);

        therapyViewModel.getDayTherapies().observe(getActivity(), new Observer<List<Therapy>>() {
            @Override
            public void onChanged(List<Therapy> therapies) {
                textViewTest.setText(String.valueOf(therapies.size()));
            }
        });

    }

    private void initWidgets(View view){
        btnBack = view.findViewById(R.id.button_calendar_back);
        btnNext = view.findViewById(R.id.button_calendar_next);

        btnTherapy = view.findViewById(R.id.button_therapy);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        dayView = view.findViewById(R.id.dayView);

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
        setUpCalendar();
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setUpCalendar();
    }

    public void navigate(View view)
    {
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_therapyFragment);
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setUpCalendar();

        String datumic = String.valueOf(formattedDate(CalendarUtils.selectedDate));

        therapyViewModel.setDatum(datumic);

    }
}