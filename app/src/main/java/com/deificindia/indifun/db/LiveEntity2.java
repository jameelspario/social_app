package com.deificindia.indifun.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.deificindia.indifun.deificpk.modals.ChatModal;
import com.google.firebase.database.Exclude;
import com.google.firebase.encoders.annotations.Encodable;

import java.io.Serializable;
import java.util.Map;

@Entity
public class LiveEntity2 implements Serializable {

    @Exclude
    @PrimaryKey(autoGenerate = true)
    public int id;

    @Ignore
    public int changeto = 0; /*1=;2=;3=;4=*/

    public String roomname;
    public String roomid;
    public String owneruid;     //firebase uid
    public String owneruidweb; //neumeric user id web
    public String ownername;
    public String owneravtar;
    public long owneravtartype;
    public long broadid;
    public String pkid = "0";           //number
    public long pkstarttime;    //number
    public boolean isgolive;
    public boolean isOwner;
    public String userid;           //string uid web :number
    public String lattitude;
    public String longitude;
    public String oage = "0";
    public String ogender;
    public boolean ispk;
    public boolean pksender;
    public long timestamp;

    public long state;

    public String pkPoint_local = "0";
    public String pkPoint_remote = "0";

    @Exclude public long is_online = 1;
    @Exclude public long is_friend = 0;
    @Exclude public long matched = 0;
    @Exclude public long is_following = 0; //I'm following or not'
    @Exclude public long follow_status = 0; //0=> no follow; 1=> I follow; 2=> He follow; 3 => both follow
    @Exclude public long no_of_friends = 0;
    @Exclude public long is_mute = 0;
    @Exclude public long is_kick = 0;
    @Exclude public long is_blocked = 0;
    @Exclude public long blocked_time = 0;


    public long callcount = 0;
    public String calltype; //audio | video

    @Ignore
    @com.google.firebase.database.Exclude
    public String broad_join_identity;

    @Ignore
    public PKInfo PkInfo;

   /* @Ignore
    @Exclude
    public EntityCall Calls;*/

    @Ignore
    @Exclude
    public ChatModal Chats;

    @Exclude
    public String pk_room;

    @Ignore
    public Map<String, EntityCall> Calls;


}
