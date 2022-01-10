package com.unipu.mobapp.diatra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.unipu.mobapp.diatra.data.Therapy;
import com.unipu.mobapp.diatra.viewmodel.TherapyViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeFragment extends Fragment {

    private TherapyViewModel therapyViewModel;
    Button btnTherapy;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        therapyViewModel = new ViewModelProvider(requireActivity()).get(TherapyViewModel.class);

    }

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

        btnTherapy = view.findViewById(R.id.btn_therapy);

        btnTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_therapyFragment);

                //Toast.makeText( getActivity().getApplicationContext(), "boyyy", Toast.LENGTH_LONG).show();

            }
        });

    }
}