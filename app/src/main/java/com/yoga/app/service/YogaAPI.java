package com.yoga.app.service;

import com.yoga.app.model.Response;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

public interface YogaAPI {

    @GET("login")
    Call<Response> LoginAPI(@Header("X-Device") String aPath, @QueryMap Map<String, String> params );

}
