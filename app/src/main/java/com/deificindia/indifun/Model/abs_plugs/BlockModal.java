package com.deificindia.indifun.Model.abs_plugs;

import com.deificindia.indifun.Model.abs.Abs;

public class BlockModal{

    /*
    * "id": "3",
"user1": "555",
"user2": "590",
"is_blocked": "1",
"is_mute": "1",
"is_kick": "1",
"blocked_time": "1612528030968",
"mute_time": "1612528140856",
"kick_time": "1612527923996",
"entry_date": "2021-02-04",
"entry_time": "05:53:00 PM",
"type": "2"
    * */

    public int id;
    public int type;
    public String user1; //blockedby_uid
    public String user2; //blocked_uid
    public String is_blocked;
    public int is_mute;
    public int is_kick;
    public long blocked_time;
    public long mute_time;
    public long kick_time;
    public String entry_date;
    public String entry_time;
}
