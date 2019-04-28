package com.yoga.app.model;

public class CourseVideos {

    public int id;
    public String title;
    public String min;
    public String description;
    public String video_url;
    public String thumnail_url;
    public boolean paid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getThumnail_url() {
        return thumnail_url;
    }

    public void setThumnail_url(String thumnail_url) {
        this.thumnail_url = thumnail_url;
    }
}
