package com.yoga.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CourseList {

    @SerializedName("success")
    private int success;

    @SerializedName("data")
    private Data data;

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getSuccess() {
        return success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return
                "CourseList{" +
                        "data = '" + data + '\'' +
                        ",success = '" + success + '\'' +
                        "}";
    }

    public class Data {

        @SerializedName("about")
        String about;

        @SerializedName("courses")
        private ArrayList<Course> data;

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public void setData(ArrayList<Course> data) {
            this.data = data;
        }

        public ArrayList<Course> getData() {
            return data;
        }
    }
}