package com.unipu.mobapp.diatra.espresso;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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
import static org.junit.Assert.assertEquals;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.unipu.mobapp.diatra.*;
import com.unipu.mobapp.diatra.ui.MainActivity;
import com.unipu.mobapp.diatra.ui.therapy.AddTherapyFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AddTherapyTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    private FragmentScenario scenario;
    private View decorView;

    @Before
    public void setUp(){

        activityScenarioRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                decorView = activity.getWindow().getDecorView();
            }
        });

        scenario = FragmentScenario.launchInContainer(AddTherapyFragment.class, null, R.style.Theme_AppCompat);
        scenario.moveToState(Lifecycle.State.STARTED);
    }

    @Test
    public void testTypeSpinner(){
        String type = "Orally";
        //onView(withId(R.id.edit_text_therapy_date)).perform(click());

        onView(withId(R.id.type_spinner)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.type_spinner)).check(matches(withSpinnerText(containsString(type))));

        onView(withId(R.id.button_save_therapy)).perform(click());

        onView(withText(R.string.enterAllData))
                .inRoot(withDecorView(not(decorView)))// Here we use decorView
                .check(matches(isDisplayed()));
    }



}