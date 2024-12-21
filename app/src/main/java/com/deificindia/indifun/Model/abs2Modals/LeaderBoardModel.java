package com.deificindia.indifun.Model.abs2Modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderBoardModel {

   /* "coin_count": 0,
            "user_id": "491",
            "firebase_uid": "",
            "full_name": "Shamshad",
            "age": "24",
            "profile_picture": "19d80d5a198b17a667bdc051d6078d02.png",
            "gender": "Male",
            "is_live": null,
            "broadcasting_type": null,
            "is_broadcasting": "0",
            "level": "4",
            "is_following": 0,
            "is_friend": 0*/

    @SerializedName("diamond_count")
    @Expose
    private long diamondCount;

    @SerializedName("coin_count")
    @Expose
    private Integer coinCount;

    @SerializedName("followers_count")
    @Expose
    private long followersCount;

    @SerializedName("firebase_uid")
    @Expose
    private String firebase_uid;

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("level")
    @Expose
    private String level;

    private int is_live;
    private int is_broadcasting;
    private int broadcasting_type;
    private int is_following;
    private int is_friend;

    public long getDiamondCount() {
        return diamondCount;
    }

    public void setDiamondCount(long diamondCount) {
        this.diamondCount = diamondCount;
    }

    public Integer getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(Integer coinCount) {
        this.coinCount = coinCount;
    }

    public long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(long followersCount) {
        this.followersCount = followersCount;
    }

    public String getFirebase_uid() {
        return firebase_uid;
    }

    public void setFirebase_uid(String firebase_uid) {
        this.firebase_uid = firebase_uid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getIs_live() {
        return is_live;
    }

    public void setIs_live(int is_live) {
        this.is_live = is_live;
    }

    public int getIs_broadcasting() {
        return is_broadcasting;
    }

    public void setIs_broadcasting(int is_broadcasting) {
        this.is_broadcasting = is_broadcasting;
    }

    public int getBroadcasting_type() {
        return broadcasting_type;
    }

    public void setBroadcasting_type(int broadcasting_type) {
        this.broadcasting_type = broadcasting_type;
    }

    public int getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(int is_friend) {
        this.is_friend = is_friend;
    }

    public int getIs_following() {
        return is_following;
    }

    public void setIs_following(int is_following) {
        this.is_following = is_following;
    }
}
