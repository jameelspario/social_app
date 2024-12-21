package com.deificindia.indifun.deificpk.utils;

import android.util.Log;

import com.twilio.video.G722Codec;
import com.twilio.video.H264Codec;
import com.twilio.video.IsacCodec;
import com.twilio.video.OpusCodec;
import com.twilio.video.PcmaCodec;
import com.twilio.video.PcmuCodec;
import com.twilio.video.Vp8Codec;
import com.twilio.video.Vp9Codec;

public class Const {

    public static final String TAG="AbstractLiveFire";
    public static final int SEARCHTIME = 30000;

    public static final int TYPE_CHAT = 1;
    public static final int TYPE_FULLSCREENGIFT = 2;
    public static final int TYPE_HEART_GIFT = 3;
    public static final int TYPE_ENTER = 3;
    public static final int TYPE_SYSTEM = 5;

   /* public static final int STAT_BROADCAST = 1;
    public static final int STAT_PK_INVITE = 2;
    public static final int STAT_PK_MODE = 3;
    public static final int STAT_REJECT_PK = 4;
    public static final int STAT_PK_RESULT_MODE = 5;
    public static final int STAT_RND_PK = 6;*/

    public static final int PK_RESULT_LOSE = 0;
    public static final int PK_RESULT_WIN = 1;
    public static final int PK_RESULT_DRAW = 2;
    public static final int PK_RESULT_UNKNOWN = 3;

    public static final String KEY_BUNDLE = "KEY_BUNDLE";
    public static final String KEY_BUNDLE_CLIP_ID = "KEY_BUNDLE_CLIP_ID";
    public static final String KEY_BUNDLE_CLIP_TAB = "KEY_BUNDLE_CLIP_TAB";
    public static final String KEY_BUNDLE_IS_OWNER = "KEY_BUNDLE_IS_OWNER";

    public static final int KEY_TAB_FRIEND = 1;
    public static final int KEY_TAB_RECOMENDED = 2;
    public static final int KEY_TAB_UNIVERSAL = 3;

    public static final String KEY_CLIP_DATA = "key_clip_data";
    public static final String KEY_IS_OWNER = "isowner";
    public static final String KEY_OWNER_UID = "isowneruid";
    public static final String KEY_ROOM_UID = "key_room_uid";

    public static final String KEY_ROOM_TITLE = "roomtitle";
    public static final String BRAODCAST_ID = "braodcast_id";
    public static final String KEY_OWNER_AVTAR = "owner_avtar";
    public static final String KEY_OWNER_AVTAR_TYPE = "KEY_OWNER_AVTAR_TYPE";

    //public static final String KEY_OWNER_UID2 = "isowneruid2";
    public static final String KEY_OWNER_NAME = "isownername";

    //public static final String KEY_ROOM_UID = "roomuid";

    //public static final String KEY_ROOM_TYPE = "key_room_type";
    //public static final String KEY_IS_MUTED = "key_is_muted";


    /*-- call constants ---*/
    public static boolean amIOnCall = false;
    public static int myCallStatus = -1;
    public static int callCount = 2;

    public static long levelNoNNull(String level){
        return (level!=null && level.isEmpty())?Long.parseLong(level):0;
    }


    public static void loge(String... s){
        StringBuilder sb = new StringBuilder();

        for (String ss:s){
            sb.append(ss);
            sb.append(" | ");
        }

        Log.e("TAG", sb.toString());
    }

}
