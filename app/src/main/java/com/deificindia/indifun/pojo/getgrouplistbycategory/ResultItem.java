package com.deificindia.indifun.pojo.getgrouplistbycategory;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("group_category")
	private String groupCategory;

	@SerializedName("group_name")
	private String groupName;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private String id;

	@SerializedName("made_by")
	private String madeBy;

	@SerializedName("entry_date")
	private String entryDate;

	public void setGroupCategory(String groupCategory){
		this.groupCategory = groupCategory;
	}

	public String getGroupCategory(){
		return groupCategory;
	}

	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	public String getGroupName(){
		return groupName;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setMadeBy(String madeBy){
		this.madeBy = madeBy;
	}

	public String getMadeBy(){
		return madeBy;
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
			"group_category = '" + groupCategory + '\'' + 
			",group_name = '" + groupName + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",made_by = '" + madeBy + '\'' + 
			",entry_date = '" + entryDate + '\'' + 
			"}";
		}
}