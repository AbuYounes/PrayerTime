package com.example.farid.prayertime.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

@Entity(tableName = "prayer_alarm_time")
public class PrayerAlarmTime implements Parcelable {
    private int id;
    private String prayerName;
    private boolean isAlarmOn;
    private int minutes;

    public PrayerAlarmTime(){

    }

    public PrayerAlarmTime(int id, String prayerName, boolean isAlarmOn, int minutes) {
        this.id = id;
        this.prayerName = prayerName;
        this.isAlarmOn = isAlarmOn;
        this.minutes = minutes;
    }

    protected PrayerAlarmTime(Parcel in) {
        id = in.readInt();
        prayerName = in.readString();
        isAlarmOn = in.readByte() != 0;
        minutes = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrayerName() {
        return prayerName;
    }

    public void setPrayerName(String prayerName) {
        this.prayerName = prayerName;
    }

    public boolean isAlarmOn() {
        return isAlarmOn;
    }

    public void setAlarmOn(boolean alarmOn) {
        isAlarmOn = alarmOn;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(prayerName);
        dest.writeByte((byte) (isAlarmOn ? 1 : 0));
        dest.writeInt(minutes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PrayerAlarmTime> CREATOR = new Creator<PrayerAlarmTime>() {
        @Override
        public PrayerAlarmTime createFromParcel(Parcel in) {
            return new PrayerAlarmTime(in);
        }

        @Override
        public PrayerAlarmTime[] newArray(int size) {
            return new PrayerAlarmTime[size];
        }
    };

}
