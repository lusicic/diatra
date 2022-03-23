package com.unipu.mobapp.diatra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unipu.mobapp.diatra.viewmodel.DayViewModel;
import com.unipu.mobapp.diatra.viewmodel.UserViewModel;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    DayViewModel therapyViewModel;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        therapyViewModel = new ViewModelProvider(this).get(DayViewModel.class);
        therapyViewModel.setDate(String.valueOf(LocalDate.now()));
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        therapyViewModel.setDate(String.valueOf(LocalDate.now()));
        userViewModel.getUser();
        setContentView(R.layout.activity_main);
        setUpNavigation();
        //checkFirstRun();
    }

    public void setUpNavigation() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment, R.id.profileFragment, R.id.settingsFragment).build();

        Toolbar toolbar = findViewById(R.id.toolbar);

        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }

    public void checkFirstRun() {
        SharedPreferences myPref = this.getSharedPreferences(
                "prefName", Context.MODE_PRIVATE);
        boolean firstLaunch = myPref.getBoolean("firstLaunch", true);

        if(firstLaunch){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new WelcomeFragment()).commit();
        }
        myPref.edit().putBoolean("firstLaunch", false).commit();
    }


}
