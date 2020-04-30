package com.example.farid.prayertime.source.local;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.farid.prayertime.database.PrayerDao;
import com.example.farid.prayertime.model.AlarmTime;

public class PrayerLocalDataSource {
    private PrayerDao prayerDao;
    private LiveData<AlarmTime> allPrayersAlarm;

    private static PrayerLocalDataSource mInstance;

    public static PrayerLocalDataSource getInstance(PrayerDao prayerDao) {
        if (mInstance == null) {
            mInstance = new PrayerLocalDataSource(prayerDao);
        }
        return mInstance;
    }

    public PrayerLocalDataSource(PrayerDao prayerDao) {
        this.prayerDao = prayerDao;
        this.allPrayersAlarm = prayerDao.getAllPrayersAlarm();
    }

    public void insert(AlarmTime alarmTime){
        new InsertPrayerAsyncTask(prayerDao).execute(alarmTime);
    }

    public void delete(AlarmTime alarmTime){
        new DeletePrayerAsyncTask(prayerDao).execute(alarmTime);
    }

    public void update(AlarmTime alarmTime){
        new UpdatePrayerAsyncTask(prayerDao).execute(alarmTime);
    }

    public LiveData<AlarmTime> getAllPrayersAlarm (){

        return allPrayersAlarm;
    }

    public static class InsertPrayerAsyncTask extends AsyncTask<AlarmTime, Void, Void> {
        private PrayerDao prayerDao;

        private InsertPrayerAsyncTask(PrayerDao prayerDao){
            this.prayerDao = prayerDao;
        }

        @Override
        protected Void doInBackground(AlarmTime... alarmTimes) {
            prayerDao.insertAlarmTime(alarmTimes[0]);
            return null;
        }
    }

    public static class UpdatePrayerAsyncTask extends AsyncTask<AlarmTime, Void, Void>{
        private PrayerDao prayerDao;

        private UpdatePrayerAsyncTask(PrayerDao prayerDao){
            this.prayerDao = prayerDao;
        }

        @Override
        protected Void doInBackground(AlarmTime... alarmTimes) {
            prayerDao.updateAlarmTime(alarmTimes[0]);
            return null;
        }
    }

    public static class DeletePrayerAsyncTask extends AsyncTask<AlarmTime, Void, Void>{
        private PrayerDao prayerDao;

        private DeletePrayerAsyncTask(PrayerDao prayerDao){
            this.prayerDao = prayerDao;
        }

        @Override
        protected Void doInBackground(AlarmTime... alarmTimes) {
            prayerDao.deleteAlarmTime(alarmTimes[0]);
            return null;
        }
    }
}
