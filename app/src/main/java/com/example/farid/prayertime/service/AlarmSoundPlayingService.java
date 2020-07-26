package com.example.farid.prayertime.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import com.example.farid.prayertime.receiver.AlarmBroadcastReceiver;
import com.example.farid.prayertime.helpers.NotificationHelper;

import com.example.farid.prayertime.model.AlarmTime;
import com.example.farid.prayertime.model.TimePrayer;
import com.example.farid.prayertime.rxbus.RxAlarmTimeInMinutes;
import com.example.farid.prayertime.rxbus.RxBusPrayerTime;
import com.example.farid.prayertime.ui.activity.MainActivity;
import com.example.farid.prayertime.util.PrayerTimeUtils;

import java.io.IOException;
import java.util.Date;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

import static com.example.farid.prayertime.Constants.ALARM_STATUS;


public class AlarmSoundPlayingService extends Service {
    private AlarmManager mAlarmManager;
    private long mMillis;
    private TimePrayer mTimePrayer;
    private AlarmTime mAlarmTimeMinutes;
    private MediaPlayer mMediaPlayer;
    private boolean isRunning;
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
        mDisposables.add(RxBusPrayerTime.subscribe(o -> {
            mTimePrayer = (TimePrayer) o;
            mMillis = PrayerTimeUtils.convertTimeStringToMilliSeconds(mTimePrayer);
        }));

        mDisposables.add(RxAlarmTimeInMinutes.subscribe(o -> mAlarmTimeMinutes = (AlarmTime) o));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String getAlarmStatus = intent.getStringExtra(ALARM_STATUS);

        createNotification();

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
            playSound();
            this.isRunning = true;

            //if there is alarm sound and the user pressed alarm "off"
            //alarm should stop going off
        } else if (this.isRunning && startId == 0) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            stopForeground(true);
            stopSelf();
            this.isRunning = false;

            startAlarm();

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

    private void createNotification(){
        Intent notificationIntent = new Intent(this.getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationHelper notificationHelper = new NotificationHelper(this);
        Notification notification = notificationHelper.getNotificationBuilder("Fajr Alarm", "Tijd om op te staan", pIntent);
        notificationHelper.getNotificationManager().notify(1, notification);
        startForeground(1, notification);
    }

    public void startAlarm() {
        Intent mIntent = new Intent(this, AlarmBroadcastReceiver.class);
        mIntent.putExtra(ALARM_STATUS, "on");
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(this, 1, mIntent, 0);

        long fajrAlarmTime = mAlarmTimeMinutes.getFajrAlarm() * 60 * 1000;
        long day = 24 * 60 * 60 * 1000;
        long alarmTime = mMillis - (fajrAlarmTime - 60000);
        Date date = new Date();
        long timeInMillisecond = date.getTime();
//        long alarmTime = timeInMillisecond + 60000;
        if (alarmTime < timeInMillisecond) {
            alarmTime += day;
        }

        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, mPendingIntent);

    }

    public void playSound() {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(this, getAlarmUri());
            final AudioManager audioManager = (AudioManager) this
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }

}
