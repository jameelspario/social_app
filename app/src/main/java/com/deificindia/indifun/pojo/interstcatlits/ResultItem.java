package com.deificindia.indifun.pojo.interstcatlits;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("id")
	private String id;

	@SerializedName("intrest_category")
	private String intrestCategory;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIntrestCategory(String intrestCategory){
		this.intrestCategory = intrestCategory;
	}

	public String getIntrestCategory(){
		return intrestCategory;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"id = '" + id + '\'' + 
			",intrest_category = '" + intrestCategory + '\'' + 
			"}";
		}
}