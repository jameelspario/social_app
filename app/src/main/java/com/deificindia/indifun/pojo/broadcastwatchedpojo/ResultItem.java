package com.deificindia.indifun.pojo.broadcastwatchedpojo;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("entry_time")
	private String entryTime;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_image")
	private String userImage;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("broadcast_id")
	private String broadcastId;

	@SerializedName("is_live")
	private String isLive;

	@SerializedName("action")
	private String action;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("entry_date")
	private String entryDate;

	public void setEntryTime(String entryTime){
		this.entryTime = entryTime;
	}

	public String getEntryTime(){
		return entryTime;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserImage(String userImage){
		this.userImage = userImage;
	}

	public String getUserImage(){
		return userImage;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setBroadcastId(String broadcastId){
		this.broadcastId = broadcastId;
	}

	public String getBroadcastId(){
		return broadcastId;
	}

	public void setIsLive(String isLive){
		this.isLive = isLive;
	}

	public String getIsLive(){
		return isLive;
	}

	public void setAction(String action){
		this.action = action;
	}

	public String getAction(){
		return action;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
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
			"entry_time = '" + entryTime + '\'' + 
			",user_id = '" + userId + '\'' + 
			",user_image = '" + userImage + '\'' + 
			",user_name = '" + userName + '\'' + 
			",broadcast_id = '" + broadcastId + '\'' + 
			",is_live = '" + isLive + '\'' + 
			",action = '" + action + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",entry_date = '" + entryDate + '\'' + 
			"}";
		}
}