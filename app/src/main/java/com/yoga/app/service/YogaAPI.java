package com.yoga.app.service;

import com.yoga.app.model.CourseDetail;
import com.yoga.app.model.CourseList;
import com.yoga.app.model.Response;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface YogaAPI {

    @POST("login")
    Call<Response> LoginAPI(@Header("X-Device") String aHeader, @QueryMap Map<String, String> params);

    @GET("dashboard")
    Call<Response> DashboardAPI(@HeaderMap Map<String, String> aHeader);

    @GET("register")
    Call<Response> RegisterAPI(@HeaderMap Map<String, String> aHeader);

    @POST("register")
    Call<Response> PutRegisterAPI(@QueryMap Map<String, String> params);

    @POST("verify_otp")
    Call<Response> getVerifyOTP(@QueryMap Map<String, String> params);

    @GET("courses")
    Call<CourseList> getCourseList(@HeaderMap Map<String, String> params);

    @GET("course/{id}")
    Call<CourseDetail> getCourseDetail(@Path("id") String user, @HeaderMap Map<String, String> params);

}
