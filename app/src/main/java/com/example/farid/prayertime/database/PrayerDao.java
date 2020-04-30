package com.example.farid.prayertime.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farid.prayertime.model.AlarmTime;

@Dao
public interface PrayerDao {

    @Delete
    void deleteAlarmTime(AlarmTime alarmTime);

    @Update
    void updateAlarmTime(AlarmTime alarmTime);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAlarmTime(AlarmTime alarmTime);

    @Query("SELECT * FROM alarm_time")
    LiveData<AlarmTime>getAllPrayersAlarm();

}
