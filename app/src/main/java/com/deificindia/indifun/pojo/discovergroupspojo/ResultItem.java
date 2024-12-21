package com.deificindia.indifun.pojo.discovergroupspojo;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("category_name")
	private String categoryName;

	@SerializedName("id")
	private String id;

	@SerializedName("entry_date")
	private String entryDate;

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
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
			"category_name = '" + categoryName + '\'' + 
			",id = '" + id + '\'' + 
			",entry_date = '" + entryDate + '\'' + 
			"}";
		}
}