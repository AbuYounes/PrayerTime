package com.example.farid.prayertime.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.farid.prayertime.receiver.AlarmBroadcastReceiver;
import com.example.farid.prayertime.helpers.NotificationHelper;

import com.example.farid.prayertime.model.AlarmTime;
import com.example.farid.prayertime.model.TimePrayer;
import com.example.farid.prayertime.rxbus.RxAlarmTimeInMinutes;
import com.example.farid.prayertime.rxbus.RxBusAlarmAction;
import com.example.farid.prayertime.rxbus.RxBusPrayerTime;
import com.example.farid.prayertime.ui.activity.MainActivity;
import com.example.farid.prayertime.util.PrayerTimeUtils;

import java.io.IOException;
import java.util.Date;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

import static com.example.farid.prayertime.Constants.ALARM_STATUS;


public class AlarmSoundPlayingService extends Service {
    private AlarmManager mAlarmManager;
    private long mMillis;
    private TimePrayer mTimePrayer;
    private AlarmTime mAlarmTime, mAlarmTimeMinutes;
    private MediaPlayer mMediaPlayer;
    private boolean isRunning, notificationEnabled;
    private CompositeDisposable mDisposables = new CompositeDisposable();

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        AlarmSoundPlayingService getService() {
            return AlarmSoundPlayingService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mDisposables.add(RxBusPrayerTime.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                mTimePrayer = (TimePrayer) o;
                mMillis = PrayerTimeUtils.convertTimeStringToMilliSeconds(mTimePrayer);
            }
        }));

        mDisposables.add(RxBusAlarmAction.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Throwable {
                mAlarmTime = (AlarmTime) o;
                int action = mAlarmTime.action;
                if (action == AlarmTime.ALARM_ACTION_SHOW_NOTIFICTION) {
                    notificationEnabled = true;
                } else if (action == AlarmTime.ALARM_ACTION_DELETE_NOTIFICATION) {
                    notificationEnabled = false;
                }
            }
        }));

        mDisposables.add(RxAlarmTimeInMinutes.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Throwable {
                mAlarmTimeMinutes = (AlarmTime) o;
            }
        }));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("PrayerTimeFragment", "Received start id " + startId + ": " + intent.getAction());
        String getAlarmStatus = intent.getStringExtra(ALARM_STATUS);
        if (intent.getAction() != null &&
                intent.getAction().equals("STOP_SERVICE")) {
            stopForeground(true);
        }

        Intent notificationIntent = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationHelper notificationHelper = new NotificationHelper(this);
        Notification notification = notificationHelper.getNotificationBuilder("Fajr Alarm", "Tijd om op te staan", pIntent);
        notificationHelper.getNotificationManager().notify(1, notification);
        startForeground(1, notification);


        switch (getAlarmStatus) {
            case "on":
                PrayerTimeUtils.saveFragState(this, true);
                startId = 1;
                break;
            case "off":
                PrayerTimeUtils.saveFragState(this, false);
                startId = 0;
                break;
            default:
                PrayerTimeUtils.saveFragState(this, false);
                startId = 0;
                break;
        }

        //if there is no alarm sound and the user pressed alarm "on"
        //alarm should stop going off
        if (!this.isRunning && startId == 1) {
            PrayerTimeUtils.playSound(this, mMediaPlayer);
            this.isRunning = true;

            //if there is alarm sound and the user pressed alarm "off"
            //alarm should stop going off
        } else if (this.isRunning && startId == 0) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            stopForeground(true);
            stopSelf();
            this.isRunning = false;

            PrayerTimeUtils.startAlarm(this, mAlarmManager, mAlarmTimeMinutes, mMillis);

            //if there is no alarm sound and the user presses alarm "off" do nothing
        } else {
            mMediaPlayer.stop();
            mMediaPlayer.reset();

            this.isRunning = false;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        this.isRunning = false;
        mDisposables.clear();


    }

}
