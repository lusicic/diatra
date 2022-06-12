package com.unipu.mobapp.diatra.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.utils.LanguageUtils;
import com.unipu.mobapp.diatra.utils.PreferencesUtils;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;
import com.unipu.mobapp.diatra.viewmodel.UserViewModel;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Locale;

import com.unipu.mobapp.diatra.services.StepsService;


public class MainActivity extends AppCompatActivity{

    DayViewModel dayViewModel;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageUtils.loadLocale(this);
        initViewModels();

        dayViewModel.setDate(String.valueOf(CalendarUtils.formattedDate(LocalDate.now())));
        userViewModel.getUser();

        setContentView(R.layout.activity_main);

        setUpNavigation();

        checkAutoSteps();

    }

    private void initViewModels() {
        dayViewModel = new ViewModelProvider(this).get(DayViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    public void setUpNavigation() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.profileFragment, R.id.settingsFragment).build();

        Toolbar toolbar = findViewById(R.id.toolbar);

        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, Bundle arguments) {
                    if(destination.getId() == R.id.homeFragment
                    || destination.getId() == R.id.profileFragment
                    || destination.getId() == R.id.settingsFragment){
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    }
                    else{
                        bottomNavigationView.setVisibility(View.INVISIBLE);
                    }
                }
        });

    }

    private void checkAutoSteps() {

        if(!PreferencesUtils.getStr(this, "AutoSteps").equals("no")){
            Intent i = new Intent(MainActivity.this, StepsService.class);
            startService(i);
        }
        else if(PreferencesUtils.getStr(this, "AutoSteps").equals("no")){
            stopService(new Intent(MainActivity.this, StepsService.class));
        }

    }

}
