package com.yoga.app.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SMErrorModel implements Serializable {

    @SerializedName("success")
    String success = "";

    @SerializedName("error")
    public
    String error = "";

    @SerializedName("message")
    public String message = "";

    @SerializedName("code")
    public String code = "";

    /*@SerializedName("data")
    public
    Data aData = null;


    public class Data implements Serializable {
        @SerializedName("code")
        public String code = "";

        @SerializedName("errorCount")
        public String errorCount = "";

        @SerializedName("error_message")
        public String errorMessage = "";

        @SerializedName("message")
        public String message = "";

        @SerializedName("url")
        public String url = "";
    }*/


}