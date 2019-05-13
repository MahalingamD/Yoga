package com.yoga.app.service;

import com.yoga.app.model.CourseDetail;
import com.yoga.app.model.CourseList;
import com.yoga.app.model.Profile;
import com.yoga.app.model.Response;
import com.yoga.app.model.Wallet;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface YogaAPI {

   @POST("login")
   Call<Response> LoginAPI( @Header("X-Device") String aHeader, @QueryMap Map<String, String> params );

   @GET("dashboard")
   Call<Response> DashboardAPI( @HeaderMap Map<String, String> aHeader );

   @GET("register")
   Call<Response> RegisterAPI( @HeaderMap Map<String, String> aHeader );

   @POST("register")
   Call<Response> PutRegisterAPI( @QueryMap Map<String, String> params );

   @POST("verify_otp")
   Call<Response> getVerifyOTP( @QueryMap Map<String, String> params );

   @GET("courses")
   Call<CourseList> getCourseList( @HeaderMap Map<String, String> params );

   @GET("wallet")
   Call<Wallet> getWalletDetails( @HeaderMap Map<String, String> params );

   @POST("feedback")
   Call<Response> putFeedbackDetails( @HeaderMap Map<String, String> params, @Query("feedback_message") String aMsg );

   @GET("course/{id}")
   Call<CourseDetail> getCourseDetail( @Path("id") String user, @HeaderMap Map<String, String> params );

   @GET("profile")
   Call<Profile> getProfileAPI( @HeaderMap Map<String, String> aHeader );


   @Multipart
   @POST("profile")
   Call<Response> upload( @HeaderMap Map<String, String> aHeader,
                          @Part MultipartBody.Part file,
                          @PartMap() HashMap<String, RequestBody> aRequestMap );

   @Multipart
   @POST("photo")
   Call<Response> uploadProfile( @HeaderMap Map<String, String> aHeader,
                                 @Part MultipartBody.Part file );


}
