package com.unipu.mobapp.diatra.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.viewmodel.LoginRegisterViewModel;

public class LoginFragment extends Fragment {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignIn;
    private TextView textViewSignUp;

    private FirebaseAuth mAuth;

    LoginRegisterViewModel loginRegisterViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        mAuth = FirebaseAuth.getInstance();

        initWidgets(view);

        initClickListeners();

    }

    private void initViewModel() {

        loginRegisterViewModel = new ViewModelProvider(requireActivity()).get(LoginRegisterViewModel.class);

    }

    private void initClickListeners() {

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if (email.length() > 0 && password.length() > 0) {
                    loginRegisterViewModel.login(email, password);
                    Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_homeFragment);
                } else {
                    Toast.makeText(getContext(), "Email Address and Password Must Be Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });

    }

    private void initWidgets(View view) {
        editTextEmail = view.findViewById(R.id.edit_text_sign_in_email);
        editTextPassword = view.findViewById(R.id.edit_text_sign_in_password);
        buttonSignIn = view.findViewById(R.id.btn_sign_in);
        textViewSignUp = view.findViewById(R.id.text_view_sign_up);
    }


}