package com.example.farid.prayertime.source;

import androidx.lifecycle.LiveData;

import com.example.farid.prayertime.model.PrayerAlarmTime;


import java.util.List;

public interface IPrayerAlarmDataSource {
    void insert(PrayerAlarmTime alarmTime);
    void update(PrayerAlarmTime alarmTime);
    void delete(PrayerAlarmTime alarmTime);

    LiveData<List<PrayerAlarmTime>> getAllPrayersAlarm();

}
