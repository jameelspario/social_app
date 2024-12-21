package com.deificindia.indifun.Model.abs_plugs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBroadResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("is_live")
    @Expose
    private String isLive;
    @SerializedName("temp_profile")
    @Expose
    private String tempProfile;
    @SerializedName("entrydate")
    @Expose
    private String entrydate;
    @SerializedName("entry_time")
    @Expose
    private String entryTime;
    @SerializedName("completion_duration")
    @Expose
    private String completionDuration;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("broadcasting_type")
    @Expose
    private String broadcastingType;
    @SerializedName("pk_with")
    @Expose
    private String pkWith;
    @SerializedName("winner_user")
    @Expose
    private String winnerUser;
    @SerializedName("block")
    @Expose
    private String block;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIsLive() {
        return isLive;
    }

    public void setIsLive(String isLive) {
        this.isLive = isLive;
    }

    public String getTempProfile() {
        return tempProfile;
    }

    public void setTempProfile(String tempProfile) {
        this.tempProfile = tempProfile;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getCompletionDuration() {
        return completionDuration;
    }

    public void setCompletionDuration(String completionDuration) {
        this.completionDuration = completionDuration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBroadcastingType() {
        return broadcastingType;
    }

    public void setBroadcastingType(String broadcastingType) {
        this.broadcastingType = broadcastingType;
    }

    public String getPkWith() {
        return pkWith;
    }

    public void setPkWith(String pkWith) {
        this.pkWith = pkWith;
    }

    public String getWinnerUser() {
        return winnerUser;
    }

    public void setWinnerUser(String winnerUser) {
        this.winnerUser = winnerUser;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}
