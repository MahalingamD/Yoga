package com.yoga.app.service;

import com.yoga.app.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private Retrofit retrofit = null;

    /**
     * This method creates a new instance of the API interface.
     *
     * @return The API interface
     */
    public YogaAPI getAPI() {
        //Set Timeout for retrofit

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BuildConfig.Main_Base_url)
                    .client(getRequestTimeOut())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(YogaAPI.class);
    }

    private OkHttpClient getRequestTimeOut() {

        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
