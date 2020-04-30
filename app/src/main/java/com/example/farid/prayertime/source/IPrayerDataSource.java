package com.example.farid.prayertime.source;

import androidx.lifecycle.LiveData;

import com.example.farid.prayertime.model.AlarmTime;
import com.example.farid.prayertime.model.TimePrayer;

public interface IPrayerDataSource {
    void insert(AlarmTime AlarmTime);
    void update(AlarmTime AlarmTime);
    void delete(AlarmTime AlarmTime);

    LiveData<AlarmTime> getAllPrayersAlarm();

    LiveData<TimePrayer> getPrayertime();
}
