package com.unipu.mobapp.diatra;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unipu.mobapp.diatra.data.AppDatabase;
import com.unipu.mobapp.diatra.data.therapy.Therapy;
import com.unipu.mobapp.diatra.data.therapy.TherapyDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    TherapyDao therapyDao;
    AppDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        Application app = ApplicationProvider.getApplicationContext();
        db =  Room.inMemoryDatabaseBuilder(app, AppDatabase.class)
                .allowMainThreadQueries().build();
        therapyDao = db.therapyDao();
    }

    @After
    public void closeDb(){
        db.close();
    }

    @Test
    public void writeAndReadTherapy() throws Exception{
        Therapy therapy = new Therapy("Orally", 15.00, "15:00", "01-09-2022");
        therapyDao.insert(therapy);
        List<Therapy> allTherapies = LiveDataTestUtil.getValue(therapyDao.getAllTherapies());
        assertThat(allTherapies.get(0).getTime()).matches(therapy.getTime());

    }

}