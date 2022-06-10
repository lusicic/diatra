package com.unipu.mobapp.diatra.ui.physicalActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.adapter.PhysicalActivityAdapter;
import com.unipu.mobapp.diatra.data.physicalActivity.PhysicalActivity;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;

import java.util.List;

public class PhysicalActivityFragment extends Fragment {

    private DayViewModel dayViewModel;

    private FloatingActionButton buttonNewPhysicalActivity;
    private TextView textViewTotalTimeActive;
    private TextView textViewPhysicalActivitiesDate;

    RecyclerView recyclerViewPhysicalActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_physical_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWidgets(view);
        initViewModel();

        recyclerViewPhysicalActivity.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPhysicalActivity.setHasFixedSize(true);

        PhysicalActivityAdapter physicalActivityAdapter = new PhysicalActivityAdapter();
        recyclerViewPhysicalActivity.setAdapter(physicalActivityAdapter);

        String date = dayViewModel.getDate().getValue();
        String formattedDate = CalendarUtils.dayMonth(date);
        textViewPhysicalActivitiesDate.setText(formattedDate);

        textViewTotalTimeActive.setText(dayViewModel.getTotalDayActive().getValue());

        dayViewModel.getDayPhysicalActivities().observe(getViewLifecycleOwner(), new Observer<List<PhysicalActivity>>() {
            @Override
            public void onChanged(List<PhysicalActivity> physicalActivities) {
                physicalActivityAdapter.setPhysicalActivities(physicalActivities);

                int tm = 0;
                for (PhysicalActivity physicalActivity: physicalActivities){
                    String[] arr = physicalActivity.getDuration().split(":");
                    tm += 60 * Integer.parseInt(arr[1]);
                    tm += 3600 * Integer.parseInt(arr[0]);
                }

                int hh = tm / 3600;
                tm %= 3600;
                int mm = tm / 60;

                String totalTimeActive = CalendarUtils.convertDate(hh) + ":" + CalendarUtils.convertDate(mm);
                textViewTotalTimeActive.setText(totalTimeActive);
                dayViewModel.setTotalDayActive(totalTimeActive);
            }
        });

        buttonNewPhysicalActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_physicalActivityFragment_to_addPhysicalActivityFragment);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                dayViewModel.deletePhysicalActivity(physicalActivityAdapter.getPhysicalActivityAt(viewHolder.getAdapterPosition()));
                dayViewModel.deleteFirebasePhysicalActivity(date, String.valueOf(physicalActivityAdapter.getPhysicalActivityAt(viewHolder.getAdapterPosition()).getId()));
                Toast.makeText(getActivity().getApplicationContext(), "Activity deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewPhysicalActivity);

        physicalActivityAdapter.setOnItemClickListener(new PhysicalActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PhysicalActivity physicalActivity) {
                dayViewModel.setSPhysicalActivity(physicalActivity);
                Navigation.findNavController(view).navigate(R.id.action_physicalActivityFragment_to_addPhysicalActivityFragment);
            }
        });
    }

    private void initViewModel() {
        dayViewModel = new ViewModelProvider(requireActivity()).get(DayViewModel.class);
    }

    private void initWidgets(View view) {
        recyclerViewPhysicalActivity = view.findViewById(R.id.recycler_view_physical_activity);
        buttonNewPhysicalActivity = view.findViewById(R.id.button_new_physical_activity);
        textViewTotalTimeActive = view.findViewById(R.id.text_view_physical_activity_total_time_active);
        textViewPhysicalActivitiesDate = view.findViewById(R.id.text_view_physical_activity_date);
    }
}