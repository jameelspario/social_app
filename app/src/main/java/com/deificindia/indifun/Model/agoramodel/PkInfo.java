package com.deificindia.indifun.Model.agoramodel;

public class PkInfo {
    public String userId;
    public long uid;
    public String userName;
    public String channelName;
    public String roomId;
    public String token;

    public PkInfo() { }

    public PkInfo(String userId, long uid, String userName, String channelName, String roomId) {
        this.userId = userId;
        this.uid = uid;
        this.userName = userName;
        this.channelName = channelName;
        this.roomId = roomId;
    }

    public PkInfo(String userId, long uid, String userName, String roomId) {
        this.userId = userId;
        this.uid = uid;
        this.userName = userName;
        this.roomId = roomId;
    }
}
