package com.deificindia.firebaseModel;

public class FollowerNotificaiton {
    private String userid;
    private String text;
    private String postid;
    private boolean ispost;
    private String time;


    public FollowerNotificaiton(String userid,String text, String postid, boolean ispost,String time) {
        this.userid = userid;
        this.text = text;
        this.postid = postid;
        this.ispost = ispost;
        this.time=time;


    }

    public FollowerNotificaiton() {
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time=time;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public boolean isIspost() {
        return ispost;
    }

    public void setIspost(boolean ispost) {
        this.ispost = ispost;
    }

}
