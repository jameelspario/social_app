package com.deificindia.indifun.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
"result": {
        "id": "555",
        "fb_id": "",
        "uid": "USER10581",
        "full_name": "jam",
        "email": "spario@gmail.com",
        "contact": "+917007925658",
        "firebase_uid": "",
        "point": "260",
        "broadcast_point": "0",
        "golden_coins": "0",
        "silver_coins": "0",
        "country": null,
        "country_id": "0",
        "cover": "",
        "state": "",
        "city": null,
        "latitude": "",
        "longitude": "",
        "credits": null,
        "age": "25",
        "dob": "1995-05-01",
        "whatsup": null,
        "gender": "male",
        "sexuality": null,
        "profile_picture": "",
        "ip": null,
        "registered": "2020-11-19",
        "register_time": "",
        "last_login_date": "2020-12-05",
        "last_login": "02:58:23 PM",
        "last_active": null,
        "otp_verified": "1",
        "otp": "180221",
        "is_verified": "1",
        "is_online": "1",
        "is_broadcasting": "0",
        "height": null,
        "weight": null,
        "last_encounter": null,
        "relationship": "",
        "status": "1"
    },
* */


public class Result {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("diamond")
    @Expose
    private String diamond;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("firebase_uid")
    @Expose
    private String firebaseUid;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("broadcast_point")
    @Expose
    private String broadcastPoint;
    @SerializedName("golden_coins")
    @Expose
    private String goldenCoins;
    @SerializedName("silver_coins")
    @Expose
    private String silverCoins;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("credits")
    @Expose
    private String credits;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("whatsup")
    @Expose
    private String whatsup;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("sexuality")
    @Expose
    private String sexuality;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("ip")
    @Expose
    private String ip;
    @SerializedName("registered")
    @Expose
    private String registered;
    @SerializedName("register_time")
    @Expose
    private String registerTime;
    @SerializedName("last_login_date")
    @Expose
    private String lastLoginDate;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("last_active")
    @Expose
    private String lastActive;
    @SerializedName("otp_verified")
    @Expose
    private String otpVerified;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("is_verified")
    @Expose
    private String isVerified;
    @SerializedName("is_online")
    @Expose
    private String isOnline;
    @SerializedName("is_broadcasting")
    @Expose
    private String isBroadcasting;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("last_encounter")
    @Expose
    private String lastEncounter;

    @SerializedName("relationship")
    @Expose
    private String relationship;

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("user_token")
    @Expose
    public String user_token;
    @SerializedName("level")
    @Expose
    private String level;

    public String getId() {
        return id;
    }
    public String getDiamond() {
        return diamond;
    }
    public void setDiamond(String diamond)
    {

         this.diamond = diamond;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFbId() {

        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getBroadcastPoint() {
        return broadcastPoint;
    }

    public void setBroadcastPoint(String broadcastPoint) {
        this.broadcastPoint = broadcastPoint;
    }

    public String getGoldenCoins() {
        return goldenCoins;
    }

    public void setGoldenCoins(String goldenCoins) {
        this.goldenCoins = goldenCoins;
    }

    public String getSilverCoins() {
        return silverCoins;
    }

    public void setSilverCoins(String silverCoins) {
        this.silverCoins = silverCoins;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getWhatsup() {
        return whatsup;
    }

    public void setWhatsup(String whatsup) {
        this.whatsup = whatsup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSexuality() {
        return sexuality;
    }

    public void setSexuality(String sexuality) {
        this.sexuality = sexuality;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastActive() {
        return lastActive;
    }

    public void setLastActive(String lastActive) {
        this.lastActive = lastActive;
    }

    public String getOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(String otpVerified) {
        this.otpVerified = otpVerified;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getIsBroadcasting() {
        return isBroadcasting;
    }

    public void setIsBroadcasting(String isBroadcasting) {
        this.isBroadcasting = isBroadcasting;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLastEncounter() {
        return lastEncounter;
    }

    public void setLastEncounter(String lastEncounter) {
        this.lastEncounter = lastEncounter;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getToken(){
        String[] str = user_token.split(" ");
        if(str.length>0){
            return str[1];
        }

        return null;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
}