package com.deificindia.indifun.fcm;

import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Logger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class Config {
    public static final String NEWS = "notice";
    public static void subscribetopic(){
        String mobile = IndifunApplication.getInstance().getCredential().getContact().replace("+", "");
        Logger.loge("TAG","topic", mobile);
        if(mobile!=null)
            FirebaseMessaging.getInstance().subscribeToTopic(mobile);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            FirebaseMessaging.getInstance().subscribeToTopic(
                    FirebaseAuth.getInstance().getCurrentUser().getUid()
            );
        }
        FirebaseMessaging.getInstance().subscribeToTopic(Config.NEWS);
    }
    public static void unsubscribetopic(){
        String mobile = IndifunApplication.getInstance().getCredential().getContact().replace("+", "");
        Logger.loge("TAG","topic", mobile);
        if(mobile!=null)
            FirebaseMessaging.getInstance().unsubscribeFromTopic(mobile);
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Config.NEWS);
    }
}
