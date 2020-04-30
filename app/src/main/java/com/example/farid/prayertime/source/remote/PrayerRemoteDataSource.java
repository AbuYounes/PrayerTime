package com.example.farid.prayertime.source.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.farid.prayertime.model.TimePrayer;
import com.example.farid.prayertime.networking.PrayerApi;
import com.example.farid.prayertime.networking.PrayerClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrayerRemoteDataSource {

    private static PrayerRemoteDataSource mInstance;
    public MutableLiveData<TimePrayer> data;

    public PrayerRemoteDataSource() {

    }

    public static PrayerRemoteDataSource getInstance() {
        if (mInstance == null) {
            mInstance = new PrayerRemoteDataSource();
        }
        return mInstance;
    }

    public LiveData<TimePrayer> getPrayertime() {
        data = new MutableLiveData<>();
        PrayerApi apiService = PrayerClient.getClient().create(PrayerApi.class);
        Call<TimePrayer> call = apiService.getPrayerTime();

        call.enqueue(new Callback<TimePrayer>() {
            @Override
            public void onResponse(Call<TimePrayer> call, Response<TimePrayer> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TimePrayer> call, Throwable t) {

            }
        });
        return data;
    }
}
