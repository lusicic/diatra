package com.unipu.mobapp.diatra.viewmodel;

import static org.junit.Assert.*;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import com.unipu.mobapp.diatra.BuildConfig;
import com.unipu.mobapp.diatra.data.food.Food;
import com.unipu.mobapp.diatra.data.food.FoodRepository;
import com.unipu.mobapp.diatra.data.physicalActivity.PhysicalActivity;
import com.unipu.mobapp.diatra.data.physicalActivity.PhysicalActivityRepository;
import com.unipu.mobapp.diatra.data.therapy.Therapy;
import com.unipu.mobapp.diatra.data.therapy.TherapyRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.time.LocalDate;

@RunWith(RobolectricTestRunner.class)
@Config(application = Application.class)
public class DayViewModelTest {

    @Mock
    TherapyRepository therapyRepository;

    @Mock
    PhysicalActivityRepository physicalActivityRepository;

    @Mock
    FoodRepository foodRepository;

    @Mock
    Therapy therapy;

    @Mock
    PhysicalActivity physicalActivity;

    @Mock
    Food food;

    @Before
    public void setUp(){
        Application app = ApplicationProvider.getApplicationContext();
        therapyRepository = new TherapyRepository(app);
        physicalActivityRepository = new PhysicalActivityRepository(app);
        foodRepository = new FoodRepository(app);
    }

    @Test
    public void insertTherapy() {
        therapyRepository.insertTherapy(new Therapy("orally", 15.0, "10:00", "05-05-2022"));
    }

    @Test
    public void updateTherapy() {
        therapyRepository.updateTherapy(new Therapy("orally", 14.0, "14:00", "06-06-2022"));
    }

    @Test
    public void deleteTherapy() {
        therapyRepository.deleteTherapy(therapy);
    }

    @Test
    public void insertPhysicalActivity() {
        physicalActivityRepository.insertPhysicalActivity(new PhysicalActivity("running", "00:30", 5.0, "01-03-2022", "14:55"));
    }
    
}