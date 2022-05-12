package com.unipu.mobapp.diatra.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.data.user.User;
import com.unipu.mobapp.diatra.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

public class UserFragment extends Fragment {

    UserViewModel userViewModel;

    private TextView textViewGender;
    private TextView textViewAge;
    private TextView textViewHeight;
    private TextView textViewWeight;
    private TextView textViewTypeOfDiabetes;

    private Button buttonEditUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidgets(view);

        userViewModel.getUser().observe(getActivity(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if(user!=null) {
                        updateTextViews(user);
                    }
                }

            private void updateTextViews(User user) {
                textViewGender.setText(user.getGender());
                textViewAge.setText(String.valueOf(user.getAge()));
                textViewHeight.setText(String.valueOf(Math.round(user.getHeight())) + " cm");
                textViewWeight.setText(String.valueOf(Math.round(user.getWeight())) + " kg");
                textViewTypeOfDiabetes.setText(user.getTypeOfDiabetes());
            }
        });

        buttonEditUser.setOnClickListener(this::changeUserInfo);
    }

    private void initViewModel() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    private void initWidgets(View view) {
        buttonEditUser = view.findViewById(R.id.button_edit_user);

        textViewGender = view.findViewById(R.id.text_view_gender);
        textViewAge = view.findViewById(R.id.text_view_age);
        textViewHeight = view.findViewById(R.id.text_view_height);
        textViewWeight = view.findViewById(R.id.text_view_weight);
        textViewTypeOfDiabetes = view.findViewById(R.id.text_view_type_of_diabetes);
    }

    private void changeUserInfo(View view){
        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_addUserFragment);
    }

}