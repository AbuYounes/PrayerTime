package com.example.farid.prayertime.source.local;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.farid.prayertime.database.PrayerAlarmDao;
import com.example.farid.prayertime.model.PrayerAlarmTime;

import java.util.List;

public class PrayerAlarmLocalDataSource {
    private PrayerAlarmDao prayerAlarmDao;
    private LiveData<List<PrayerAlarmTime>> prayerAlarmTimeList;

    private static PrayerAlarmLocalDataSource mInstance;

    public static PrayerAlarmLocalDataSource getInstance(PrayerAlarmDao prayerAlarmDao){
        if(mInstance == null){
            mInstance = new PrayerAlarmLocalDataSource(prayerAlarmDao);
        }
        return mInstance;
    }

    public PrayerAlarmLocalDataSource(PrayerAlarmDao prayerAlarmDao) {
        this.prayerAlarmDao = prayerAlarmDao;
        prayerAlarmTimeList = prayerAlarmDao.getAllPrayersAlarm();
    }

    public void insert(PrayerAlarmTime alarmTime){
        new PrayerAlarmLocalDataSource.InsertPrayerAsyncTask(prayerAlarmDao).execute(alarmTime);
    }

    public void delete(PrayerAlarmTime alarmTime){
        new PrayerAlarmLocalDataSource.DeletePrayerAsyncTask(prayerAlarmDao).execute(alarmTime);
    }

    public void update(PrayerAlarmTime alarmTime){
        new PrayerAlarmLocalDataSource.UpdatePrayerAsyncTask(prayerAlarmDao).execute(alarmTime);
    }

    public LiveData<List<PrayerAlarmTime>> getAllPrayersAlarm (){

        return prayerAlarmTimeList;
    }

    public static class InsertPrayerAsyncTask extends AsyncTask<PrayerAlarmTime, Void, Void> {
        private PrayerAlarmDao prayerAlarmDao;

        private InsertPrayerAsyncTask(PrayerAlarmDao prayerAlarmDao){
            this.prayerAlarmDao = prayerAlarmDao;
        }

        @Override
        protected Void doInBackground(PrayerAlarmTime... alarmTimes) {
            prayerAlarmDao.insertAlarmTime(alarmTimes[0]);
            return null;
        }
    }

    public static class UpdatePrayerAsyncTask extends AsyncTask<PrayerAlarmTime, Void, Void>{
        private PrayerAlarmDao prayerAlarmDao;

        private UpdatePrayerAsyncTask(PrayerAlarmDao prayerAlarmDao){
            this.prayerAlarmDao = prayerAlarmDao;
        }

        @Override
        protected Void doInBackground(PrayerAlarmTime... alarmTimes) {
            prayerAlarmDao.updateAlarmTime(alarmTimes[0]);
            return null;
        }
    }

    public static class DeletePrayerAsyncTask extends AsyncTask<PrayerAlarmTime, Void, Void>{
        private PrayerAlarmDao prayerAlarmDao;

        private DeletePrayerAsyncTask(PrayerAlarmDao prayerAlarmDao){
            this.prayerAlarmDao = prayerAlarmDao;
        }

        @Override
        protected Void doInBackground(PrayerAlarmTime... alarmTimes) {
            prayerAlarmDao.deleteAlarmTime(alarmTimes[0]);
            return null;
        }
    }
}
