package com.unipu.mobapp.diatra.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.unipu.mobapp.diatra.utils.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class StepsService extends Service implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Toast.makeText(this, " steps: " + PreferencesUtils.getInt(this,"DeltaSteps"), Toast.LENGTH_SHORT).show();
        int eventSteps = Math.round(event.values[0]);

        if (!PreferencesUtils.getStr(this, "Today").equals(today())) {
            PreferencesUtils.saveString(this, "Today", today());
            PreferencesUtils.saveInt(this, "Steps", eventSteps);
        } else {
            int deltaSteps = eventSteps - PreferencesUtils.getInt(this,"Steps");
            PreferencesUtils.saveInt(this, "DeltaSteps", deltaSteps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public String today() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(Calendar.getInstance().getTime());
    }

}
