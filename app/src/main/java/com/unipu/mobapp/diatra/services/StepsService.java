package com.unipu.mobapp.diatra.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.room.Room;

import com.unipu.mobapp.diatra.R;
import com.unipu.mobapp.diatra.data.AppDatabase;
import com.unipu.mobapp.diatra.data.firebase.FirebaseRepository;
import com.unipu.mobapp.diatra.data.physicalActivity.Pedometer;
import com.unipu.mobapp.diatra.data.physicalActivity.PedometerDao;
import com.unipu.mobapp.diatra.data.physicalActivity.PhysicalActivityRepository;
import com.unipu.mobapp.diatra.ui.MainActivity;
import com.unipu.mobapp.diatra.utils.CalendarUtils;
import com.unipu.mobapp.diatra.utils.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class StepsService extends Service implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    PhysicalActivityRepository physicalActivityRepository;
    FirebaseRepository firebaseRepository;

    private static final String ACTION_STOP_LISTEN = "action_stop_listen";

    int deltaSteps = 0;
    int milestoneSteps = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(sensor != null)  sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && ACTION_STOP_LISTEN.equals(intent.getAction())) {
            stopForeground(true);
            stopSelf();
            sensorManager.unregisterListener(this);

            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getBaseContext().getSystemService(ns);
            nMgr.cancel(1);

            return START_NOT_STICKY;
        }
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if(sensor != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            showNotification();
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //Toast.makeText(this, PreferencesUtils.getInt(this, "Steps") + " steps", Toast.LENGTH_SHORT).show();
        int eventSteps = Math.round(event.values[0]);

        if (!PreferencesUtils.getStr(this, "Today").equals(CalendarUtils.today())) {
            PreferencesUtils.saveString(this, "Today", CalendarUtils.today());
            PreferencesUtils.saveInt(this, "Steps", eventSteps);

            physicalActivityRepository = new PhysicalActivityRepository(getApplication());
            physicalActivityRepository.insertSteps(new Pedometer(CalendarUtils.today(), 0));

            milestoneSteps = 0;
            deltaSteps = 0;
        }
        else {
            deltaSteps = eventSteps - PreferencesUtils.getInt(this,"Steps");
            PreferencesUtils.saveInt(this, "DeltaSteps", deltaSteps);

            //if(deltaSteps>0) physicalActivityRepository.updateSteps(PreferencesUtils.getInt(this, "DeltaSteps"));

            if(deltaSteps >= milestoneSteps){
                saveSteps();
                milestoneSteps+=20;
            }

        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        stopForeground(true);
        stopSelf();
        sensorManager.unregisterListener(this);

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getBaseContext().getSystemService(ns);
        nMgr.cancel(1);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(){

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_LOW);
        notificationChannel.setShowBadge(false);

        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),1,intent,0);

        Intent intentic = new Intent(this, StepsService.class);
        intentic.setAction(ACTION_STOP_LISTEN);
        PendingIntent actionIntent = PendingIntent.getService(this, 123, intentic, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification=new Notification.Builder(getBaseContext(),CHANNEL_ID)
                .setContentTitle(getString(R.string.steps))
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_sneaker,"Stop", actionIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_sneaker)
                .build();

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        //notificationManager.notify(1,notification);

        startForeground(101, notification);
    }

    public void saveSteps(){
        firebaseRepository = new FirebaseRepository(getApplication());
        firebaseRepository.insertSteps(CalendarUtils.today(), PreferencesUtils.getInt(this, "DeltaSteps"));

        physicalActivityRepository = new PhysicalActivityRepository(getApplication());
        physicalActivityRepository.updateSteps(PreferencesUtils.getInt(this, "DeltaSteps"));
    }

}
