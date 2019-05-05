package com.yoga.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Profile implements Serializable{

    @SerializedName("data")
    public ProfileData data;

    @SerializedName("success")
    public int success;

    @SerializedName("error")
    public String error;



    public class ProfileData implements Serializable {

        public String account_id = "";
        public String account_email = "";
        public String account_country_code = "";
        public String account_mobile_no = "";
        public String account_wallet_coins = "";
        public String account_lang_id = "";
        public String account_lang_name = "";
        public String account_referral_code = "";
        public String account_created_on = "";
        public String account_name = "";
        public String account_gender = "";
        public String account_gender_name = "";
        public String account_age = "";
        public String account_health_disorder = "";
        public String account_health_disorder_name = "";

        public String account_profession = "";
        public String account_profession_name = "";
        public String account_country = "";
        public String account_country_name = "";
        public String account_photo = "";


    }


}
