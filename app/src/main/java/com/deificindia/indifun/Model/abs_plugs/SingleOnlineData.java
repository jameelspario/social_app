package com.deificindia.indifun.Model.abs_plugs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleOnlineData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("firebase_uid")
    @Expose
    private String firebaseUid;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("whatsup")
    @Expose
    private String whatsup;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("registered")
    @Expose
    private String registered;
    @SerializedName("diamond")
    @Expose
    private String diamond;
    @SerializedName("heart")
    @Expose
    private String heart;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("my_xp")
    @Expose
    private String myXp;
    @SerializedName("to_next_level")
    @Expose
    private String toNextLevel;
    @SerializedName("no_of_friends")
    @Expose
    private int noOfFriends;
    @SerializedName("is_online")
    @Expose
    private String isOnline;
    @SerializedName("add_broadcast_id")
    @Expose
    private String addBroadcastId;
    @SerializedName("add_broadcast_title")
    @Expose
    private String addBroadcastTitle;
    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("count_down_in_sec")
    @Expose
    private String countDownInSec;
    @SerializedName("broadcasting_type")
    @Expose
    private String broadcastingType;
    @SerializedName("is_broadcasting")
    @Expose
    private String isBroadcasting;
    @SerializedName("is_following")
    @Expose
    private int isFollowing;
    @SerializedName("is_blocked")
    @Expose
    private int isBlocked;
    @SerializedName("is_friend")
    @Expose
    private int isFriend;
    private int is_mute;
    private int is_kick;

    private double latitude;
    private double longitude;


    @SerializedName("is_matched")
    @Expose
    private int isMatched;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getWhatsup() {
        return whatsup;
    }

    public void setWhatsup(String whatsup) {
        this.whatsup = whatsup;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMyXp() {
        return myXp;
    }

    public void setMyXp(String myXp) {
        this.myXp = myXp;
    }

    public String getToNextLevel() {
        return toNextLevel;
    }

    public void setToNextLevel(String toNextLevel) {
        this.toNextLevel = toNextLevel;
    }

    public int getNoOfFriends() {
        return noOfFriends;
    }

    public void setNoOfFriends(int noOfFriends) {
        this.noOfFriends = noOfFriends;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getAddBroadcastId() {
        return addBroadcastId;
    }

    public void setAddBroadcastId(String addBroadcastId) {
        this.addBroadcastId = addBroadcastId;
    }

    public String getAddBroadcastTitle() {
        return addBroadcastTitle;
    }

    public void setAddBroadcastTitle(String addBroadcastTitle) {
        this.addBroadcastTitle = addBroadcastTitle;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCountDownInSec() {
        return countDownInSec;
    }

    public void setCountDownInSec(String countDownInSec) {
        this.countDownInSec = countDownInSec;
    }

    public String getBroadcastingType() {
        return broadcastingType;
    }

    public void setBroadcastingType(String broadcastingType) {
        this.broadcastingType = broadcastingType;
    }

    public String getIsBroadcasting() {
        return isBroadcasting;
    }

    public void setIsBroadcasting(String isBroadcasting) {
        this.isBroadcasting = isBroadcasting;
    }

    public int getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(int isFollowing) {
        this.isFollowing = isFollowing;
    }

    public int getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public int getIsMatched() {
        return isMatched;
    }

    public void setIsMatched(int isMatched) {
        this.isMatched = isMatched;
    }

    public int getIs_mute() {
        return is_mute;
    }

    public void setIs_mute(int is_mute) {
        this.is_mute = is_mute;
    }

    public int getIs_kick() {
        return is_kick;
    }

    public void setIs_kick(int is_kick) {
        this.is_kick = is_kick;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
