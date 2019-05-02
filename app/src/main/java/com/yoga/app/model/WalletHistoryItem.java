package com.yoga.app.model;

import com.google.gson.annotations.SerializedName;

public class WalletHistoryItem{

	@SerializedName("whistory_mode_name")
	private String whistoryModeName;

	@SerializedName("whistory_type_name")
	private String whistoryTypeName;

	@SerializedName("whistory_id")
	private int whistoryId;

	@SerializedName("whistory_mode")
	private int whistoryMode;

	@SerializedName("whistory_type")
	private int whistoryType;

	@SerializedName("whistory_created_on")
	private String whistoryCreatedOn;

	@SerializedName("whistory_coins")
	private int whistoryCoins;

	public void setWhistoryModeName(String whistoryModeName){
		this.whistoryModeName = whistoryModeName;
	}

	public String getWhistoryModeName(){
		return whistoryModeName;
	}

	public void setWhistoryTypeName(String whistoryTypeName){
		this.whistoryTypeName = whistoryTypeName;
	}

	public String getWhistoryTypeName(){
		return whistoryTypeName;
	}

	public void setWhistoryId(int whistoryId){
		this.whistoryId = whistoryId;
	}

	public int getWhistoryId(){
		return whistoryId;
	}

	public void setWhistoryMode(int whistoryMode){
		this.whistoryMode = whistoryMode;
	}

	public int getWhistoryMode(){
		return whistoryMode;
	}

	public void setWhistoryType(int whistoryType){
		this.whistoryType = whistoryType;
	}

	public int getWhistoryType(){
		return whistoryType;
	}

	public void setWhistoryCreatedOn(String whistoryCreatedOn){
		this.whistoryCreatedOn = whistoryCreatedOn;
	}

	public String getWhistoryCreatedOn(){
		return whistoryCreatedOn;
	}

	public void setWhistoryCoins(int whistoryCoins){
		this.whistoryCoins = whistoryCoins;
	}

	public int getWhistoryCoins(){
		return whistoryCoins;
	}
}