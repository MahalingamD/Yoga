package com.yoga.app.model;

import com.google.gson.annotations.SerializedName;

public class Wallet{

	@SerializedName("data")
	private WalletData data;

	@SerializedName("success")
	private int success;

	public void setData(WalletData data){
		this.data = data;
	}

	public WalletData getData(){
		return data;
	}

	public void setSuccess(int success){
		this.success = success;
	}

	public int getSuccess(){
		return success;
	}
}