package com.deificindia.indifun.fires;

import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.STAT_BROADCAST;
import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.STAT_CALL_MODE;
import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.STAT_REJECT_PK;
import static com.deificindia.indifun.deificpk.utils.Const.loge;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.MyDatasKt;
import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.db.LiveAppDb;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.db.LiveRepo;
import com.deificindia.indifun.deificpk.utils.Const;
import com.deificindia.indifun.fires.m.UserJoinedInfo;
import com.deificindia.indifun.modals.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FieldPath;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveFireFun {

    public static final String TAG = "LiveFireFun";

    public static LiveFireFun instance(){
        return new LiveFireFun();
    }

    /*Go live by owner*/

    public void update_live(String broad_room_name, String broad_room_id, String broad_usr_uid, String broad_usr_uid2,
                           String broad_usr_name,String broad_usr_avtar, long broad_usr_avtar_type, long broadcast_id, long state){

        Result cred = IndifunApplication.getInstance().getCredential();

        LiveEntity2 entity2 = new LiveEntity2();

        entity2.roomname = broad_room_name;
        entity2.roomid = broad_room_id;
        entity2.owneruid = broad_usr_uid;
        entity2.owneruidweb = cred.getId();
        entity2.userid = cred.getUid();
        entity2.ownername = cred.getFullName();
        entity2.owneravtar = broad_usr_avtar;
        entity2.owneravtartype = broad_usr_avtar_type;
        entity2.broadid = broadcast_id;

        entity2.isOwner = true;
        entity2.lattitude = cred.getLatitude();
        entity2.longitude = cred.getLongitude();

        entity2.oage = cred.getAge();
        entity2.ogender = cred.getGender();

        entity2.ispk = false;
        entity2.pksender = false;
        entity2.timestamp = System.currentTimeMillis();
        entity2.state = 1;

        entity2.is_online = 1;


        updateFire(entity2);



    }
    public void updateFire(LiveEntity2 entity2){
        FirebaseDatabase.getInstance()
                .getReference(FireConst.COLL_GO)
                .child(entity2.owneruid)
                .setValue(entity2)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //Toast.makeText(act, ""+broad_room_id, Toast.LENGTH_SHORT).show();
                    }
        });
    }

    public void readPkUsers(){

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(FireConst.COLL_GO);
        Query query = ref.orderByChild("state").equalTo(1);//equalTo(1, "state");


        LiveAppDb.databaseWriteExecutor.execute(()->{
            DataSnapshot snapshot = query.get().getResult();


        });

        //Query query = ref.equalTo(1, "state");

        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    //loge(TAG,"addoncomplete1 ",  new Gson().toJson(task.getResult().getValue()));
                    //GenericTypeIndicator<List<LiveEntity2>> live = new GenericTypeIndicator<List<LiveEntity2>>() {};
                    Object obj = task.getResult().getValue();
                    loge(TAG,"addoncomplete ",  new Gson().toJson(obj));

                }

            }
        }).addOnFailureListener(e -> {
            loge(TAG, "addOnFailureListener ",  e.getMessage());
        });

        /*ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                loge(TAG,"single time ", new Gson().toJson(snapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.addValueEventListener(new ValueEventListener() {
                @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loge(TAG,"onDataChange ", new Gson().toJson(snapshot.getValue()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }

    static Map<String, Object> getMap(int n){
        Map<String, Object> map2 = new HashMap<>();
        map2.put(FireConst.CHANGE_KEY, n);
        return map2;
    }

    public void inviteUserForPk(String local_owner_uid, String remote_uid, Map<String, Object> pkinfo,
                                int stat,
                                FireConst.FailureListner<String> listener){

        Map<String, Object> map= getMap(FireConst.CHANGE_PK);
        map.put("state", stat);
        map.put("pksender", false);

        map.put(FireConst.SUB_COLL_GO_PK, pkinfo);

        FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
                .child(remote_uid).child("state").get()
                .addOnCompleteListener(task -> {
                    try {
                        if (task.getResult().getValue(Long.class) == 1) {

                            FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
                                    .child(remote_uid)
                                    .updateChildren(map);

                            change_sender_state(local_owner_uid, stat,true);

                        }
                    }catch (Exception e){
                        if(listener!=null) listener.onFail("");
                        FirebaseCrashlytics.getInstance().recordException(e);
                    }
                }).addOnFailureListener(e -> {
                    if(listener!=null) listener.onFail("");
                });

    }

    //primary owner setting ==> on invite send => send state ==2
    private void change_sender_state(String uid, long stat, boolean issender){
        Map<String, Object> map= getMap(FireConst.CHANGE_PK);
        map.put("state", stat);
        map.put("pksender", issender);
        map.put(FireConst.SUB_COLL_GO_PK, null);

        updateMapToBroadUserNode(uid, map);
    }

    /*cancel pk => send state ==1*/
    public void resumetobroadstate(String loca_fuid, String remote_fuid, boolean issender, int stat){

        Map<String, Object> map= getMap(FireConst.CHANGE_PK);
        map.put("state", stat);
        map.put("pksender", issender);
        map.put(FireConst.SUB_COLL_GO_PK, null);

        updateMapToBroadUserNode(loca_fuid, map);
        updateMapToBroadUserNode(remote_fuid, map);
    }

    ///accepting pk request///
    public void acceptInvitationSecOwner(String local_owner_uid, String remote_uid,
                                         Map<String, Object> pkinfo, int state){

        Map<String, Object> map= getMap(FireConst.CHANGE_PK);
        map.put("state", state);
        map.put(FireConst.SUB_COLL_GO_PK, pkinfo);

        updateMapToBroadUserNode(remote_uid, map);


        Map<String, Object> map_local= new HashMap<>();
        map_local.put("state", state);

        updateMapToBroadUserNode(local_owner_uid, map_local);

    }

    public void rejectPkInvitation(String local_owner_uid, String remote_uid,
                                                  Map<String, Object> pkinfo){

        Map<String, Object> map= getMap(FireConst.CHANGE_PK);

        /*to primery owner - remote owner - pk invite sender*/
        map.put("state", STAT_REJECT_PK);
        map.put(FireConst.SUB_COLL_GO_PK, pkinfo);

        updateMapToBroadUserNode(remote_uid, map);

        /*to sec. owner - ownself*/
        map.put("state", STAT_BROADCAST);
        map.put(FireConst.SUB_COLL_GO_PK, null);
        updateMapToBroadUserNode(local_owner_uid, map);


    }





    private void updateMapToBroadUserNode(String user_uid, Map<String, Object> map){
        FirebaseDatabase.getInstance()
                .getReference(FireConst.COLL_GO)
                .child(user_uid)
                .updateChildren(map);
    }

    public void onPkRejectedBySecOwner(String local_owner_uid, int state){
        Map<String, Object> map= getMap(FireConst.CHANGE_BROAD);
        map.put("state", state);
        map.put("pksender", false);
        map.put(FireConst.SUB_COLL_GO_PK, null);

        updateMapToBroadUserNode(local_owner_uid, map);
    }

    /*--------call mode---------------------------*/
    public void start_call_mode(String owner_fuid, String call_type){
        Map<String, Object> map = getMap(FireConst.CHANGE_BROAD);
        map.put("state", STAT_CALL_MODE);
        map.put("callcount", Const.callCount);
        map.put("calltype", call_type);

        FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
                .child(owner_fuid)
                .updateChildren(map);

    }

    public void joinCall(EntityCall data){
        updateToLive(FireConst.CHANGE_CALL, data.owner, data, FireConst.BROAD_CALLS, data.jfuid);
    }

    public void acceptCall_or_rejectCall(String owneruid, String joineruid, long state){

        Map<String, Object> map2 = new HashMap<>();
        map2.put("state", state);

        //updateToLive(FireConst.CHANGE_CALL, owneruid, map2, FireConst.BROAD_CALLS, joineruid);
        FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
                .child(owneruid).child(FireConst.BROAD_CALLS)
                .child(joineruid)
                .updateChildren(map2);
    }

    public void closeCall_mode(String owneruid){
        Map<String, Object> map = LiveFireFun.getMap(FireConst.CHANGE_BROAD);;
        map.put("state", STAT_BROADCAST);
        map.put("callcount", 0);
        map.put("calltype", "");
        map.put(FireConst.BROAD_CALLS, null);

        FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
                .child(owneruid)
                .updateChildren(map);

    }

    public void updatePkIdAndStartTime(String owner1, Map<String, Object> map) {

        FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
                .child(owner1)
                .updateChildren(map);
    }

    public void updateToLive(int update_to, String owner, Object data, String... path){
        new Thread(()->{
            Map<String, Object> map2 = getMap(update_to);

            StringBuilder sb = new StringBuilder();
            for (int i=0; i<path.length; i++){
                sb.append(path[i]);

                if(i == path.length-1) break;
                sb.append("/");
            }

            map2.put(sb.toString(), data);

            FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
                    .child(owner)
                    .updateChildren(map2);

        }).start();
    }




}
