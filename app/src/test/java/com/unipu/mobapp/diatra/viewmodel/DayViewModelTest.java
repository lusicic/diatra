package com.unipu.mobapp.diatra.viewmodel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.google.firebase.FirebaseApp;
import com.unipu.mobapp.diatra.BuildConfig;
import com.unipu.mobapp.diatra.data.AppDatabase;
import com.unipu.mobapp.diatra.data.firebase.FirebaseRepository;
import com.unipu.mobapp.diatra.data.food.Food;
import com.unipu.mobapp.diatra.data.food.FoodRepository;
import com.unipu.mobapp.diatra.data.physicalActivity.PhysicalActivity;
import com.unipu.mobapp.diatra.data.physicalActivity.PhysicalActivityRepository;
import com.unipu.mobapp.diatra.data.therapy.Therapy;
import com.unipu.mobapp.diatra.data.therapy.TherapyDao;
import com.unipu.mobapp.diatra.data.therapy.TherapyRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.time.LocalDate;
import java.util.List;


@RunWith(RobolectricTestRunner.class)
@Config(application = Application.class)
public class DayViewModelTest {

    TherapyRepository repo;
    DayViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Mock
    Observer<List<Therapy>> dataObserver;

    @Mock
    List<Therapy> therapyList;

    @Before
    public void setUp(){
        Application app = ApplicationProvider.getApplicationContext();
        AppDatabase db =  Room.inMemoryDatabaseBuilder(app, AppDatabase.class)
                        .allowMainThreadQueries().build();
        FirebaseApp.initializeApp(app);
        repo = mock(TherapyRepository.class);
        viewModel = new DayViewModel(app);
    }

    @Test
    public void testNull() {
        assertNotNull(viewModel.getDayTherapies());
    }




}