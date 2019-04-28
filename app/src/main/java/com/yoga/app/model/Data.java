package com.yoga.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Data implements Serializable {
    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("token_type")
    @Expose
    private String token_type;
    @SerializedName("expires_in")
    @Expose
    private String expires_in;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getExpires_in() {
        return expires_in;
    }

    @SerializedName("account_id")
    public String account_id = "";

    @SerializedName("otp")
    public String otp = "";


    @SerializedName("banner")
    private List<Banner> mBannerList;

    public List<Banner> getmBannerList() {
        return mBannerList;
    }

    public void setmBannerList(List<Banner> mBannerList) {
        this.mBannerList = mBannerList;
    }


    @SerializedName("daily_quotes")
    private DailyQuotes mDailyQuotes;

    public DailyQuotes getDailyQuotes() {
        return mDailyQuotes;
    }

    public void setDailyQuotes(DailyQuotes mDailyQuotes) {
        this.mDailyQuotes = mDailyQuotes;
    }

    @SerializedName("gender")
    private List<Gender> mGenderList;

    public List<Gender> getmGenderList() {
        return mGenderList;
    }

    public void setmGenderList(List<Gender> mGenderList) {
        this.mGenderList = mGenderList;
    }

    @SerializedName("health_disorder")
    private List<HealthDisorder> mDisorderList;

    public List<HealthDisorder> getmDisorderList() {
        return mDisorderList;
    }

    public void setmDisorderList(List<HealthDisorder> mDisorderList) {
        this.mDisorderList = mDisorderList;
    }

    @SerializedName("profession")
    private List<Profession> mProfessionList;

    public List<Profession> getmProfessionList() {
        return mProfessionList;
    }

    public void setmProfessionList(List<Profession> mProfessionList) {
        this.mProfessionList = mProfessionList;
    }

    @SerializedName("country")
    private List<Country> mCountryList;

    public List<Country> getmCountryList() {
        return mCountryList;
    }

    public void setmCountryList(List<Country> mCountryList) {
        this.mCountryList = mCountryList;
    }
}