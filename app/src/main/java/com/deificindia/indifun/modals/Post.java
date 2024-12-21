package com.deificindia.indifun.modals;

import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    @PrimaryKey(autoGenerate = true)
   public int id;


    public String title;
    public String oage;
    public String ogender;
    public String lattitude;
    public String longitude;
    public String gender1;
    public String image;
    public String broad_user;
    public String title1;

    public String getLattitude() {
        return lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getOage() {
        return oage;
    }

    public String getOgender() {
        return ogender;
    }

    public String getRoomid() {
        return roomid;
    }
    public void setRoomid(String room_id){
        this.roomid=room_id;
    }



    public boolean getPksender() {
        return pksender;
    }

    public long getOwneravtartype() {
        return owneravtartype;
    }

    public long getPkduration() {
        return pkduration;
    }

    public String getUid() {
        return uid;
    }
    public String getOwneravtar() {
        return owneravtar;
    }
    public long getBroadid() {
        return broadid;
    }
//
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public String getRoomname() {
        return roomname;
    }

    public String getOwnername() {
        return ownername;
    }

    public String roomname;
    public String roomid;
    public String owneruid;
    public String owneruidweb;
    public String ownername;
    public String owneravtar;
    public long owneravtartype;
    public long broadid;
    public long pkid;

    public Timestamp timestamp;

    public boolean pksender;
    public boolean ispk;
    public long pkduration;
    public long pkstarttime;
    public long localpoint;
    public long remotepoint;
    public long state;
    public long callcount;
    public String calltype;

    public String pkroomid;
    public String pkuseruid;
    public String pkuseruidweb;
    public String pkusername;
    public String pkavtar;
    public long pkavtartype;
    public long pkbroadid;
    public long pkpkid;
//    public double latitude;
//    public double longitude;
    //-----Api-Data--------------------------------

  //  public String full_name="";
    public String uid=""; /*"USER10586"*/
   // public String fuid=""; /*"Ro0JDaJPdOcKNiD3LcF61F9Q3Lg1"*/
    public String wuid=""; /*"586"*/
    public String age="";
    public String gender="";
    public String diamond="";
    public String heart="";
    public String registered;
    public int friends;

    public int is_friend;
    public int is_blocked;
    public int is_following;
    public int matched;
    public int is_mute;
    public int is_kick;
    public long blocked_time;

   // public String image=""; //profile_picture // owneravtar
    public int is_online;
    public String level="";
    public String my_xp;
    public String to_next_level;
    public String count_down_in_sec;
    @Deprecated public String room_id="";
    public String add_broadcast_id="";
    public String add_broadcast_title="";
    public String is_broadcasting="";
    public String broadcasting_type="";
    public String whatsapp;



    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");

        return sdf.format(new Date(timestamp.getSeconds()));
    }


    @Override
    public @NotNull String toString() {
        return "Post{" +
                "id=" + id +
                ", roomname='" + roomname + '\'' +
                ", roomid='" + roomid + '\'' +
//                ", owneruid='" + owneruid + '\'' +
//                ", owneruidweb='" + owneruidweb + '\'' +
//                ", ownername='" + ownername + '\'' +
//                ", owneravtar='" + owneravtar + '\'' +
                ", owneravtartype=" + owneravtartype +
                ", broadid='" + broadid + '\'' +
                ", timestamp=" + timestamp +
                ", pksender=" + pksender +
                ", ispk=" + ispk +
                ", pkduration=" + pkduration +
                ", pkstarttime=" + pkstarttime +
                ", localpoint=" + localpoint +
                ", remotepoint=" + remotepoint +
                ", state=" + state +
                ", pkroomid='" + pkroomid + '\'' +
                ", pkuseruid='" + pkuseruid + '\'' +
                ", pkuseruidweb='" + pkuseruidweb + '\'' +
                ", pkusername='" + pkusername + '\'' +
                ", pkavtar='" + pkavtar + '\'' +
                ", pkavtartype=" + pkavtartype +
                ", uid='" + uid + '\'' +
                ", fuid='" + owneruid + '\'' +
                ", wuid='" + wuid + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", diamond='" + diamond + '\'' +
                ", heart='" + heart + '\'' +
                ", registered='" + registered + '\'' +
                ", friends='" + friends + '\'' +
                ", is_friend=" + is_friend +
                ", is_blocked=" + is_blocked +
                ", is_following=" + is_following +
                ", matched=" + matched +
                ", image='" + owneravtar + '\'' +
                ", is_online='" + is_online + '\'' +
                ", level='" + level + '\'' +
                ", my_xp='" + my_xp + '\'' +
                ", to_next_level='" + to_next_level + '\'' +
                ", count_down_in_sec='" + count_down_in_sec + '\'' +
                ", room_id='" + room_id + '\'' +
                ", add_broadcast_id='" + add_broadcast_id + '\'' +
                ", add_broadcast_title='" + add_broadcast_title + '\'' +
                ", is_broadcasting='" + is_broadcasting + '\'' +
                ", broadcasting_type='" + broadcasting_type + '\'' +
                ", whatsapp='" + whatsapp + '\'' +
                '}';
    }


}
