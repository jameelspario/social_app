package com.deificindia.indifun.pojo.userlangpojo;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("id")
	private String id;

	@SerializedName("language_id")
	private String languageId;

	@SerializedName("language_name")
	private String languageName;

	@SerializedName("selected")
	private boolean selected;

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

	public void setLanguageId(String languageId){
		this.languageId = languageId;
	}

	public String getLanguageId(){
		return languageId;
	}

	public void setLanguageName(String languageName){
		this.languageName = languageName;
	}

	public String getLanguageName(){
		return languageName;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"user_id = '" + userId + '\'' + 
			",id = '" + id + '\'' + 
			",language_id = '" + languageId + '\'' + 
			",language_name = '" + languageName + '\'' + 
			"}";
		}
}