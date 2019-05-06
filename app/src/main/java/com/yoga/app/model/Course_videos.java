package com.yoga.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Awesome Pojo Generator
 */
public class Course_videos implements Serializable {
    @SerializedName("cvideo_is_free")
    @Expose
    private Integer cvideo_is_free;
    @SerializedName("cvideo_thumbnail")
    @Expose
    private String cvideo_thumbnail;
    @SerializedName("cvideo_desc_title")
    @Expose
    private String cvideo_desc_title;
    @SerializedName("cvideo_id")
    @Expose
    private Integer cvideo_id;
    @SerializedName("cvideo_video")
    @Expose
    private String cvideo_video;
    @SerializedName("cvideo_desc_description")
    @Expose
    private String cvideo_desc_description;

    public void setCvideo_is_free(Integer cvideo_is_free) {
        this.cvideo_is_free = cvideo_is_free;
    }

    public Integer getCvideo_is_free() {
        return cvideo_is_free;
    }

    public void setCvideo_thumbnail(String cvideo_thumbnail) {
        this.cvideo_thumbnail = cvideo_thumbnail;
    }

    public String getCvideo_thumbnail() {
        return cvideo_thumbnail;
    }

    public void setCvideo_desc_title(String cvideo_desc_title) {
        this.cvideo_desc_title = cvideo_desc_title;
    }

    public String getCvideo_desc_title() {
        return cvideo_desc_title;
    }

    public void setCvideo_id(Integer cvideo_id) {
        this.cvideo_id = cvideo_id;
    }

    public Integer getCvideo_id() {
        return cvideo_id;
    }

    public void setCvideo_video(String cvideo_video) {
        this.cvideo_video = cvideo_video;
    }

    public String getCvideo_video() {
        return cvideo_video;
    }

    public String getCvideo_desc_description() {
        return cvideo_desc_description;
    }

    public void setCvideo_desc_description(String cvideo_desc_description) {
        this.cvideo_desc_description = cvideo_desc_description;
    }
}