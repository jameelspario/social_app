package com.deificindia.indifun.pojo.userinterstpojo;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("intrest_id")
	private String intrestId;

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIntrestId(String intrestId){
		this.intrestId = intrestId;
	}

	public String getIntrestId(){
		return intrestId;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"user_id = '" + userId + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",intrest_id = '" + intrestId + '\'' + 
			"}";
		}
}