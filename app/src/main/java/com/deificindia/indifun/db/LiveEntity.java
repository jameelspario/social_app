package com.deificindia.indifun.db;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class LiveEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int isconnected;
//-----firebase data--real--time---//
//private String title;
//    private String latitude;
//    private String longitude;
//    private String publisher;
//    private String user_id;
//    private String temp_profile;
//    private boolean isgolive;


//--firebase realtime--?///



    public String status;
    public String search;
    public String id1;



    //------firebase data---------
    public String roomname;
    public String roomid;
    public String owneruid;
    public String owneruidweb;
    public String ownername;
    public String owneravtar;
    public long owneravtartype;
    public long broadid;
    public long pkid;


   /* public String oage;
    public String ogender;
    public String olevel;
    public String oheart;
    public String odiamond;
    public String oxp;
    public String ofriends;

    public String gender;
    public String age;
    public String whatsupp;*/

    public Timestamp timestamp;

    public int pksender;
    public int ispk;
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
    public double latitude;
    public double longitude;
    private String title;

    private String publisher;
    private String user_id;
    //    private String id;
//    private String temp_profile;
    private boolean isgolive;

    public LiveEntity(String title, String full_name, double latitude,double longitude,String publisher, String user_id,String image,boolean isgolive) {
        this.title=title;
        this.latitude=latitude;
        this.longitude=longitude;
        this.publisher=publisher;
        this.user_id=user_id;

        this.image=image;
        this.isgolive=isgolive;
    }

    public LiveEntity() {
    }

    public boolean isIsgolive(){
        return isgolive;
    }
    public void setIsgolive(boolean isgolive){
        this.isgolive=isgolive;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getTemp_profile(){
//        Log.d("temp",temp_profile);
        return image;

    }
    public void setTemp_profile(String temp_profile){
        this.image=temp_profile;
    }

    public void setPublisher(String publisher){
        this.publisher=publisher;
    }
    public String getPublisher(){
        return publisher;
    }
    public void setUser_id(String user_id){
        this.user_id=user_id;
    }
    public String getUser_id(){
        return user_id;
    }

    public String getFull_name(){
        return full_name;
    }
    public void setFull_name(String full_name){
        this.full_name=full_name;
    }
   public double getLatitude(){
        return latitude;
   }
   public void setLatitude(double latitude){
        this.latitude=latitude;
   }  public double getLongitude(){
        return longitude;
   }
   public void setLongitude(double longitude){
        this.longitude=longitude;
   }
    //-----Api-Data--------------------------------

    public String full_name="";
    public String uid=""; /*"USER10586"*/
    public String fuid=""; /*"Ro0JDaJPdOcKNiD3LcF61F9Q3Lg1"*/
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

    public String image=""; //profile_picture // owneravtar
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
        return "LiveEntity{" +
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
                ", fuid='" + fuid + '\'' +
                ", wuid='" + wuid + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", diamond='" + diamond + '\'' +
                ", heart='" + heart + '\'' +
                ", registered='" + registered + '\'' +
                ", friends='" + friends + '\'' +///////////
                ", is_friend=" + is_friend +
                ", is_blocked=" + is_blocked +
                ", is_following=" + is_following +
                ", matched=" + matched +
                ", image='" + image + '\'' +
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
