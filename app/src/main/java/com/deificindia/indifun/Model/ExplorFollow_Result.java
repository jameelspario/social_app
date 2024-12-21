package com.deificindia.indifun.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExplorFollow_Result {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("entry_date")
    @Expose
    private String entry_date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("total_likes")
    @Expose
    private String total_likes;
    @SerializedName("total_comments")
    @Expose
    private String total_comments;
    @SerializedName("post_by")
    @Expose
    private String post_by;
    @SerializedName("user_name")
    @Expose
    private String user_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal_likes() {
        return total_likes;
    }

    public void setTotal_likes(String total_likes) {
        this.total_likes = total_likes;
    }

    public String getTotal_comments() {
        return id;
    }

    public void setTotal_comments(String total_comments) {
        this.total_comments = total_comments;
    }

    public String getPost_by() {
        return post_by;
    }

    public void setPost_by(String post_by) {
        this.post_by = post_by;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
