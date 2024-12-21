package com.deificindia.indifun.Model.abs_plugs;

import com.deificindia.indifun.Model.abs.Abs;

/*
{
"id": "28",
"user_id": "555",
"user_name": "jam",
"title": "sfcv",
"user_image": "",
"duration": "0",
"is_live": "0",
"temp_profile": "",
"entrydate": "2021-01-16",
"entry_time": "03:51:14 PM",
"completion_duration": "120",
"end_time": "03:53:50 PM",
"broadcasting_type": "3",
"entry_militime": "",
"end_militime": "",
"pk_with": "0",
"winner_user": "0",
"block": "0"
},
* */
public class BroadWatched extends Abs {
    private int id;
    private int user_id;
    private String user_name;
    private String title;
    private String user_image;
    private int duration;
    private int is_live;
    private String temp_profile;
    private String entrydate;
    private String entry_time;
    private String completion_duration;
    private String end_time;
    private String broadcasting_type;
    private String entry_militime;
    private String end_militime;
    private String pk_with;
    private String winner_user;
    private String block;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getIs_live() {
        return is_live;
    }

    public void setIs_live(int is_live) {
        this.is_live = is_live;
    }

    public String getTemp_profile() {
        return temp_profile;
    }

    public void setTemp_profile(String temp_profile) {
        this.temp_profile = temp_profile;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public String getCompletion_duration() {
        return completion_duration;
    }

    public void setCompletion_duration(String completion_duration) {
        this.completion_duration = completion_duration;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getBroadcasting_type() {
        return broadcasting_type;
    }

    public void setBroadcasting_type(String broadcasting_type) {
        this.broadcasting_type = broadcasting_type;
    }

    public String getEntry_militime() {
        return entry_militime;
    }

    public void setEntry_militime(String entry_militime) {
        this.entry_militime = entry_militime;
    }

    public String getEnd_militime() {
        return end_militime;
    }

    public void setEnd_militime(String end_militime) {
        this.end_militime = end_militime;
    }

    public String getPk_with() {
        return pk_with;
    }

    public void setPk_with(String pk_with) {
        this.pk_with = pk_with;
    }

    public String getWinner_user() {
        return winner_user;
    }

    public void setWinner_user(String winner_user) {
        this.winner_user = winner_user;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}
