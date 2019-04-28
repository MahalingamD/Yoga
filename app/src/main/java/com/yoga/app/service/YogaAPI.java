package com.yoga.app.service;

import com.yoga.app.model.Response;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface YogaAPI {

    @POST("login")
    Call<Response> LoginAPI(@Header("X-Device") String aHeader, @QueryMap Map<String, String> params);

    @GET("dashboard")
    Call<Response> DashboardAPI(@HeaderMap Map<String, String> aHeader);

}
