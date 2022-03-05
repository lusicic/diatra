package com.unipu.mobapp.diatra;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unipu.mobapp.diatra.data.AppDatabase;
import com.unipu.mobapp.diatra.data.User;
import com.unipu.mobapp.diatra.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

public class UserFragment extends Fragment {

    UserViewModel userViewModel;

    private TextView textViewName;
    private TextView textViewDateOfBirth;
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

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.getUser();
        buttonEditUser = view.findViewById(R.id.button_edit_user);

        textViewName = view.findViewById(R.id.text_view_name);
        textViewDateOfBirth = view.findViewById(R.id.text_view_date_of_birth);
        textViewHeight = view.findViewById(R.id.text_view_height);
        textViewWeight = view.findViewById(R.id.text_view_weight);
        textViewTypeOfDiabetes = view.findViewById(R.id.text_view_type_of_diabetes);

        userViewModel.getUser().observe(getActivity(), new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if(user==null) {
                        textViewName.setText("");
                        textViewDateOfBirth.setText("");
                        textViewHeight.setText("");
                        textViewWeight.setText("");
                        textViewTypeOfDiabetes.setText("");
                    }
                    else{
                        textViewName.setText(user.getName());
                        textViewDateOfBirth.setText(user.getDateOfBirth());
                        textViewHeight.setText(String.valueOf(user.getHeight()));
                        textViewWeight.setText(String.valueOf(user.getWeight()));
                        textViewTypeOfDiabetes.setText(user.getTypeOfDiabetes());
                    }
                }
        });

        buttonEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_addUserFragment);
            }
        });
    }

}