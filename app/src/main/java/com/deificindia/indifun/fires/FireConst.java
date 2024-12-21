package com.deificindia.indifun.fires;

public class FireConst {

    public static final String COLL_GO = "Broadcasters";
    public static final String SUB_COLL_GO_PK = "PkInfo";
    public static final String RANDOMPKCOLL = "RandomPkCall";
    public static final String USERS_JOINED = "UsersJoined";
    public static final String BROAD_CALLS = "Calls";
    public static final String CHATS = "Chats";
    public static final String GiftSender = "Gifting";
    public static final String Gifting = "Gifting";

    public static final String FILTER_KEY_JOINED_ROOM = "owner_broadcast_id";
    public static final String CHANGE_KEY = "changeto";

    public static final int CHANGE_BROAD = 0;
    public static final int CHANGE_PK = 2;
    public static final int CHANGE_CHAT = 3;
    public static final int CHANGE_CALL = 4;


    public interface SuccessListener<T>{
        void onSuccess(T param);
    }

    public interface FailureListner<T>{
        void onFail(T e);
    }


}
