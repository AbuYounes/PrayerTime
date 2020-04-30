package com.example.farid.prayertime.source;

import androidx.lifecycle.LiveData;


import com.example.farid.prayertime.model.AlarmTime;
import com.example.farid.prayertime.model.TimePrayer;
import com.example.farid.prayertime.source.local.PrayerLocalDataSource;
import com.example.farid.prayertime.source.remote.PrayerRemoteDataSource;

public class PrayerRepository implements IPrayerDataSource {
    private PrayerRemoteDataSource mRemoteDataSource;
    private PrayerLocalDataSource mLocalDataSource;
    private static PrayerRepository mInstance;

    public PrayerRepository(PrayerRemoteDataSource mRemoteDataSource, PrayerLocalDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }

    public PrayerRepository(PrayerRemoteDataSource mRemoteDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
    }

    public PrayerRepository(PrayerLocalDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }


    public static PrayerRepository getInstance(PrayerLocalDataSource localDataSource){
        if(mInstance == null){
            mInstance = new PrayerRepository(localDataSource);
        }
        return mInstance;
    }

    public static PrayerRepository getInstance(PrayerRemoteDataSource remoteDataSource){
        if(mInstance == null){
            mInstance = new PrayerRepository(remoteDataSource);
        }
        return mInstance;
    }

    public static PrayerRepository getInstance(PrayerRemoteDataSource remoteDataSource, PrayerLocalDataSource localDataSource) {
        if (mInstance == null) {
            mInstance = new PrayerRepository(remoteDataSource, localDataSource);
        }
        return mInstance;
    }


    @Override
    public void insert(AlarmTime alarmTime) {
        mLocalDataSource.insert(alarmTime);
    }

    @Override
    public void update(AlarmTime alarmTime) {
        mLocalDataSource.update(alarmTime);
    }

    @Override
    public void delete(AlarmTime alarmTime) {
        mLocalDataSource.delete(alarmTime);
    }

    @Override
    public LiveData<AlarmTime> getAllPrayersAlarm() {
        return mLocalDataSource.getAllPrayersAlarm();
    }


    public LiveData<TimePrayer> getPrayertime() {
        return mRemoteDataSource.getPrayertime();
    }

}
