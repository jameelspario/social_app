package com.deificindia.indifun.deificpk.modals;

public class PkInfo{
    public long pkid;

    public String pkroomid;
    public String pkuseruid;
    public String pkuseruidweb;
    public String pkusername;
    public String pkavtar;
    public long pkavtartype;
    public long pkbroadid;
    public long pkpkid;


    public boolean pksender;
    public boolean ispk;
    public long pkduration;
    public long pkstarttime;
    public long localpoint;
    public long remotepoint;
    public long state;

    public PkInfo() {}

    public boolean isNotNull(){
        return pkroomid!=null;
    }
}