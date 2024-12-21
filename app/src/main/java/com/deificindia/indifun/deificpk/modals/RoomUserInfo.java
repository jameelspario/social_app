package com.deificindia.indifun.deificpk.modals;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomUserInfo {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String identity;
    public String roomname; //roomid
    public String ownerfuid;
    public String fuid;
    public String wuid;
    public String avtar;
    public String image;
    public String name;
    public long points;
    public String level;
    public String broadid;
    public String senderavtar;
    public boolean online;
    public boolean enterplayed;
    private String Joiner_id;
    private String Joiner_name;
    private String broad_id;
    private String broad_key;
    private String broad_user;
    private boolean isgolive;


    public String animation_type; // json / svga
    public String animation; // animation file name

    public RoomUserInfo() {}

    public String getAvtar(){

        return avtar;

    }
    public RoomUserInfo(String image,String Joiner_id,String Joiner_name,String broad_id,String broad_user,String broad_key,boolean isgolive){
        this.Joiner_id=Joiner_id;
        this.broad_id=broad_id;
        this.broad_key=broad_key;
        this.broad_user=broad_user;
        this.Joiner_name=Joiner_name;
        this.isgolive=isgolive;
        this.image=image;
    }
    public String getSenderavtar(){
        return senderavtar;
    }
    public void setSenderavtar(String senderavtar){
        this.senderavtar=senderavtar;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAvtar(String avtar) {
        this.avtar = avtar;
    }

    public RoomUserInfo(String wuid, long points) {
        this.wuid = wuid;
        this.points = points;
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


    public RoomUserInfo(String wuid, String fuid, String avtar) {
        this.wuid = wuid;
        this.fuid = fuid;
        this.avtar = avtar;
        Log.d("ava",avtar.toString()) ;

    }

    public RoomUserInfo(String wuid, String fuid, String avtar, String name) {
        this.wuid = wuid;
        this.fuid = fuid;
        this.avtar = avtar;
        this.name = name;
        Log.d("ava1",avtar.toString()) ;
    }


    public RoomUserInfo(String identity, String roomname, String ownerfuid, String fuid, boolean online) {
        this.identity = identity;
        this.roomname = roomname;
        this.ownerfuid = ownerfuid;
        this.fuid = fuid;
        this.online = online;
    }

}
