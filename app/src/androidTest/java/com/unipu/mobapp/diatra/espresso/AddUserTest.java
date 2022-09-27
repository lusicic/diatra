package com.unipu.mobapp.diatra.espresso;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.not;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.ui.MainActivity;
import com.unipu.mobapp.diatra.ui.therapy.AddTherapyFragment;
import com.unipu.mobapp.diatra.ui.user.AddUserFragment;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddUserTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    private FragmentScenario scenario;
    private View decorView;

    private TestNavHostController navController;

    @Before
    public void setUp(){

        navController = new TestNavHostController(ApplicationProvider.getApplicationContext());

        activityScenarioRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });

        scenario = FragmentScenario.launchInContainer(AddUserFragment.class);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.onFragment(fragment -> {
            navController.setGraph(R.navigation.main_nav);

            Navigation.setViewNavController(fragment.requireView(), navController);
        });
    }

    @Test
    public void testAddUser(){
        String age = "27";
        String height = "156";
        String weight = "55";
        onView(withId(R.id.spinner_gender)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.edit_text_age)).perform(typeText(age), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.edit_text_height)).perform(typeText(height), pressKey(KeyEvent.KEYCODE_ENTER));
        onView(withId(R.id.edit_text_weight)).perform(typeText(weight), pressKey(KeyEvent.KEYCODE_ENTER));

        onView(withId(R.id.spinner_diabetes_type)).perform(click());
        onData(anything()).atPosition(3).perform(click());

        onView(ViewMatchers.withId(R.id.button_save_edit_user)).perform(scrollTo(), ViewActions.click());
        onView(withText(R.string.userAdded))
                .inRoot(withDecorView(not(decorView)))// Here we use decorView
                .check(matches(isDisplayed()));
    }


}