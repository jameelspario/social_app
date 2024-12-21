package com.deificindia.indifun.rest;


import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.interfaces.IndiFunResult;

import java.io.InputStream;

public class RetroCallListener {

    public interface OnSuccessListener{
        void onSuccessResult(int type_result, Object obj);
    }

    public interface OnSuccessListenerV2<T>{
        void onSuccessResult(int type_result, T obj2);
    }

    public interface OnFailListener{
        void onError(int type_result, int code, String message);
    }

    public interface OnInputStream{
        void onResult(InputStream inputStream, LottieAnimationView lottieAnimationView);
        void onError(String err);
    }

    public static final int ONFOLLOWCLICK = 1;

    public static final int FOLLOW_POST = 2;
    public static final int USER_PROFILE = 3;
    public static final int GET_PROFILE_PHOTOS = 4;
    public static final int ADD_PROFILE_PHOTOS = 5;
    public static final int USER_PROFILE_UPDATE = 6;
    public static final int USER_INTERESTS = 7;
    public static final int LANGUAGE_LIST = 8;
    public static final int GET_POST = 9;
    public static final int BROADCAST_BETWEEN = 10;
    public static final int GENERATE_TOKEN = 11;
    public static final int REQUEST_TOKEN = 12;
    public static final int GET_USER_LEVEL = 13;
    public static final int HEART_POINT = 14;

    //leaderBoardDiamond >> 110-210-310--610
    //leaderBoarcoin    >> 110-210-310--610
    //leaderBoarfollower >> 110-210--610
    public static final int COIN_RECORD = 15;
    public static final int CHECKINTIME = 16;
    public static final int USERBROADLEVEL = 17;
    public static final int USERLEVEL = 18;
    public static final int USERPROFILE = 19;
    public static final int USERXPBYFIREBASEUID = 20;

    public static final int IS_BROADCASTING = 21;
    public static final int ADDBROADCASTE = 22;
    public static final int UPDATE_BROADCASTING = 23;
    public static final int JOINER_NAME = 24;
    public static final int GIFT_DURING_BROAD = 25;
    public static final int GET_SERVER_TIME = 26;
    public static final int PK_INVITATION = 27;
    public static final int BLOCKUSER = 28;
    public static final int UNBLOCKLIST = 29;
    public static final int UNFOLLOWCLICK = 30;
    public static final int GET_ZIP_GIFTS = 31;
    public static final int MUSIC_LIST = 32;
    public static final int TRENDING = 33;
    public static final int NEWSTAR_INDIA = 34;
    public static final int POST_FOLLOW = 35;
    public static final int POST_PUBLIC = 36;
    public static final int POST_RECOMMENDED = 37;
    public static final int LEVEL_DATA = 38;
    public static final int GIFT_SENDER_LIST = 39;
    public static final int ENDPK = 40;




}
