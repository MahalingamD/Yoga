package com.yoga.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class Response {
    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("success")
    @Expose
    private Integer success;

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getSuccess() {
        return success;
    }


}