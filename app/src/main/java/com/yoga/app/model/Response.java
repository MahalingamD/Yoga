package com.yoga.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


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

   @SerializedName("error")
   private String error;

   @SerializedName("code")
   private String code;

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getError() {
      return error;
   }

   public void setError(String error) {
      this.error = error;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage( String message ) {
      this.message = message;
   }

   public void setData( Data data ) {
      this.data = data;
   }

   public Data getData() {
      return data;
   }

   public void setSuccess( Integer success ) {
      this.success = success;
   }

   public Integer getSuccess() {
      return success;
   }


}