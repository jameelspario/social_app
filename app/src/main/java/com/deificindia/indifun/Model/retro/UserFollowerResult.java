
package com.deificindia.indifun.Model.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class UserFollowerResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("following_id")
    @Expose
    private String followingId;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("time")
    @Expose
    private String time;
    private String full_name;
    private String age;
    private String gender;
    private int frnd_icon;
    private String level;
    private String profile_picture;

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

    public String getFollowingId() {
        return followingId;
    }

    public void setFollowingId(String followingId) {
        this.followingId = followingId;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFrnd_icon(){
        return frnd_icon;
    }

    public  void setFrnd_icon(int frnd_icon){
        this.frnd_icon = frnd_icon;
    }

    public String getLevel(){
        return level;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof UserFollowerResult))
            return false;

        if((userId.equals(((UserFollowerResult) o).getUserId()) && followingId.equals(((UserFollowerResult) o).getFollowingId())) || full_name==null)
            return true;
        else return false;

    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, followingId, full_name);
    }

    public UserFollowerResult() {
        super();
    }
}
