package com.deificindia.indifun.Model.abs_plugs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class JoinerName {
    /*
    *
    * {"full_name":"kunal pal","user_id":"588","profile_picture":"af78fb148af1ab1eb94dfde87172ca57.png",
    * "level":"1","uid":"lu2lmRvpDeOydWZWbMQGsNero5J2","gender":"Male","diamond":null,"heart":null,
    * "registered":"2020-12-19","my_xp":"0","to_next_level":300,"friends":0,"is_online":"0",
    * "add_broadcast_id":null,"add_broadcast_title":null,"count_down_in_sec":null,
    * "broadcasting_type":null,"is_broadcasting":"0"}
    *
    *
 */

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String full_name;
    public String user_id; //588
    public String profile_picture;
    public String level;
    public String firebase_uid; //firebase_uid
    public String uid; //USER10588
    public String gender;
    public String diamond;
    public String heart;
    public String registered;
    public String broadcast_level;
    public long broadcast_point;
    public String my_xp;
    public String total_xp;
    public String to_next_level;
    public String friends;
    public String is_online;
    public String add_broadcast_id;
    public String add_broadcast_title;
    public String count_down_in_sec;
    public String broadcasting_type;
    public String is_broadcasting;


}
