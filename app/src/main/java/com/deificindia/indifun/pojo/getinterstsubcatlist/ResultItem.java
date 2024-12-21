package com.deificindia.indifun.pojo.getinterstsubcatlist;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("intrest_category_id")
	private String intrestCategoryId;

	@SerializedName("id")
	private String id;

	@SerializedName("intrest_subcategory")
	private String intrestSubcategory;

	@SerializedName("intrest_category")
	private String intrestCategory;

	@SerializedName("status")
	private String status;

	@SerializedName("intrest_id")
	private String intrest_id;

	public void setIntrestCategoryId(String intrestCategoryId){
		this.intrestCategoryId = intrestCategoryId;
	}

	public String getIntrestCategoryId(){
		return intrestCategoryId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIntrestSubcategory(String intrestSubcategory){
		this.intrestSubcategory = intrestSubcategory;
	}

	public String getIntrestSubcategory(){
		return intrestSubcategory;
	}

	public void setIntrestCategory(String intrestCategory){
		this.intrestCategory = intrestCategory;
	}

	public String getIntrestCategory(){
		return intrestCategory;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIntrest_id() {
		return intrest_id;
	}

	public void setIntrest_id(String intrest_id) {
		this.intrest_id = intrest_id;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"intrest_category_id = '" + intrestCategoryId + '\'' + 
			",id = '" + id + '\'' + 
			",intrest_subcategory = '" + intrestSubcategory + '\'' + 
			",intrest_category = '" + intrestCategory + '\'' +
					",status = '" + status + '\'' +
					",intrest_id = '" + intrest_id + '\'' +
					"}";
		}
}