package com.unipu.mobapp.diatra.viewmodel;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unipu.mobapp.diatra.LiveDataTestUtil;
import com.unipu.mobapp.diatra.data.AppDatabase;
import com.unipu.mobapp.diatra.data.therapy.Therapy;
import com.unipu.mobapp.diatra.data.therapy.TherapyRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;


@RunWith(AndroidJUnit4.class)
public class ViewModelTest {

    AppDatabase db;
    DayViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        Application app = ApplicationProvider.getApplicationContext();
        db =  Room.inMemoryDatabaseBuilder(app, AppDatabase.class)
                .allowMainThreadQueries().build();
        viewModel = new DayViewModel(app);
    }

    @After
    public void closeDb(){
        db.close();
    }

    @Test
    public void addTherapy() throws Exception{
        Therapy therapy = new Therapy("Orally", 15.00, "15:00", "02-09-2022");
        viewModel.insertTherapy(therapy);

        Therapy therapy2 = new Therapy("Orally", 45.00, "15:01", "02-09-2022");
        viewModel.insertTherapy(therapy2);

        viewModel.getAllTherapies();

        assertThat(LiveDataTestUtil.getValue(viewModel.getAllTherapies()).isEmpty()).isFalse();
    }

    @Test
    public void deleteTherapies() throws Exception{

        viewModel.deleteAllTherapies();

        assertThat(LiveDataTestUtil.getValue(viewModel.getAllTherapies()).isEmpty()).isTrue();
    }
}
