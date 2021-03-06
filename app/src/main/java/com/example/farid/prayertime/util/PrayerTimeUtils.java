package com.example.farid.prayertime.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.farid.prayertime.model.AlarmTime;
import com.example.farid.prayertime.rxbus.RxBusAlarmAction;
import com.example.farid.prayertime.model.TimePrayer;

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

    //Convert alarm time to epoch milliseconds
    public static long convertTimeStringToMilliSeconds(TimePrayer timePrayer){
        long millis = 0;
        String dateString = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String dateTime = dateString + " "+ timePrayer.getFajr()+":00";

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


    //Open the AlarmFragment when clicking on alarm notification
    public static void saveFragState(Context context, boolean alarmState){
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREF_FRAGMENT, MODE_PRIVATE).edit();
        editor.putBoolean(ALARM_STATUS, alarmState);
        editor.apply();
    }

    //TODO open the notification only when the app is killed or in background
    public static void toggleNotification(boolean show) {
        AlarmTime alarmTime;
        if (show) {
            alarmTime = new AlarmTime(ALARM_ACTION_SHOW_NOTIFICTION);
        } else {
            alarmTime = new AlarmTime(ALARM_ACTION_DELETE_NOTIFICATION);
        }
        RxBusAlarmAction.publish(alarmTime);
    }
}
