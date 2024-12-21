
package com.deificindia.indifun.Model.retro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatModel_Result {

    /*
    *
* {
"id": "1",
"user_to": "555",
"user_from": "590",
"username_to": "jam",
"username_from": "Nike",
"image_to": "",
"image_from": "dace54d3125cfc355664335e84e5275c.png",
"date": "2021-01-15",
"time": "04:44:26 PM",
"mili_time": "1610709266448"
}
*
    * */

    private String id;
    private String user_to;
    private String user_from;
    private String username_to;
    private String username_from;
    private String image_to;
    private String image_from;
    private String date;
    private String time;
    private long mili_time;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_to() {
        return user_to;
    }

    public void setUser_to(String user_to) {
        this.user_to = user_to;
    }

    public String getUser_from() {
        return user_from;
    }

    public void setUser_from(String user_from) {
        this.user_from = user_from;
    }

    public String getUsername_to() {
        return username_to;
    }

    public void setUsername_to(String username_to) {
        this.username_to = username_to;
    }

    public String getUsername_from() {
        return username_from;
    }

    public void setUsername_from(String username_from) {
        this.username_from = username_from;
    }

    public String getImage_to() {
        return image_to;
    }

    public void setImage_to(String image_to) {
        this.image_to = image_to;
    }

    public String getImage_from() {
        return image_from;
    }

    public void setImage_from(String image_from) {
        this.image_from = image_from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getMili_time() {
        return mili_time;
    }

    public void setMili_time(long mili_time) {
        this.mili_time = mili_time;
    }
}
