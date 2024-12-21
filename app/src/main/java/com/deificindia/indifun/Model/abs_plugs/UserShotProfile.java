package com.deificindia.indifun.Model.abs_plugs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserShotProfile {

    /*
    {
        "id": "590",
        "uid": "USER10593",
        "firebase_uid": "kLapj2xOYYh2MxrqawCG7XzjeyL2",
        "full_name": "Nike",
        "age": "23",
        "whatsup": "hii",
        "gender": "Male",
        "profile_picture": "dace54d3125cfc355664335e84e5275c.png",
        "heart": "34",
        "diamond": "1477",
        "user_level": "7",
        "my_xp": "11670",
        "to_next_userlevel": 7530,
        "broadcast_level": "5",
        "my_broadcasting_xp": "2701",
        "to_next_broadcastlevel": 2099,
        "is_following": 0,
        "is_blocked": null,
        "is_mute": null,
        "is_kick": null
    }
    * */
    private String id; //"590",
    private String uid; //"USER10593",
    private String firebase_uid;
    private String full_name;
    private String age;
    private String whatsup;
    private String gender;
    private String profile_picture;
    private String heart;
    private String diamond;
    private int user_level;
    private int my_xp;
    private int to_next_userlevel;
    private int broadcast_level;
    private int my_broadcasting_xp;
    private int to_next_broadcastlevel;
    private int is_following;
    private int is_blocked;
    private int is_mute;
    private int is_kick;


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

    public String getFirebase_uid() {
        return firebase_uid;
    }

    public void setFirebase_uid(String firebase_uid) {
        this.firebase_uid = firebase_uid;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public int getMy_xp() {
        return my_xp;
    }

    public void setMy_xp(int my_xp) {
        this.my_xp = my_xp;
    }

    public int getTo_next_userlevel() {
        return to_next_userlevel;
    }

    public void setTo_next_userlevel(int to_next_userlevel) {
        this.to_next_userlevel = to_next_userlevel;
    }

    public int getBroadcast_level() {
        return broadcast_level;
    }

    public void setBroadcast_level(int broadcast_level) {
        this.broadcast_level = broadcast_level;
    }

    public int getMy_broadcasting_xp() {
        return my_broadcasting_xp;
    }

    public void setMy_broadcasting_xp(int my_broadcasting_xp) {
        this.my_broadcasting_xp = my_broadcasting_xp;
    }

    public int getTo_next_broadcastlevel() {
        return to_next_broadcastlevel;
    }

    public void setTo_next_broadcastlevel(int to_next_broadcastlevel) {
        this.to_next_broadcastlevel = to_next_broadcastlevel;
    }

    public int getIs_following() {
        return is_following;
    }

    public void setIs_following(int is_following) {
        this.is_following = is_following;
    }

    public int getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(int is_blocked) {
        this.is_blocked = is_blocked;
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
}
