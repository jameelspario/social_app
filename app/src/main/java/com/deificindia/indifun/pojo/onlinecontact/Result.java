package com.deificindia.indifun.pojo.onlinecontact;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("owner_name")
	private String ownerName;

	@SerializedName("city")
	private String city;

	@SerializedName("address_1")
	private String address1;

	@SerializedName("created")
	private String created;

	@SerializedName("address_2")
	private String address2;

	@SerializedName("fax_number")
	private String faxNumber;

	@SerializedName("app_name")
	private String appName;

	@SerializedName("email2")
	private String email2;

	@SerializedName("email1")
	private String email1;

	@SerializedName("pin")
	private String pin;

	@SerializedName("nationality")
	private String nationality;

	@SerializedName("mobile_number2")
	private String mobileNumber2;

	@SerializedName("logo")
	private String logo;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("id")
	private String id;

	@SerializedName("state")
	private String state;

	@SerializedName("mobile_number")
	private String mobileNumber;

	public void setOwnerName(String ownerName){
		this.ownerName = ownerName;
	}

	public String getOwnerName(){
		return ownerName;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setAddress1(String address1){
		this.address1 = address1;
	}

	public String getAddress1(){
		return address1;
	}

	public void setCreated(String created){
		this.created = created;
	}

	public String getCreated(){
		return created;
	}

	public void setAddress2(String address2){
		this.address2 = address2;
	}

	public String getAddress2(){
		return address2;
	}

	public void setFaxNumber(String faxNumber){
		this.faxNumber = faxNumber;
	}

	public String getFaxNumber(){
		return faxNumber;
	}

	public void setAppName(String appName){
		this.appName = appName;
	}

	public String getAppName(){
		return appName;
	}

	public void setEmail2(String email2){
		this.email2 = email2;
	}

	public String getEmail2(){
		return email2;
	}

	public void setEmail1(String email1){
		this.email1 = email1;
	}

	public String getEmail1(){
		return email1;
	}

	public void setPin(String pin){
		this.pin = pin;
	}

	public String getPin(){
		return pin;
	}

	public void setNationality(String nationality){
		this.nationality = nationality;
	}

	public String getNationality(){
		return nationality;
	}

	public void setMobileNumber2(String mobileNumber2){
		this.mobileNumber2 = mobileNumber2;
	}

	public String getMobileNumber2(){
		return mobileNumber2;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setMobileNumber(String mobileNumber){
		this.mobileNumber = mobileNumber;
	}

	public String getMobileNumber(){
		return mobileNumber;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"owner_name = '" + ownerName + '\'' + 
			",city = '" + city + '\'' + 
			",address_1 = '" + address1 + '\'' + 
			",created = '" + created + '\'' + 
			",address_2 = '" + address2 + '\'' + 
			",fax_number = '" + faxNumber + '\'' + 
			",app_name = '" + appName + '\'' + 
			",email2 = '" + email2 + '\'' + 
			",email1 = '" + email1 + '\'' + 
			",pin = '" + pin + '\'' + 
			",nationality = '" + nationality + '\'' + 
			",mobile_number2 = '" + mobileNumber2 + '\'' + 
			",logo = '" + logo + '\'' + 
			",phone_number = '" + phoneNumber + '\'' + 
			",id = '" + id + '\'' + 
			",state = '" + state + '\'' + 
			",mobile_number = '" + mobileNumber + '\'' + 
			"}";
		}
}