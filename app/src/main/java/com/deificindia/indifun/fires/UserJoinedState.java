package com.deificindia.indifun.fires;

import static com.deificindia.indifun.fires.FireConst.FILTER_KEY_JOINED_ROOM;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Utility.Indifun;
import com.deificindia.indifun.Utility.Logger;
import com.deificindia.indifun.fires.m.UserJoinedInfo;
import com.deificindia.indifun.rest.RetroCallListener;
import com.deificindia.indifun.rest.RetroCalls;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserJoinedState {

    WeakReference<Context> context;
    UserJoinedInfo local_user_info;

    ChildEventListener childEventListener_joined;
    DatabaseReference databaseReference_joined;
    Query query;

    Map<String, UserJoinedInfo> userJoinedInfoList = new HashMap<>();
    UserJoinedListener userJoinedListener;



    private UserJoinedState(Context context) {
        databaseReference_joined = FirebaseDatabase.getInstance().getReference(FireConst.USERS_JOINED);
        this.context = new WeakReference<>(context);
    }

    public static UserJoinedState instance(Context cnx){
        return new UserJoinedState(cnx);
    }


    private void usersJoinedRoomListener(){
        userJoinedInfoList.clear();

        query = databaseReference_joined.orderByChild(FILTER_KEY_JOINED_ROOM).equalTo(local_user_info.room_id);

        childEventListener_joined = query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                UserJoinedInfo userJoinedInfo = snapshot.getValue(UserJoinedInfo.class);
                //Logger.loge("Userjoined ", Logger.toGson(userJoinedInfo));
                if(userJoinedInfo!=null) {
                    boolean condition = userJoinedInfo.user_broad_identity.equals(String.format("%s%s", userJoinedInfo.user_fuid, local_user_info.room_owner_fuid));
                 //   Logger.loge("Userjoined ", ""+condition);
                    if (condition && !userJoinedInfoList.containsKey(userJoinedInfo.user_fuid)) {
                   //     Logger.loge("Userjoined ", "joined");
                        userJoinedInfoList.put(userJoinedInfo.user_fuid, userJoinedInfo);
                        trigger_user_joined(UserChanged.JOINED, userJoinedInfo);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserJoinedInfo userJoinedInfo = snapshot.getValue(UserJoinedInfo.class);
                if(userJoinedInfo!=null) {
                    boolean condition = userJoinedInfo.user_broad_identity.equals(String.format("%s%s", userJoinedInfo.user_fuid, local_user_info.room_owner_fuid));
                    if (condition) {
                        trigger_user_joined(UserChanged.CHANGED, userJoinedInfo);
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                UserJoinedInfo userJoinedInfo = snapshot.getValue(UserJoinedInfo.class);
                if(userJoinedInfo!=null) {
                    userJoinedInfoList.remove(userJoinedInfo.user_fuid);
                    trigger_user_joined(UserChanged.LEAVED, userJoinedInfo);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void removeJoinedRoomListener(){
        if(databaseReference_joined!=null && childEventListener_joined!=null)
            databaseReference_joined.removeEventListener(childEventListener_joined);

        userJoinedInfoList.clear();
    }

    private void trigger_user_joined(UserChanged state, UserJoinedInfo info){
        if(userJoinedListener!=null)
            userJoinedListener.onUserChange(state, info);
    }

    public interface UserJoinedListener{
        void onUserChange(UserChanged state, UserJoinedInfo users);
    }

    public UserJoinedState joinRoom(UserJoinedInfo info) {
        this.local_user_info = info;
        //databaseReference_joined.child(info.user_fuid).setValue(info);

        RetroCalls.instance()
                .stringParam(new String[]{
                        info.user_broad_identity,
                        info.owner_broadcast_id,
                        info.room_id,
                        info.room_owner_fuid
                }).join_broad((type_result, obj2) -> { });

        usersJoinedRoomListener();
        return this;
    }

    /* -- controlles -- */

    public enum UserChanged{
        JOINED, LEAVED, CHANGED
    }

    public UserJoinedState setUserJoinedListener(UserJoinedListener userJoinedListener) {
        this.userJoinedListener = userJoinedListener;
        return this;
    }

    public List<UserJoinedInfo> getUserJoinedInfoList() {
        return new ArrayList<>(userJoinedInfoList.values());
    }

    public void leaveRoom(){
        removeJoinedRoomListener();
        databaseReference_joined.child(local_user_info.user_fuid).removeValue();
    }



}
