package com.deificindia.indifun.deificpk.modals;


import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.TYPE_SYSTEM;
import static com.deificindia.indifun.deificpk.utils.Const.TYPE_CHAT;
import static com.deificindia.indifun.deificpk.utils.Const.TYPE_FULLSCREENGIFT;
import static com.deificindia.indifun.deificpk.utils.Const.TYPE_HEART_GIFT;

import com.deificindia.indifun.Model.retro.ChatModel_Result;
import com.deificindia.indifun.modals.Result;
import com.google.firebase.auth.FirebaseAuth;

//broadcasters/uid/chat/{rnd}/
public class ChatModal {
    public String room;
    public String owner;
    public long time;
    public long type;
    public String message = "";
    public String senderuid;
    public String senderuidweb;
    public String level;
    public String sendername;
    public String senderavtar;
    public long senderxp;

    public long giftid = 0;
    public String giftcat = "";
    public String giftname = "";
    public String giftanim = "";
    public long gifttype = 0;
    public long giftpoing = 0;

    public long system = 0;
    public String tofuid = "";
    public String towuid = "";
    public String toname = "";

    public ChatModal() { }

    public static ChatModal instance(){
        return new ChatModal();
    }

    public ChatModal buildHeart(String broad_room_id, String broad_usr_uid, Result credentials, long xp){
        this.room = broad_room_id;
        this.owner = broad_usr_uid;
        this.time = System.currentTimeMillis();
        this.type = TYPE_HEART_GIFT;
        this.message = "";

        userDetails(credentials, xp);

        return this;
    }

    public ChatModal buildGift(String broad_room_id, String broad_usr_uid,
                                  Result credentials, long xp, GiftInfo2 info){
        this.room = broad_room_id;
        this.owner = broad_usr_uid;
        this.time = System.currentTimeMillis();
        this.type = TYPE_FULLSCREENGIFT;
        this.message = "";

        userDetails(credentials, xp);

        this.giftid = info.id;
        this.giftcat = String.valueOf(info.gift_category);
        this.giftanim = info.animation;
        this.giftname = info.name;
        this.gifttype = info.type;
        this.giftpoing = info.point;

        return this;
    }

    public ChatModal buildMessage(String toString, String broad_room_id, String broad_usr_uid, Result credentials, long xp){
        this.room = broad_room_id;
        this.owner = broad_usr_uid;
        this.time = System.currentTimeMillis();
        this.type = TYPE_CHAT;
        this.message = toString;

        userDetails(credentials, xp);


        return this;
    }

    public ChatModal buidSystem(String toString,
                                String broad_room_id,
                                String broad_usr_uid,
                                long xp,
                                long system,
                                String toFuid,
                                String toWuid,
                                String toName,
                                Result credentials){
        this.room = broad_room_id;
        this.owner = broad_usr_uid;
        this.time = System.currentTimeMillis();
        this.type = TYPE_SYSTEM;
        this.message = toString;

        userDetails(credentials, xp);

        this.system = system;
        this.tofuid = toFuid;
        this.towuid = toWuid;
        this.toname = toName;

        return this;
    }
    private void userDetails(Result credentials, long xp){
        this.senderuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.senderuidweb = credentials.getId();
        this.sendername = credentials.getFullName();
        this.senderavtar = credentials.getProfilePicture();
        this.senderxp = xp;
    }
}
