package com.deificindia.indifun.pojo.rechargemedal;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("medal_subcategory")
	private String medalSubcategory;

	@SerializedName("achievement")
	private String achievement;

	@SerializedName("medal_category")
	private String medalCategory;

	@SerializedName("progress_limit")
	private String progressLimit;

	@SerializedName("id")
	private String id;

	@SerializedName("entry_date")
	private String entryDate;

	public void setMedalSubcategory(String medalSubcategory){
		this.medalSubcategory = medalSubcategory;
	}

	public String getMedalSubcategory(){
		return medalSubcategory;
	}

	public void setAchievement(String achievement){
		this.achievement = achievement;
	}

	public String getAchievement(){
		return achievement;
	}

	public void setMedalCategory(String medalCategory){
		this.medalCategory = medalCategory;
	}

	public String getMedalCategory(){
		return medalCategory;
	}

	public void setProgressLimit(String progressLimit){
		this.progressLimit = progressLimit;
	}

	public String getProgressLimit(){
		return progressLimit;
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
			"medal_subcategory = '" + medalSubcategory + '\'' + 
			",achievement = '" + achievement + '\'' + 
			",medal_category = '" + medalCategory + '\'' + 
			",progress_limit = '" + progressLimit + '\'' + 
			",id = '" + id + '\'' + 
			",entry_date = '" + entryDate + '\'' + 
			"}";
		}
}