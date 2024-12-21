package com.deificindia.indifun.pojo.searchuserpojo;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("whatsup")
	private Object whatsup;

	@SerializedName("country")
	private Object country;

	@SerializedName("gender")
	private String gender;

	@SerializedName("sexuality")
	private Object sexuality;

	@SerializedName("city")
	private Object city;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("registered")
	private Object registered;

	@SerializedName("language")
	private Object language;

	@SerializedName("point")
	private String point;

	@SerializedName("uid")
	private String uid;

	@SerializedName("otp_verified")
	private String otpVerified;

	@SerializedName("credits")
	private Object credits;

	@SerializedName("contact")
	private String contact;

	@SerializedName("id")
	private String id;

	@SerializedName("state")
	private String state;

	@SerializedName("relationship")
	private String relationship;

	@SerializedName("email")
	private Object email;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("height")
	private Object height;

	@SerializedName("languages")
	private String languages;

	@SerializedName("last_login")
	private Object lastLogin;

	@SerializedName("ip")
	private Object ip;

	@SerializedName("weight")
	private Object weight;

	@SerializedName("profile_picture")
	private String profilePicture;

	@SerializedName("otp")
	private String otp;

	@SerializedName("is_verified")
	private String isVerified;

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("last_encounter")
	private Object lastEncounter;

	@SerializedName("fb_id")
	private String fbId;

	@SerializedName("dob")
	private String dob;

	@SerializedName("last_active")
	private Object lastActive;

	@SerializedName("age")
	private String age;

	@SerializedName("status")
	private String status;

	public void setWhatsup(Object whatsup){
		this.whatsup = whatsup;
	}

	public Object getWhatsup(){
		return whatsup;
	}

	public void setCountry(Object country){
		this.country = country;
	}

	public Object getCountry(){
		return country;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setSexuality(Object sexuality){
		this.sexuality = sexuality;
	}

	public Object getSexuality(){
		return sexuality;
	}

	public void setCity(Object city){
		this.city = city;
	}

	public Object getCity(){
		return city;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setRegistered(Object registered){
		this.registered = registered;
	}

	public Object getRegistered(){
		return registered;
	}

	public void setLanguage(Object language){
		this.language = language;
	}

	public Object getLanguage(){
		return language;
	}

	public void setPoint(String point){
		this.point = point;
	}

	public String getPoint(){
		return point;
	}

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}

	public void setOtpVerified(String otpVerified){
		this.otpVerified = otpVerified;
	}

	public String getOtpVerified(){
		return otpVerified;
	}

	public void setCredits(Object credits){
		this.credits = credits;
	}

	public Object getCredits(){
		return credits;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getContact(){
		return contact;
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

	public void setRelationship(String relationship){
		this.relationship = relationship;
	}

	public String getRelationship(){
		return relationship;
	}

	public void setEmail(Object email){
		this.email = email;
	}

	public Object getEmail(){
		return email;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	public void setHeight(Object height){
		this.height = height;
	}

	public Object getHeight(){
		return height;
	}

	public void setLanguages(String languages){
		this.languages = languages;
	}

	public String getLanguages(){
		return languages;
	}

	public void setLastLogin(Object lastLogin){
		this.lastLogin = lastLogin;
	}

	public Object getLastLogin(){
		return lastLogin;
	}

	public void setIp(Object ip){
		this.ip = ip;
	}

	public Object getIp(){
		return ip;
	}

	public void setWeight(Object weight){
		this.weight = weight;
	}

	public Object getWeight(){
		return weight;
	}

	public void setProfilePicture(String profilePicture){
		this.profilePicture = profilePicture;
	}

	public String getProfilePicture(){
		return profilePicture;
	}

	public void setOtp(String otp){
		this.otp = otp;
	}

	public String getOtp(){
		return otp;
	}

	public void setIsVerified(String isVerified){
		this.isVerified = isVerified;
	}

	public String getIsVerified(){
		return isVerified;
	}

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
	}

	public void setLastEncounter(Object lastEncounter){
		this.lastEncounter = lastEncounter;
	}

	public Object getLastEncounter(){
		return lastEncounter;
	}

	public void setFbId(String fbId){
		this.fbId = fbId;
	}

	public String getFbId(){
		return fbId;
	}

	public void setDob(String dob){
		this.dob = dob;
	}

	public String getDob(){
		return dob;
	}

	public void setLastActive(Object lastActive){
		this.lastActive = lastActive;
	}

	public Object getLastActive(){
		return lastActive;
	}

	public void setAge(String age){
		this.age = age;
	}

	public String getAge(){
		return age;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"whatsup = '" + whatsup + '\'' + 
			",country = '" + country + '\'' + 
			",gender = '" + gender + '\'' + 
			",sexuality = '" + sexuality + '\'' + 
			",city = '" + city + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",registered = '" + registered + '\'' + 
			",language = '" + language + '\'' + 
			",point = '" + point + '\'' + 
			",uid = '" + uid + '\'' + 
			",otp_verified = '" + otpVerified + '\'' + 
			",credits = '" + credits + '\'' + 
			",contact = '" + contact + '\'' + 
			",id = '" + id + '\'' + 
			",state = '" + state + '\'' + 
			",relationship = '" + relationship + '\'' + 
			",email = '" + email + '\'' + 
			",longitude = '" + longitude + '\'' + 
			",height = '" + height + '\'' + 
			",languages = '" + languages + '\'' + 
			",last_login = '" + lastLogin + '\'' + 
			",ip = '" + ip + '\'' + 
			",weight = '" + weight + '\'' + 
			",profile_picture = '" + profilePicture + '\'' + 
			",otp = '" + otp + '\'' + 
			",is_verified = '" + isVerified + '\'' + 
			",full_name = '" + fullName + '\'' + 
			",last_encounter = '" + lastEncounter + '\'' + 
			",fb_id = '" + fbId + '\'' + 
			",dob = '" + dob + '\'' + 
			",last_active = '" + lastActive + '\'' + 
			",age = '" + age + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}