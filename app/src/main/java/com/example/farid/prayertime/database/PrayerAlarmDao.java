package com.example.farid.prayertime.database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farid.prayertime.model.PrayerAlarmTime;

import java.util.List;

public interface PrayerAlarmDao {
    @Delete
    void deleteAlarmTime(PrayerAlarmTime alarmTime);

    @Update
    void updateAlarmTime(PrayerAlarmTime alarmTime);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlarmTime(PrayerAlarmTime alarmTime);

    @Query("SELECT * FROM prayer_alarm_time")
    LiveData<List<PrayerAlarmTime>> getAllPrayersAlarm();
}
