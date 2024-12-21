package com.deificindia.indifun.pojo.stickerspojo;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("cover")
	private String cover;

	@SerializedName("is_premium")
	private String isPremium;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public void setCover(String cover){
		this.cover = cover;
	}

	public String getCover(){
		return cover;
	}

	public void setIsPremium(String isPremium){
		this.isPremium = isPremium;
	}

	public String getIsPremium(){
		return isPremium;
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

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"cover = '" + cover + '\'' + 
			",is_premium = '" + isPremium + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}