package com.yoga.app.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Response{
  @SerializedName("data")
  @Expose
  private Data data;
  @SerializedName("success")
  @Expose
  private Integer success;
  public void setData(Data data){
   this.data=data;
  }
  public Data getData(){
   return data;
  }
  public void setSuccess(Integer success){
   this.success=success;
  }
  public Integer getSuccess(){
   return success;
  }
}