package com.deificindia.indifun.Model.retro;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LiveResult implements Parcelable {

    /*
         "user_id": "485",
            "user_name": "Ayush Agrawal",
            "diamond": null,
            "heart": null,
            "uid": "USER002",
            "registered": "2020-10-17",
            "level": "1",
            "my_xp": "40",
            "to_next_level": "260",
            "friends": "0",
            "image": "52559048a897f6a44bbb4b2a2b9df583.png",
            "is_online": "1",
            "add_broadcast_id": null,
            "add_broadcast_title": null,
            "room_id": null,
            "count_down_in_sec": null,
            "broadcasting_type": null,
            "is_broadcasting": "1"
        * */

    private String user_id="";
    private String uid;
    private String user_name="";
    private String age="";
    private String gender="";
    private String diamond="";
    private String heart="";
    private String registered;
    private String friends="";
    public String image="";
    private String is_online="";
    private String level="";
    private String my_xp;
    private String to_next_level;
    private String count_down_in_sec;
    private String room_id="";
    private String add_broadcast_id="";
    private String add_broadcast_title="";
    private String is_broadcasting="";
    private String broadcasting_type="";
    private String whatsapp;
    private String aristocracy;
    public long state=0;
    private ArrayList<String> blockUser;

    public LiveResult() { }

    protected LiveResult(Parcel in) {
        user_id = in.readString();
        uid = in.readString();
        user_name = in.readString();
        age = in.readString();
        gender = in.readString();
        diamond = in.readString();
        heart = in.readString();
        registered = in.readString();
        friends = in.readString();
        image = in.readString();
        is_online = in.readString();
        level = in.readString();
        my_xp = in.readString();
        to_next_level = in.readString();
        count_down_in_sec = in.readString();
        room_id = in.readString();
        add_broadcast_id = in.readString();
        add_broadcast_title = in.readString();
        is_broadcasting = in.readString();
        broadcasting_type = in.readString();
        whatsapp = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(uid);
        dest.writeString(user_name);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(diamond);
        dest.writeString(heart);
        dest.writeString(registered);
        dest.writeString(friends);
        dest.writeString(image);
        dest.writeString(is_online);
        dest.writeString(level);
        dest.writeString(my_xp);
        dest.writeString(to_next_level);
        dest.writeString(count_down_in_sec);
        dest.writeString(room_id);
        dest.writeString(add_broadcast_id);
        dest.writeString(add_broadcast_title);
        dest.writeString(is_broadcasting);
        dest.writeString(broadcasting_type);
        dest.writeString(whatsapp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LiveResult> CREATOR = new Creator<LiveResult>() {
        @Override
        public LiveResult createFromParcel(Parcel in) {
            return new LiveResult(in);
        }

        @Override
        public LiveResult[] newArray(int size) {
            return new LiveResult[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIs_online() {
        return is_online;
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMy_xp() {
        return my_xp;
    }

    public void setMy_xp(String my_xp) {
        this.my_xp = my_xp;
    }

    public String getTo_next_level() {
        return to_next_level;
    }

    public void setTo_next_level(String to_next_level) {
        this.to_next_level = to_next_level;
    }

    public String getCount_down_in_sec() {
        return count_down_in_sec;
    }

    public void setCount_down_in_sec(String count_down_in_sec) {
        this.count_down_in_sec = count_down_in_sec;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getAdd_broadcast_id() {
        return add_broadcast_id;
    }

    public void setAdd_broadcast_id(String add_broadcast_id) {
        this.add_broadcast_id = add_broadcast_id;
    }

    public String getAdd_broadcast_title() {
        return add_broadcast_title;
    }

    public void setAdd_broadcast_title(String add_broadcast_title) {
        this.add_broadcast_title = add_broadcast_title;
    }

    public String getIs_broadcasting() {
        return is_broadcasting;
    }

    public void setIs_broadcasting(String is_broadcasting) {
        this.is_broadcasting = is_broadcasting;
    }

    public String getBroadcasting_type() {
        return broadcasting_type;
    }

    public void setBroadcasting_type(String broadcasting_type) {
        this.broadcasting_type = broadcasting_type;
    }

    public static Creator<LiveResult> getCREATOR() {
        return CREATOR;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
}
