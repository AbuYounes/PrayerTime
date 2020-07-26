package com.example.farid.prayertime.database;

import android.content.Context;

import androidx.room.Database;;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.farid.prayertime.model.AlarmTime;
import com.example.farid.prayertime.model.PrayerAlarmTime;

@Database(entities = {AlarmTime.class, PrayerAlarmTime.class}, version = 1)
public abstract class AnourDatabase extends RoomDatabase {
    public abstract PrayerDao prayerDao();

    private static AnourDatabase instance;

    public static synchronized AnourDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AnourDatabase.class, "alarm_time_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDBAsyncTask(instance).execute();
//        }
//    };
//
//    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
//        private PrayerDao prayerDao;
//
//        private PopulateDBAsyncTask(AnourDatabase db){
//            prayerDao = db.prayerDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            AlarmTime alarmTime = new AlarmTime(15, 0,0,0,0);
//            prayerDao.insertAlarmTime(alarmTime);
//            return null;
//        }
//    }
}
