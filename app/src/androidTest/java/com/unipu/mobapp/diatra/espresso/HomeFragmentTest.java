package com.unipu.mobapp.diatra.espresso;

import static androidx.test.espresso.Espresso.onView;

import static com.google.common.truth.Truth.assertThat;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.ui.HomeFragment;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HomeFragmentTest {

    @Test
    public void testNavigationToTherapy(){
        TestNavHostController navController = new TestNavHostController(ApplicationProvider.getApplicationContext());

        FragmentScenario homeScenario = FragmentScenario.launchInContainer(HomeFragment.class, null, R.style.Theme_AppCompat);

        homeScenario.onFragment(fragment -> {
            navController.setGraph(R.navigation.main_nav);

            Navigation.setViewNavController(fragment.requireView(), navController);
        });

        onView(ViewMatchers.withId(R.id.therapyCard)).perform(ViewActions.click());
        assertThat(navController.getCurrentDestination().getId()).isEqualTo(R.id.therapyFragment);
    }
}
