package com.deificindia.indifun.Model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public class Result {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("fb_id")
        @Expose
        private String fbId;
        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("full_name")
        @Expose
        private Object fullName;
        @SerializedName("email")
        @Expose
        private Object email;
        @SerializedName("contact")
        @Expose
        private String contact;
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
        private Object country;
        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private Object city;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("credits")
        @Expose
        private Object credits;
        @SerializedName("age")
        @Expose
        private String age;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("whatsup")
        @Expose
        private Object whatsup;
        @SerializedName("gender")
        @Expose
        private Object gender;
        @SerializedName("sexuality")
        @Expose
        private Object sexuality;
        @SerializedName("profile_picture")
        @Expose
        private Object profilePicture;
        @SerializedName("ip")
        @Expose
        private Object ip;
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
        private Object lastLogin;
        @SerializedName("last_active")
        @Expose
        private Object lastActive;
        @SerializedName("otp_verified")
        @Expose
        private Object otpVerified;
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
        @SerializedName("language")
        @Expose
        private Object language;
        @SerializedName("height")
        @Expose
        private Object height;
        @SerializedName("weight")
        @Expose
        private Object weight;
        @SerializedName("last_encounter")
        @Expose
        private Object lastEncounter;
        @SerializedName("languages")
        @Expose
        private String languages;
        @SerializedName("relationship")
        @Expose
        private String relationship;
        @SerializedName("status")
        @Expose
        private String status;

        public String getId() {
            return id;
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

        public Object getFullName() {
            return fullName;
        }

        public void setFullName(Object fullName) {
            this.fullName = fullName;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
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

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
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

        public Object getCredits() {
            return credits;
        }

        public void setCredits(Object credits) {
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

        public Object getWhatsup() {
            return whatsup;
        }

        public void setWhatsup(Object whatsup) {
            this.whatsup = whatsup;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public Object getSexuality() {
            return sexuality;
        }

        public void setSexuality(Object sexuality) {
            this.sexuality = sexuality;
        }

        public Object getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(Object profilePicture) {
            this.profilePicture = profilePicture;
        }

        public Object getIp() {
            return ip;
        }

        public void setIp(Object ip) {
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

        public Object getLastLogin() {
            return lastLogin;
        }

        public void setLastLogin(Object lastLogin) {
            this.lastLogin = lastLogin;
        }

        public Object getLastActive() {
            return lastActive;
        }

        public void setLastActive(Object lastActive) {
            this.lastActive = lastActive;
        }

        public Object getOtpVerified() {
            return otpVerified;
        }

        public void setOtpVerified(Object otpVerified) {
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

        public Object getLanguage() {
            return language;
        }

        public void setLanguage(Object language) {
            this.language = language;
        }

        public Object getHeight() {
            return height;
        }

        public void setHeight(Object height) {
            this.height = height;
        }

        public Object getWeight() {
            return weight;
        }

        public void setWeight(Object weight) {
            this.weight = weight;
        }

        public Object getLastEncounter() {
            return lastEncounter;
        }

        public void setLastEncounter(Object lastEncounter) {
            this.lastEncounter = lastEncounter;
        }

        public String getLanguages() {
            return languages;
        }

        public void setLanguages(String languages) {
            this.languages = languages;
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

}


}
