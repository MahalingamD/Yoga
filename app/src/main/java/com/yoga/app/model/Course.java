package com.yoga.app.model;

import com.google.gson.annotations.SerializedName;

public class Course {

	@SerializedName("course_desc_benefits")
	private String courseDescBenefits;

	@SerializedName("course_id")
	private int courseId;

	@SerializedName("course_level")
	private String courseLevel;

	@SerializedName("course_desc_name")
	private String courseDescName;

	@SerializedName("course_is_free")
	private int courseIsFree;

	@SerializedName("course_image")
	private String courseImage;

	@SerializedName("course_desc_description")
	private String courseDescDescription;

	@SerializedName("course_desc_about")
	private String courseDescAbout;

	public void setCourseDescBenefits(String courseDescBenefits){
		this.courseDescBenefits = courseDescBenefits;
	}

	public String getCourseDescBenefits(){
		return courseDescBenefits;
	}

	public void setCourseId(int courseId){
		this.courseId = courseId;
	}

	public int getCourseId(){
		return courseId;
	}

	public void setCourseLevel(String courseLevel){
		this.courseLevel = courseLevel;
	}

	public String getCourseLevel(){
		return courseLevel;
	}

	public void setCourseDescName(String courseDescName){
		this.courseDescName = courseDescName;
	}

	public String getCourseDescName(){
		return courseDescName;
	}

	public void setCourseIsFree(int courseIsFree){
		this.courseIsFree = courseIsFree;
	}

	public int getCourseIsFree(){
		return courseIsFree;
	}

	public void setCourseImage(String courseImage){
		this.courseImage = courseImage;
	}

	public String getCourseImage(){
		return courseImage;
	}

	public void setCourseDescDescription(String courseDescDescription){
		this.courseDescDescription = courseDescDescription;
	}

	public String getCourseDescDescription(){
		return courseDescDescription;
	}

	public void setCourseDescAbout(String courseDescAbout){
		this.courseDescAbout = courseDescAbout;
	}

	public String getCourseDescAbout(){
		return courseDescAbout;
	}

	@Override
 	public String toString(){
		return 
			"Course{" +
			"course_desc_benefits = '" + courseDescBenefits + '\'' + 
			",course_id = '" + courseId + '\'' + 
			",course_level = '" + courseLevel + '\'' + 
			",course_desc_name = '" + courseDescName + '\'' + 
			",course_is_free = '" + courseIsFree + '\'' + 
			",course_image = '" + courseImage + '\'' + 
			",course_desc_description = '" + courseDescDescription + '\'' + 
			",course_desc_about = '" + courseDescAbout + '\'' + 
			"}";
		}
}