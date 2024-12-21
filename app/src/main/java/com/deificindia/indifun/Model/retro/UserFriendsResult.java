package com.deificindia.indifun.Model.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class UserFriendsResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("friend_id")
    @Expose
    private String friendId;
    @SerializedName("friendship_date")
    @Expose
    private String friendshipDate;
    private String full_name;
    private String age;
    private String gender;
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

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendshipDate() {
        return friendshipDate;
    }

    public void setFriendshipDate(String friendshipDate) {
        this.friendshipDate = friendshipDate;
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

        if (!(o instanceof UserFriendsResult))
            return false;

        if((userId.equals(((UserFriendsResult) o).getUserId()) && friendId.equals(((UserFriendsResult) o).getFriendId())) || full_name==null)
            return true;
        else return false;

    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId, full_name);
    }

    public UserFriendsResult() {
        super();
    }
}
