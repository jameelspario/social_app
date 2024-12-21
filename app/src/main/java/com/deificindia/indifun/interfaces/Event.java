package com.deificindia.indifun.interfaces;

import com.deificindia.indifun.deificpk.modals.CallModal;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;

import java.util.List;
import java.util.Map;

public class Event {

    private static OnFilterUpdate onFilterUpdate;
    private static GlobalStateChange _GlobalStateChange;
    static Firelistener.FireCallListener fireCallListener;

    public static void setOnFilterUpdate(OnFilterUpdate listener){
        onFilterUpdate = listener;
    }

    public static void trigger(boolean ischanged){
        if(onFilterUpdate!=null) onFilterUpdate.onFileter(ischanged);
    }

    public static void setGlobalStateChange(GlobalStateChange listener){
        _GlobalStateChange = listener;
    }

    public static void trigger(int g){
        if(_GlobalStateChange!=null) _GlobalStateChange.onGlobalchange(g);
    }

    public static void fireCallListener(Firelistener.FireCallListener listener){
        fireCallListener = listener;
    }

    public static void fireCallListener(CallModal modl){
        if(fireCallListener!=null) fireCallListener.onReceiveCall(modl);
    }

}
