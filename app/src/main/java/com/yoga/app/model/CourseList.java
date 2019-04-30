package com.yoga.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CourseList {

    @SerializedName("data")
    private ArrayList<Course> data;

    @SerializedName("success")
    private int success;

    public void setData(ArrayList<Course> data) {
        this.data = data;
    }

    public ArrayList<Course> getData() {
        return data;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return
                "CourseList{" +
                        "data = '" + data + '\'' +
                        ",success = '" + success + '\'' +
                        "}";
    }
}