package com.deificindia.indifun.pojo.alllangaugespojo;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("language")
	private String language;

	@SerializedName("id")
	private String id;

	@SerializedName("status")
	private String status;

	@SerializedName("selected")
	private boolean selected;

	@SerializedName("language_id")
	private String language_id;


	public void setLanguage(String language){
		this.language = language;
	}

	public String getLanguage(){
		return language;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(String language_id) {
		this.language_id = language_id;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"language = '" + language + '\'' + 
			",id = '" + id + '\'' +
					",status = '" + status + '\'' +
					",language_id = '" + language_id + '\'' +
					"}";
		}
}