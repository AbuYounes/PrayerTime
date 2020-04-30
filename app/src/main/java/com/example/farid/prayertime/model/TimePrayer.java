package com.example.farid.prayertime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimePrayer implements Parcelable {
    @SerializedName("arDate")
    @Expose
    private String arDate;
    @SerializedName("nlDate")
    @Expose
    private String nlDate;
    @SerializedName("fajr")
    @Expose
    private String fajr;
    @SerializedName("shoeroeq")
    @Expose
    private String shoeroeq;
    @SerializedName("dohr")
    @Expose
    private String dohr;
    @SerializedName("asr")
    @Expose
    private String asr;
    @SerializedName("maghrib")
    @Expose
    private String maghrib;
    @SerializedName("isha")
    @Expose
    private String isha;

    public int action;
    public int alarmState;

    public TimePrayer(String arDate, String nlDate, String fajr, String shoeroeq,
                      String dohr, String asr, String maghrib, String isha) {
        this.arDate = arDate;
        this.nlDate = nlDate;
        this.fajr = fajr;
        this.shoeroeq = shoeroeq;
        this.dohr = dohr;
        this.asr = asr;
        this.maghrib = maghrib;
        this.isha = isha;
    }



    public TimePrayer(int action) {
        this.action = action;
    }

    public TimePrayer(Parcel in) {
        arDate = in.readString();
        nlDate = in.readString();
        fajr = in.readString();
        shoeroeq = in.readString();
        dohr = in.readString();
        asr = in.readString();
        maghrib = in.readString();
        isha = in.readString();
    }

    public static final Creator<TimePrayer> CREATOR = new Creator<TimePrayer>() {
        @Override
        public TimePrayer createFromParcel(Parcel in) {
            return new TimePrayer(in);
        }

        @Override
        public TimePrayer[] newArray(int size) {
            return new TimePrayer[size];
        }
    };

    public String getArDate() {
        return arDate;
    }

    public void setArDate(String arDate) {
        this.arDate = arDate;
    }

    public String getNlDate() {
        return nlDate;
    }

    public void setNlDate(String nlDate) {
        this.nlDate = nlDate;
    }

    public String getFajr() {
        return fajr;
    }

    public void setFajr(String fajr) {
        this.fajr = fajr;
    }

    public String getShoeroeq() {
        return shoeroeq;
    }

    public void setShoeroeq(String shoeroeq) {
        this.shoeroeq = shoeroeq;
    }

    public String getDohr() {
        return dohr;
    }

    public void setDohr(String dohr) {
        this.dohr = dohr;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }

    public String getIsha() {
        return isha;
    }

    public void setIsha(String isha) {
        this.isha = isha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(arDate);
        dest.writeString(nlDate);
        dest.writeString(fajr);
        dest.writeString(shoeroeq);
        dest.writeString(dohr);
        dest.writeString(asr);
        dest.writeString(maghrib);
        dest.writeString(isha);
    }
}
