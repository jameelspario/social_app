package com.deificindia.indifun.pojo.getuserprofilegallerypojo;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("image")
	private String image;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("id")
	private String id;

	@SerializedName("entry_date")
	private String entryDate;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEntryDate(String entryDate){
		this.entryDate = entryDate;
	}

	public String getEntryDate(){
		return entryDate;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"image = '" + image + '\'' + 
			",user_id = '" + userId + '\'' + 
			",id = '" + id + '\'' + 
			",entry_date = '" + entryDate + '\'' + 
			"}";
		}
}