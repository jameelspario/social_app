package com.deificindia.firebaseModel;

public class AddLiveModel {

    private String Joiner_id;
    private String Joiner_name;
    public String broad_id;
    public String broad_key;
    public String broad_user;
    public boolean isgolive;
    public String owneruid;
    public String image;
    public String roomid;
    public boolean  isOwner;
    public boolean isPkMode, isPkSender, onCall;
    public long broad_usr_avtar_type=0;
    public int broad_room_type = 1;

//    public void AdLiveModel(String roomid,String image,String Joiner_id,String Joiner_name,String broad_id,String broad_user,String broad_key,boolean isgolive){
//        this.Joiner_id=Joiner_id;
//        this.broad_id=broad_id;
//        this.broad_key=broad_key;
//        this.broad_user=broad_user;
//        this.Joiner_name=Joiner_name;
//        this.isgolive=isgolive;
//        this.roomid=roomid;
//        this.image=image;
//    }
public String getRoomid(){
        return roomid;
}
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJoiner_id() {
        return Joiner_id;
    }

    public String getBroad_id() {
        return broad_id;
    }

    public boolean isIsgolive() {
        return isgolive;
    }

    public String getBroad_key() {
        return broad_key;
    }

    public void setBroad_id(String broad_id) {
        this.broad_id = broad_id;
    }

    public String getJoiner_name() {
        return Joiner_name;
    }

    public String getBroad_user() {
        return broad_user;
    }

    public void setJoiner_id(String joiner_id) {
        Joiner_id = joiner_id;
    }

    public void setBroad_key(String broad_key) {
        this.broad_key = broad_key;
    }

    public void setBroad_user(String broad_user) {
        this.broad_user = broad_user;
    }

    public void setJoiner_name(String joiner_name) {
        Joiner_name = joiner_name;
    }

    public void setIsgolive(boolean isgolive) {
        this.isgolive = isgolive;
    }
}
