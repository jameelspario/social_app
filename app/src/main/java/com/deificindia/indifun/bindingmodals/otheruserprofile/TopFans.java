package com.deificindia.indifun.bindingmodals.otheruserprofile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopFans implements Parcelable {

    @SerializedName("user_id")
    @Expose
    private String userid;
    @SerializedName("coin")
    @Expose
    private int coincollect;
    @SerializedName("profile_picture")
    @Expose
    private String profilepic;
    @SerializedName("full_name")
    @Expose
    private String full_name;
    @SerializedName("age")
    @Expose
    private  String age;

    @SerializedName("level")
    @Expose
    private  String level;
    @SerializedName("gender")
    @Expose
    private  String gender;
    private String color="#000000";

    public TopFans() {
    }

    public TopFans(String userid, int coincollect, String profilepic) {
        this.userid = userid;
        this.coincollect = coincollect;
        this.profilepic = profilepic;
    }

    public TopFans(Parcel in) {
        userid = in.readString();
        coincollect = in.readInt();
        profilepic = in.readString();
    }

    public static final Creator<TopFans> CREATOR = new Creator<TopFans>() {
        @Override
        public TopFans createFromParcel(Parcel in) {
            return new TopFans(in);
        }

        @Override
        public TopFans[] newArray(int size) {
            return new TopFans[size];
        }
    };

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCoincollect() {
        return coincollect;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public String getUserid() {
        return userid;
    }

    public void setCoincollect(int coincollect) {
        this.coincollect = coincollect;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userid);
        dest.writeInt(coincollect);
        dest.writeString(profilepic);
    }


    public String getLevel() {
        return level;
    }

    public String getAge() {
        return age;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
