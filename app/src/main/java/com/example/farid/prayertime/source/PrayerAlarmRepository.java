package com.example.farid.prayertime.source;

import androidx.lifecycle.LiveData;

import com.example.farid.prayertime.model.PrayerAlarmTime;
import com.example.farid.prayertime.source.local.PrayerAlarmLocalDataSource;

import java.util.List;

public class PrayerAlarmRepository implements IPrayerAlarmDataSource {

    private PrayerAlarmLocalDataSource prayerAlarmLocalDataSource;
    private static PrayerAlarmRepository mInstance;

    public PrayerAlarmRepository(PrayerAlarmLocalDataSource prayerAlarmLocalDataSource) {
        this.prayerAlarmLocalDataSource = prayerAlarmLocalDataSource;
    }

    public static PrayerAlarmRepository getInstance(PrayerAlarmLocalDataSource prayerAlarmLocalDataSource){
        if(mInstance == null){
            mInstance = new PrayerAlarmRepository(prayerAlarmLocalDataSource);
        }
        return mInstance;
    }

    @Override
    public void insert(PrayerAlarmTime alarmTime) {
        prayerAlarmLocalDataSource.insert(alarmTime);
    }

    @Override
    public void update(PrayerAlarmTime alarmTime) {
        prayerAlarmLocalDataSource.update(alarmTime);
    }

    @Override
    public void delete(PrayerAlarmTime alarmTime) {
        prayerAlarmLocalDataSource.delete(alarmTime);
    }

    @Override
    public LiveData<List<PrayerAlarmTime>> getAllPrayersAlarm() {
        return prayerAlarmLocalDataSource.getAllPrayersAlarm();
    }
}
