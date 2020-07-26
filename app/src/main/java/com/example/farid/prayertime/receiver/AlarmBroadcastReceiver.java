package com.example.farid.prayertime.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.farid.prayertime.service.AlarmSoundPlayingService;
import com.example.farid.prayertime.util.PrayerTimeUtils;

import static com.example.farid.prayertime.Constants.ALARM_STATUS;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String getAlarmStatus="";
        if(intent.getStringExtra(ALARM_STATUS)!=null){
            getAlarmStatus = intent.getStringExtra(ALARM_STATUS);
        }

        Intent serviceIntent = new Intent(context,  AlarmSoundPlayingService.class);

        serviceIntent.putExtra(ALARM_STATUS, getAlarmStatus);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PrayerTimeUtils.saveFragState(context, true);
            context.startForegroundService(serviceIntent);
        } else {
            PrayerTimeUtils.saveFragState(context, true);
            context.startService(serviceIntent);
        }


    }
}
