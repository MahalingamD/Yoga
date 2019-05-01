package com.yoga.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class CourseDetail {

    @SerializedName("data")
    private Course data;

    @SerializedName("success")
    private int success;

    public void setData(Course data) {
        this.data = data;
    }

    public Course getData() {
        return data;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getSuccess() {
        return success;
    }
}