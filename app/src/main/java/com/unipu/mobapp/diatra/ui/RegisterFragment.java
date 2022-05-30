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


public class RegisterFragment extends Fragment {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignUp;
    private TextView textViewSignIn;

    private FirebaseAuth mAuth;

    LoginRegisterViewModel loginRegisterViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        initWidgets(view);
        initClickListeners();
    }

    private void initViewModel() {

        loginRegisterViewModel = new ViewModelProvider(requireActivity()).get(LoginRegisterViewModel.class);

    }

    private void initClickListeners() {

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                if (email.length() > 0 && password.length() > 0) {
                    loginRegisterViewModel.register(email, password);
                } else {
                    Toast.makeText(getContext(), "Email Address and Password Must Be Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment);
            }
        });
    }

    private void initWidgets(View view) {
        editTextEmail = view.findViewById(R.id.edit_text_sign_up_email);
        editTextPassword = view.findViewById(R.id.edit_text_sign_up_password);
        buttonSignUp = view.findViewById(R.id.btn_sign_up);
        textViewSignIn = view.findViewById(R.id.text_view_sign_in);
    }


}