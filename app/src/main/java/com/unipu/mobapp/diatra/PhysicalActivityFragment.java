package com.unipu.mobapp.diatra;

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
import com.unipu.mobapp.diatra.adapter.PhysicalActivityAdapter;
import com.unipu.mobapp.diatra.data.PhysicalActivity;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;

import java.util.List;

public class PhysicalActivityFragment extends Fragment {

    private DayViewModel dayViewModel;

    private FloatingActionButton buttonNewPhysicalActivity;
    private TextView textViewPhysicalActivitiesNumber;
    private TextView textViewPhysicalActivitiesDate;

    RecyclerView recyclerViewPhysicalActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        textViewPhysicalActivitiesDate.setText(date);

        dayViewModel.getDayPhysicalActivities().observe(getViewLifecycleOwner(), new Observer<List<PhysicalActivity>>() {
            @Override
            public void onChanged(List<PhysicalActivity> physicalActivities) {
                physicalActivityAdapter.setPhysicalActivities(physicalActivities);
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
        textViewPhysicalActivitiesNumber = view.findViewById(R.id.text_view_physical_activity_number);
        textViewPhysicalActivitiesDate = view.findViewById(R.id.text_view_physical_activity_date);
    }
}