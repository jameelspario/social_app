package com.deificindia.indifun.Model.agoramodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestToken {

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
        @SerializedName("token_uid")
        @Expose
        private String tokenUid;
        @SerializedName("token_uaccount")
        @Expose
        private String tokenUaccount;
        @SerializedName("channelName")
        @Expose
        private String channelName;
        @SerializedName("room_id")
        @Expose
        private String roomId;
        @SerializedName("user_id")
        @Expose
        private String userId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTokenUid() {
            return tokenUid;
        }

        public void setTokenUid(String tokenUid) {
            this.tokenUid = tokenUid;
        }

        public String getTokenUaccount() {
            return tokenUaccount;
        }

        public void setTokenUaccount(String tokenUaccount) {
            this.tokenUaccount = tokenUaccount;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

}
