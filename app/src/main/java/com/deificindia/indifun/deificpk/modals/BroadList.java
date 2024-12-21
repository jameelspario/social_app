package com.deificindia.indifun.deificpk.modals;

import com.deificindia.indifun.db.EntityCall;
import com.google.firebase.Timestamp;

import java.util.Map;

public class BroadList {

    public String roomname;
    public String roomid;
    public double  latitude;
    public  double longitude ;
    public String owneruid;
    public String owneruidweb;
    public String ownername;
    public String owneravtar;
    public long owneravtartype;
    public long broadid;
    public long pkid;

    public String oage;
    public String ogender;
    public String olevel;
    public String oheart;
    public String odiamond;
    public String oxp;
    public String ofriends;

    public String friends;
    public String gender;
    public String age;
    public String whatsupp;

    public boolean ispk;
    public boolean pksender;
    public Timestamp timestamp;
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

    public long localpoint;
    public long remotepoint;

    public long pkduration;
    public long pkstarttime;

    public Map<String, EntityCall> calls;

    public BroadList() {}


}
