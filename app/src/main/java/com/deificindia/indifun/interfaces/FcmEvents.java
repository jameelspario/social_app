package com.deificindia.indifun.interfaces;

import com.google.firebase.messaging.RemoteMessage;

public class FcmEvents {

    static OnFcmMessage _listener;

    public static void setOnFcmMessage(OnFcmMessage listener){
        _listener = listener;
    }

    public interface OnFcmMessage{
        void  onMessage(RemoteMessage remoteMessage);
    }

    public static void triggerEvent(RemoteMessage remoteMessage){
        if(_listener!=null)
            _listener.onMessage(remoteMessage);
    }
}
