package com.unipu.mobapp.diatra;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unipu.mobapp.diatra.adapter.TherapyAdapter;
import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.viewmodel.TherapyViewModel;

import java.util.List;

public class TherapyFragment extends Fragment {

    private TherapyViewModel therapyViewModel;

    private FloatingActionButton buttonNewTherapy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_therapy, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerViewTherapy = view.findViewById(R.id.recycler_view_therapy);
        buttonNewTherapy = view.findViewById(R.id.button_new_therapy);

        recyclerViewTherapy.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewTherapy.setHasFixedSize(true);

        TherapyAdapter therapyAdapter = new TherapyAdapter();
        recyclerViewTherapy.setAdapter(therapyAdapter);

        therapyViewModel = new ViewModelProvider(requireActivity()).get(TherapyViewModel.class);
        therapyViewModel.getAllTherapies().observe(getActivity(), new Observer<List<Therapy>>() {
            @Override
            public void onChanged(List<Therapy> therapies) {
                //Toast.makeText( getActivity().getApplicationContext(), "heyyy", Toast.LENGTH_LONG).show();
                therapyAdapter.setTherapies(therapies);
            }
        });

        buttonNewTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_therapyFragment_to_addTherapyFragment);
            }
        });



    }

}