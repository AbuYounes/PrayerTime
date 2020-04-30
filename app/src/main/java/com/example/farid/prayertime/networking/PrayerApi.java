package com.example.farid.prayertime.networking;

import com.example.farid.prayertime.model.TimePrayer;

import retrofit2.Call;
import retrofit2.http.GET;

public  interface PrayerApi {
    //live data
    @GET("prayertimes.php")
    Call<TimePrayer> getPrayerTime();
}
