package com.example.farid.prayertime.networking;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.farid.prayertime.Constants.BASE_URL;

public class PrayerClient {
    private static Retrofit retrofit = null;
    //builds the url and converts json into java object via gson-converter
    public static Retrofit getClient() {
        if (retrofit == null) {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(logging)
//                    .build();

            retrofit = new Retrofit.Builder()
                    //get baseurl from yasir
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
