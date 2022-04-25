package com.unipu.mobapp.diatra.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.ui.MainActivity;

import org.jetbrains.annotations.NotNull;

public class WelcomeFragment extends Fragment {

    Button nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).getSupportActionBar().hide();
        nextButton = view.findViewById(R.id.btn_welcome);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(android.R.id.content, new WelcomeUserFragment())
                        .commit();*/
                getActivity().onBackPressed();
                Toast.makeText( getActivity().getApplicationContext(), "boyyy", Toast.LENGTH_LONG).show();
            }
        });
    }

}