package com.unipu.mobapp.diatra.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.services.StepsService;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.utils.LanguageUtils;
import com.unipu.mobapp.diatra.utils.PreferencesUtils;

import org.w3c.dom.Text;

import java.util.Locale;

public class SettingsFragment extends Fragment {

    private TextView btnChangeLanguage;
    private TextView btnAutoSteps;

    private String selectedLanguage;
    private String choice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LanguageUtils.loadLocale(getContext());
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidgets(view);
        initClickListeners();

    }

    private void initWidgets(View view) {
        btnChangeLanguage = view.findViewById(R.id.text_view_change_language);
        btnAutoSteps = view.findViewById(R.id.text_view_automatic_steps);

    }

    private void initClickListeners() {

        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLanguageDialog();
            }
        });

        btnAutoSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAutoStepsDialog();
            }
        });
    }


    public void openLanguageDialog() {
        final String[] languages = {getString(R.string.english), getString(R.string.croatian)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.change_language);
        builder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int selected) {
                //selectedLanguage = languages[selected];
                if(selected == 0){
                    selectedLanguage="en";
                }
                else if(selected == 1){
                    selectedLanguage="hr";
                }
            }
        });
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                LanguageUtils.saveLocale(getContext(), selectedLanguage);
                getActivity().recreate();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton( R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void openAutoStepsDialog(){
        final String[] answers = {getString(R.string.yes), getString(R.string.no)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.automatic_steps_title);
        builder.setSingleChoiceItems(answers, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int selected) {
                //selectedLanguage = languages[selected];
                if(selected == 0){
                    choice="yes";
                }
                else if(selected == 1){
                    choice="no";
                }
            }
        });
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                PreferencesUtils.saveString(getContext(), "AutoSteps", choice);
                getActivity().recreate();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton( R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();

    }


}