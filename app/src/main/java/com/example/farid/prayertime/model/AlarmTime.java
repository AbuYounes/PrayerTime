package com.example.farid.prayertime.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarm_time")
public class AlarmTime implements Parcelable{
    public final static int ALARM_STATE_UNDEFINED = -1;
    public final static int ALARM_STATE_STOPPED = 0;
    public final static int ALARM_STATE_STARTED = 1;

    public final static int ALARM_ACTION_NONE = -1;
    public final static int ALARM_ACTION_STOP = 0;
    public final static int ALARM_ACTION_START = 1;
    public final static int ALARM_ACTION_SHOW_NOTIFICTION = 2;
    public final static int ALARM_ACTION_DELETE_NOTIFICATION = 3;


    @PrimaryKey
    private int id;
    private int fajrAlarm;
    private int duhrAlarm;
    private int asrAlarm;
    private int maghribAlarm;
    private int ishaaAlarm;

    public int action;
    public int alarmState;

    @Ignore
    public AlarmTime(){
        //empty constructor
    }

    public AlarmTime(int fajrAlarm, int duhrAlarm, int asrAlarm, int maghribAlarm, int ishaaAlarm) {
        this.fajrAlarm = fajrAlarm;
        this.duhrAlarm = duhrAlarm;
        this.asrAlarm = asrAlarm;
        this.maghribAlarm = maghribAlarm;
        this.ishaaAlarm = ishaaAlarm;
    }

    @Ignore
    public AlarmTime(int action, int alarmState) {
        this.action = action;
        this.alarmState = alarmState;
    }

    @Ignore
    public AlarmTime(int action) {
        this.action = action;
    }

    public AlarmTime(Parcel in) {
        id = in.readInt();
        fajrAlarm = in.readInt();
        duhrAlarm = in.readInt();
        asrAlarm = in.readInt();
        maghribAlarm = in.readInt();
        ishaaAlarm = in.readInt();
        action = in.readInt();
        alarmState = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(fajrAlarm);
        dest.writeInt(duhrAlarm);
        dest.writeInt(asrAlarm);
        dest.writeInt(maghribAlarm);
        dest.writeInt(ishaaAlarm);
        dest.writeInt(action);
        dest.writeInt(alarmState);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlarmTime> CREATOR = new Creator<AlarmTime>() {
        @Override
        public AlarmTime createFromParcel(Parcel in) {
            return new AlarmTime(in);
        }

        @Override
        public AlarmTime[] newArray(int size) {
            return new AlarmTime[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getFajrAlarm() {
        return fajrAlarm;
    }

    public int getDuhrAlarm() {
        return duhrAlarm;
    }

    public int getAsrAlarm() {
        return asrAlarm;
    }

    public int getMaghribAlarm() {
        return maghribAlarm;
    }

    public int getIshaaAlarm() {
        return ishaaAlarm;
    }

    public void setFajrAlarm(int fajrAlarm) {
        this.fajrAlarm = fajrAlarm;
    }

    public void setDuhrAlarm(int duhrAlarm) {
        this.duhrAlarm = duhrAlarm;
    }

    public void setAsrAlarm(int asrAlarm) {
        this.asrAlarm = asrAlarm;
    }

    public void setMaghribAlarm(int maghribAlarm) {
        this.maghribAlarm = maghribAlarm;
    }

    public void setIshaaAlarm(int ishaaAlarm) {
        this.ishaaAlarm = ishaaAlarm;
    }


}
