package com.deificindia.indifun.pojo.onlinecontact;

import com.google.gson.annotations.SerializedName;

public class OnlinecontactResponse{

	@SerializedName("result")
	private Result result;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"OnlinecontactResponse{" + 
			"result = '" + result + '\'' + 
			"}";
		}
}