package com.deificindia.indifun.fires.m;

import com.deificindia.indifun.modals.Result;
import com.google.firebase.database.Exclude;

import java.io.InputStream;

public class UserJoinedInfo {

    public String user_fuid;
    public String user_wuid;
    public String user_age;
    public String user_avtar;
    public String user_gender;
    public String user_name;
    public String user_level;
    public Long user_xp;
    public String user_frame;
    public String user_animation;
    public String level_color; /*#000*/
    public String gift_background;
    public String entry_background;
    public String animation_from;

    public String user_broad_identity; /*localfuidOwnerfuid*/
    public String owner_broadcast_id; /*34354*/

    public String room_id;
    public String room_owner_fuid;

    @Exclude
    public InputStream inputStream;

    @Exclude
    public boolean isAnimationPlayed;



    public UserJoinedInfo() {}

    public UserJoinedInfo(String user_fuid, String user_broad_id, String room_id, String room_owner_fuid, String owner_broad_id) {
        this.user_fuid = user_fuid;
        this.user_broad_identity = user_broad_id;
        this.room_id = room_id;
        this.room_owner_fuid = room_owner_fuid;
        this.owner_broadcast_id = owner_broad_id;
    }

    public UserJoinedInfo getinfo(Result credentials) {
        this.user_wuid = credentials.getId();
        this.user_name = credentials.getFullName();
        this.user_avtar = credentials.getProfilePicture();
        this.user_age = credentials.getAge();
        this.user_gender = credentials.getGender();
        this.user_level = credentials.getLevel();

        return this;
    }


}
