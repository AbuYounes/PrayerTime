package com.example.farid.prayertime.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import com.example.farid.prayertime.model.AlarmTime;
import com.example.farid.prayertime.receiver.AlarmBroadcastReceiver;
import com.example.farid.prayertime.rxbus.RxBusAlarmAction;
import com.example.farid.prayertime.model.TimePrayer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static com.example.farid.prayertime.Constants.ALARM_STATUS;
import static com.example.farid.prayertime.Constants.SHARED_PREF_FRAGMENT;
import static com.example.farid.prayertime.model.AlarmTime.ALARM_ACTION_DELETE_NOTIFICATION;
import static com.example.farid.prayertime.model.AlarmTime.ALARM_ACTION_SHOW_NOTIFICTION;

public class PrayerTimeUtils {

    public static long convertTimeStringToMilliSeconds(TimePrayer timePrayer){
        long millis = 0;
        String dateString = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String dateTime = dateString + " "+"13:59:00"; //timePrayer.getIsha()+"

        /*
          With this new Date/Time API, when using a date, you need to
          specify the Zone where the date/time will be used. For your case,
          seems that you want/need to use the default zone of your system.
          Check which zone you need to use for specific behaviour e.g.
          CET or America/Lima
        */
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime,
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss") );
            millis = localDateTime
                    .atZone(ZoneId.systemDefault())
                    .toInstant().toEpochMilli();
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date;
            try {
                date = sdf.parse(dateTime);
                millis = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return millis;
    }

    public static void toggleNotification(boolean show) {
        AlarmTime alarmTime;
        if (show) {
            alarmTime = new AlarmTime(ALARM_ACTION_SHOW_NOTIFICTION);
        } else {
            alarmTime = new AlarmTime(ALARM_ACTION_DELETE_NOTIFICATION);
        }
        RxBusAlarmAction.publish(alarmTime);
    }

    public static void saveFragState(Context context, boolean alarmState){
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_FRAGMENT, MODE_PRIVATE).edit();
        editor.putBoolean(ALARM_STATUS, alarmState);
        editor.apply();
    }


    public static void playSound(Context context, MediaPlayer mediaPlayer) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, getAlarmUri());
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private static Uri getAlarmUri() {
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

    public static void startAlarm(Context context, AlarmManager alarmManager, AlarmTime alarmTimeMinutes, long millis) {
        Intent mIntent = new Intent(context, AlarmBroadcastReceiver.class);
        mIntent.putExtra(ALARM_STATUS, "on");
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(context, 1, mIntent, 0);

        long fajrAlarmTime = alarmTimeMinutes.getFajrAlarm() * 60 * 1000;
        long day = 24 * 60 * 60 * 1000;
        long alarmTime = millis - (fajrAlarmTime - 60000);
        Date date = new Date();
        long timeInMillisecond = date.getTime();

        if (alarmTime < timeInMillisecond) {
            alarmTime += day;
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, mPendingIntent);

    }
}
