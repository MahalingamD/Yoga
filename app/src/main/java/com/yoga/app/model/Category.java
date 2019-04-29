package com.yoga.app.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

   public String title = "";

   @SerializedName("courses")
   public ArrayList<Courses> mCoursesArrayList;


   public class Courses implements Serializable {

      public String course_id = "";
      public String course_level = "";
      public String course_is_free = "";
      public String course_desc_name = "";
      public String course_image = "";
      public String course_desc_about = "";
      public String course_desc_benefits = "";
      public String course_desc_description = "";

   }
}
