package com.example.farid.prayertime.ui;

import android.app.Application;

import com.example.farid.prayertime.database.AnourDatabase;
import com.example.farid.prayertime.model.AlarmTime;
import com.example.farid.prayertime.model.TimePrayer;
import com.example.farid.prayertime.source.PrayerRepository;
import com.example.farid.prayertime.source.local.PrayerLocalDataSource;
import com.example.farid.prayertime.source.remote.PrayerRemoteDataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PrayerViewModel extends AndroidViewModel {
    private PrayerRepository prayerRepository;

    public PrayerViewModel(@NonNull Application application) {
        super(application);
        AnourDatabase anourDatabase = AnourDatabase.getInstance(application);

        prayerRepository = PrayerRepository.getInstance(
                PrayerRemoteDataSource.getInstance(),
                PrayerLocalDataSource.getInstance(anourDatabase.prayerDao())
                );
    }

    public void insert(AlarmTime alarmTime) {
        prayerRepository.insert(alarmTime);
    }

    public void update(AlarmTime alarmTime) {
        prayerRepository.update(alarmTime);
    }

    public void delete(AlarmTime alarmTime) {
        prayerRepository.delete(alarmTime);
    }

    public LiveData<AlarmTime> getAllPrayersAlarm(){
        return prayerRepository.getAllPrayersAlarm();
    }

    public LiveData<TimePrayer> getPrayerTime() {
        return prayerRepository.getPrayertime();
    }

}
