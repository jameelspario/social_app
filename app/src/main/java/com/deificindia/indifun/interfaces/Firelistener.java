package com.deificindia.indifun.interfaces;

import com.deificindia.firebaseModel.AddLiveModel;
import com.deificindia.indifun.Model.abs_plugs.JoinerName;
import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.deificpk.modals.CallModal;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;

import java.util.List;

public class Firelistener {

    public interface OnLiveBroad{
        void onLive(List<BroadList> obj);
    }

    public interface FireCallListener{
        void onReceiveCall(CallModal modl);
    }

    public interface OnLiveUserClick{
        void onUserSelected(RoomUserInfo info);
    }



    public interface OnRoomUserInfo{
        void roomUserInfo(RoomUserInfo info);
    }

    public interface OnRoomUserList{
        void roomUserInfo(List<RoomUserInfo> info);
    }

    public interface OnLevel{
        void onLevel(long level);
    }

    public interface OnJoinerNameData{
        void onJoinerName(JoinerName data);
    }

    public interface OnCallRead{
        void onCalls(List<EntityCall> list);
    }

}
