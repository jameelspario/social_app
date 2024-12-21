package com.deificindia.indifun.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.Model.GetFollow_Result;
import com.deificindia.indifun.Model.GiftSenderList_Response;
import com.deificindia.indifun.Model.GiftSenderList_Result;
import com.deificindia.indifun.Model.abs.DataMod;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Model.abs_plugs.SingleOnlineData;
import com.deificindia.indifun.Model.abs_plugs.ZipGiftItem;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.ListSorting;
import com.deificindia.indifun.Utility.MyDatasKt;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.Partition;
import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.db.LiveEntity;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.db.LiveRepo;
import com.deificindia.indifun.db.LiveRepo2;
import com.deificindia.indifun.db.TempRepo;
import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.deificpk.modals.ChatModal;
import com.deificindia.indifun.deificpk.modals.GiftListResponse2;
import com.deificindia.indifun.deificpk.modals.MusicInfo;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;
import com.deificindia.indifun.deificpk.utils.Const;
import com.deificindia.indifun.fires.FireConst;
import com.deificindia.indifun.fires.FireFun;
import com.deificindia.indifun.fires.LiveFireFun;
import com.deificindia.indifun.interfaces.Event;
import com.deificindia.indifun.modals.Post;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.LoadInterface;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.rest.RetroConfig2;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun.Utility.Constants.*;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.STAT_BROADCAST;
import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.STAT_CALL_MODE;
import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.TYPE_CALL;
import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.TYPE_SYSTEM;
import static com.deificindia.indifun.fires.FireFun.BROADCASTERS;
import static com.deificindia.indifun.rest.RetroCallListener.BLOCKUSER;
import static com.deificindia.indifun.rest.RetroCallListener.GET_ZIP_GIFTS;
import static com.deificindia.indifun.rest.RetroCallListener.GIFT_DURING_BROAD;
import static com.deificindia.indifun.rest.RetroCallListener.GIFT_SENDER_LIST;
import static com.deificindia.indifun.rest.RetroCallListener.JOINER_NAME;
import static com.deificindia.indifun.rest.RetroCallListener.MUSIC_LIST;
import static com.deificindia.indifun.rest.RetroCallListener.PK_INVITATION;
import static com.deificindia.indifun.rest.RetroCallListener.UPDATE_BROADCASTING;

/*
* /* Intent i = new Intent(THREADEVENT);
    i.putExtra(SHUTDOWN, broad_usr_uid);
    LocalBroadcastManager.getInstance(this).sendBroadcast(i);
* */
public class ControllService extends Service {

    public static final String TAG = "Service";

    static int processors = Runtime.getRuntime().availableProcessors();
    static final ExecutorService executor = Executors.newFixedThreadPool(processors);

    private final CompositeDisposable disposables = new CompositeDisposable();

    Result cread;
    String local_fuid;
    long broad_id;

    ListenerRegistration deleteEvent;
    public ControllService() { }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        cread = IndifunApplication.getInstance().getCredential();
        local_fuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().clearPersistence();
        changeOnlineStatus(cread.getId(), 1);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastlistener, new IntentFilter(THREADEVENT));

        LiveRepo.clear(getApplicationContext());
        LiveRepo.clear_call(getApplicationContext());
        get_joinerNameInfo(local_fuid);
        //get_levels();
        get_all_gift_list();
        //clearFireData(); //clear chat and start live listener for broad user
        getZipList();
        load_music();
        get_level_table();

        readLiveBroadcasters();

        //listenCall();
        return START_STICKY;
    }

    protected IndifunApplication application() {
        return (IndifunApplication) getApplicationContext();
    }

    Runnable runnable = () -> {
        new Thread(()->{
            GiftSenderList_Response data = RetroCalls.instance().callType(GIFT_SENDER_LIST)
                    .long_params(broad_id)
                    .gift_sender_list();

            if(data!=null && data.getStatus()==1 && data.getResult()!=null){
                filterGiftSenderResult(data);
            }

            //refreshUserList(); //recycle
        }).start();
    };

    /*void clearFireData(){
        FirebaseFirestore.getInstance().collection(BROADCASTERS)
                .document(local_fuid).delete();

        deleteEvent = FirebaseFirestore.getInstance().collection(BROADCASTERS)
                .document(local_fuid)
                .collection(CHATS)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            if (deleteEvent!=null) deleteEvent.remove();
                            liveDataListener();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            String id = dc.getDocument().getId();
                            if(id!=null && dc.getDocument().getDocumentReference(id)!=null)
                                dc.getDocument().getDocumentReference(id).delete();
                        }

                        if (deleteEvent!=null) deleteEvent.remove();

                        liveDataListener();
                    }
                });
    }*/

    private BroadcastReceiver broadcastlistener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent ) {
            if(intent!=null){
                //int tye = intent.getIntExtra("TYPE", 0);
                ControllModal data = intent.getParcelableExtra(CONTROLLTHREAD);
                if(data!=null){
                    filterAction(data);
                    loge( TAG, "Thrad", "", toGson(data));
                }
            }
        }
    };

    private void event(){
        /* ((MyApplication) getApplication())
                        .bus()
                        .send(new Events.TapEvent());
        * */
        disposables.add(((IndifunApplication) getApplication())
                .bus()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) {
                        if (object instanceof ControllModal) {
                            filterAction((ControllModal) object);
                        }/* else if (object instanceof Events.TapEvent) {
                            //textView.setText("Tap Event Received");
                        }*/
                    }
                }));

    }

    DatabaseReference call_reference;

    public void deleteLiveBroad2(String broad_usr_fuid, String braod_usr_wuid, String broadid){
        loge(TAG, "deleteLiveBroad:", "run", broad_usr_fuid, braod_usr_wuid, broadid);

        endBroadOnWeb(braod_usr_wuid, broadid);
    }

//    private void uploadImage_124() {
//        String fir=FirebaseAuth.getInstance().getCurrentUser().getUid();
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        database.getReference("GOLIVE1")
//                .child(fir)
//                .removeValue();
//    }

//    public void deletef(){
//
//         FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        database.getReference("Go") //delete live
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .removeValue();
//    }


    public void deleteLiveChatBroad2(String broad_usr_fuid, String braod_usr_wuid, String broadid){
        loge(TAG, "deleteLiveBroad:", "run", broad_usr_fuid, braod_usr_wuid, broadid);
        FirebaseFirestore.getInstance().collection(BROADCASTERS)
                .document(broad_usr_fuid)
                .collection("chats")
                .document()
                .delete();

        endBroadOnWeb(braod_usr_wuid, broadid);
    }



    public void endBroadOnWeb(String braod_usr_wuid, String broadid){
       // ApiCall1.instance().client().UPDATE_BROADCASTING(braod_usr_wuid, broadid);
        RetroCalls.instance()
                .callType(UPDATE_BROADCASTING)
                .stringParam(braod_usr_wuid, broadid)
                .setOnFail((t,c, m)->{

                }).update_broadcasting((t, o)->{

        });
    }

    String msg ="";
    void bloackUser(String roomId_blockedFrom, String wuserId, String fuserId, String toname, int blocktype){
        switch (blocktype){
            case 1: msg = "User is blocked"; break;
            case 2: msg = "User is muted in your broad for 24 hours"; break;
            case 3: msg = "User is kicked out from your broad and can't join for 48 hours."; break;
            default: msg = "";
        }

        RetroCalls.instance().callType(BLOCKUSER)
                .withUID()
                .stringParam(wuserId)
                .intParam(blocktype)
                .blockuser((a,b)->{
                   // loge(TAG, "bloackUser1", msg, roomId_blockedFrom);
                    if(b!=null && b.getStatus()==1){
                       // loge(TAG, "bloackUser", msg, roomId_blockedFrom);
                        if(!msg.isEmpty()) Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        sendSystemMessage(msg,
                                roomId_blockedFrom,
                                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                0,
                                blocktype,
                                fuserId,
                                wuserId,
                                toname

                        );
                        msg="";
                    }
                });
    }
    ChildEventListener call_event;
    //////////Call recver////////////////
    protected void canceleCall(String cancelby, String cancelto, int b){
        loge(TAG,"canceleCall",  cancelby, cancelto);

        Map<String, Object> map = new HashMap<>();
        map.put("iscalling", false);
        if(b==1) map.put("istalking", false);

        setData(cancelby, map);
        //setData(cancelto, map);
    }

    private void setData(String doc, Map<String, Object> map){
        if(doc==null) return;
        FirebaseFirestore.getInstance().collection("Calls")
                .document(doc)
                .set(map, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {

                }).addOnFailureListener(e -> {

        });
    }

    @Override
    public void onDestroy() {
        changeOnlineStatus(cread.getId(), 0);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastlistener);
        if(livedata!=null) livedata.remove();
        super.onDestroy();
        //uploadImage_124();
        //uploadImage_sender();
        //  deletef();

    }

    public void sendSystemMessage(String toString, String broad_room_id,
                                         String broad_usr_uid,
                                         long xp, long system,
                                  String toFuid,
                                  String toWuid,
                                  String toName
    ) {
        Result credentials = IndifunApplication.getInstance().getCredential();
        /*FireFun.sendMessage(
                broad_room_id,
                broad_usr_uid,
                TYPE_SYSTEM,
                toString,
                broad_usr_uid,
                credentials.getId(),
                credentials.getFullName(),
                credentials.getProfilePicture(),
                xp,
                0,
                "",
                "",
                "",
                0,
                0,
                system, toFuid, toWuid, toName

        );*/

        FireFun.sendMessage(ChatModal.instance().buidSystem(
                toString,
                broad_room_id,
                broad_usr_uid,
                xp,
                system, toFuid, toWuid, toName, credentials));
    }

    private void filterAction(ControllModal data){
        loge(TAG, data.data, data.dataone, data.datato);
        switch (data.TYPE){
            case ENDWEBBROACAST: //ending broad before starting new broad
                endBroadOnWeb(data.data, data.dataone);
               // deletef();
              ///  uploadImage_124();
                break;
            case REMOVEBROACAST:
                deleteLiveBroad2(data.data, data.dataone, data.datato);
              //  deletef();
               // uploadImage_124();
                break;
            case STARTCALLLISTENER:
                break;
            case ENDCALL:
                //end call rcvr
                //canceleCall(data.data, data.dataone, data.bool);
                //if(listenerRegistration!=null) listenerRegistration.remove();
                break;
            case LIVEHOT: break;
            case LIVEFOLLOW: break;
            case LIVERECOMEDED: break;
            case UPDATEGIFTPOINT:
                //executor.submit(() -> gift_during_broad(data.ints[0], data.ints[1], data.strs[0], data.strs[1]));
                break;
            case GETTOPGIFTER:
                giftsenderlist(data.longs[0]);
                break;
            case BLOACKUSER:
                bloackUser(data.strs[0], data.strs[1], data.strs[2],data.strs[3], data.ints[0]);
                break;
            case UPDATESERVERTIMEANDPKID:
                updateservertimeandpkid(data);
                break;
            case RESETUPDATESERVERTIMEANDPKID:
                //resetupdateservertimeandpkid(data.strs[0]);
                break;
            case CALLTOUSER:
                calltouser(data);
                break;
            case EDNCALLSTAT:
                cahngeState(data.strs[0]);
                break;
            case GET_JOINERNAMEINFO:
                get_joinerNameInfo(data.strs[0]);
                break;
            case STARTCALLMODE:
                //start_call_mode(data);
                break;
            case JOIN_CALL:
                //joinCall(data);
                break;
            case ACCEPTCALL_OR_REJECTCALL:
                acceptCall_or_rejectCall(data);
                break;
            case CLOSE_CALL_MODE:
                closeCall_mode(data);
                break;

        }
    }

    private void readLiveCall(String owner, String roomid){
        if(owner==null) return;

        call_reference = FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
                .child(owner).child(FireConst.BROAD_CALLS);

        call_event = call_reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                EntityCall entityCall = snapshot.getValue(EntityCall.class);

                loge(TAG, "Call", new Gson().toJson(entityCall));
                if(entityCall==null) return;

                entityCall.room = roomid;
                LiveRepo.updateoradd_call(getApplicationContext(), entityCall);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                EntityCall entityCall = snapshot.getValue(EntityCall.class);
                if(entityCall==null) return;

                entityCall.room = roomid;
                LiveRepo.updateoradd_call(getApplicationContext(), entityCall);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                EntityCall entityCall = snapshot.getValue(EntityCall.class);
                if(entityCall==null) return;
                LiveRepo.clear_call(getApplicationContext(), entityCall.jfuid);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    ListenerRegistration livedata;
    void liveDataListener(){
      String uid1 = FirebaseAuth.getInstance().getCurrentUser().getUid();
      CollectionReference ref = FirebaseFirestore.getInstance().collection(BROADCASTERS);
      //final Query johnMessagesQuery = ref.whereNotEqualTo("owneruid", uid1);

        livedata = ref.addSnapshotListener((value, error) -> {
          if (error!=null){ return; }

          for (DocumentChange dc : value.getDocumentChanges()) {
              BroadList data = dc.getDocument().toObject(BroadList.class);
              //loge(TAG, "", toGson(data));
              switch (dc.getType()) {
                  case ADDED:
                      AddFireData(data);
                      break;
                  case MODIFIED:
                      updatedata(data);
                      break;
                  case REMOVED:
                      LiveRepo.updateIsOnline(getApplicationContext(), data.owneruid, 0);
                      clear_call(data);
                      break;

              }
          }
      });
    }

    void AddFireData(BroadList d){
        LiveEntity ent = new LiveEntity();
        ent.roomname = d.roomname;
        ent.roomid = d.roomid;
        ent.fuid = d.owneruid;
        ent.wuid = d.owneruidweb;
        ent.full_name = d.ownername;
        ent.image = d.owneravtar;
        ent.owneravtartype = d.owneravtartype;
        ent.broadid = d.broadid;
        ent.pkid = d.pkid;

        ent.age = d.oage;
        ent.gender = d.ogender;

        //ent.is_friend = -1;
        //ent.is_blocked = -1;
        //ent.is_following = -1;
        //ent.matched = -1;
        ent.is_online = 1;

        ent.ispk = d.ispk?1:0;
        ent.pksender = d.pksender?1:0;
        ent.pkduration = d.pkduration;
        ent.pkstarttime = d.pkstarttime;
        ent.state = d.state;
        ent.callcount = d.callcount;
        ent.calltype = d.calltype;

        ent.pkroomid = d.pkroomid;
        ent.pkuseruid = d.pkuseruid;
        ent.pkuseruidweb = d.pkuseruidweb;
        ent.pkusername = d.pkusername;
        ent.pkavtar = d.pkavtar;
        ent.pkavtartype = d.pkavtartype;
        ent.pkbroadid = d.pkbroadid;
        ent.pkpkid = d.pkpkid;

        LiveRepo.AddFireData(getApplicationContext(), ent);
        single_online_data_run(d.owneruidweb);

        if(d.state==7 && d.calls!=null){
            updateCallMap(d.calls, d.roomid);
        } else
            LiveRepo.clear_call(getApplicationContext());

    }

    void updatedata(BroadList d){
        loge(TAG, "updatedata", " "+new Gson().toJson(d));
        LiveEntity ent = new LiveEntity();
        /*ent.roomname = d.roomname;
        ent.roomid = d.roomid;*/
        ent.fuid = d.owneruid;
        ent.latitude = d.latitude;
        ent.longitude = d.longitude;
        /*ent.wuid = d.owneruidweb;
        ent.full_name = d.ownername;
        ent.image = d.owneravtar;
        ent.owneravtartype = d.owneravtartype;
        ent.broadid = d.broadid;*/

        ent.pkid = d.pkid;

        //ent.age = d.oage;
        //ent.gender = d.ogender;

        //ent.is_friend = -1;
        //ent.is_blocked = -1;
        //ent.is_following = -1;
        //ent.matched = -1;
        //ent.is_online = 0;

        ent.ispk = d.ispk?1:0;
        ent.pksender = d.pksender?1:0;
        ent.pkduration = d.pkduration;
        ent.pkstarttime = d.pkstarttime;
        ent.state = d.state;
        ent.callcount = d.callcount;
        ent.calltype = d.calltype;

        ent.pkroomid = d.pkroomid;
        ent.pkuseruid = d.pkuseruid;
        ent.pkuseruidweb = d.pkuseruidweb;
        ent.pkusername = d.pkusername;
        ent.pkavtar = d.pkavtar;
        ent.pkavtartype = d.pkavtartype;
        ent.pkbroadid = d.pkbroadid;
        ent.pkpkid = d.pkpkid;

        ent.localpoint = d.localpoint;
        ent.remotepoint = d.remotepoint;
        LiveRepo.updateFireData(getApplicationContext(), ent);
        single_online_data_run(d.owneruidweb);

        if(d.state==7 && d.calls!=null){
            updateCallMap(d.calls, d.roomid);
        }else {
            LiveRepo.clear_call(getApplicationContext());
        }
    }

    void clear_call(BroadList d){
        //LiveRepo.clear_call(getApplicationContext(), "");
    }

    void liveHot1(String userId){
        String str_uid = MySharePref.getUserId();
        Call<GetFollow_Result> call = AppConfig.loadInterface().hot_homepage();
        call.enqueue(new Callback<GetFollow_Result>() {
            @Override
            public void onResponse(Call<GetFollow_Result> call, Response<GetFollow_Result> response) {
                GetFollow_Result resdata = response.body();
                if(resdata!=null && resdata.getStatus()==1 && resdata.getResult()!=null){

                }else{

                }
            }

            @Override
            public void onFailure(Call<GetFollow_Result> call, Throwable t) {

            }
        });
    }
    void liveFollow(String userId){
        Call<GetFollow_Result> call = AppConfig.loadInterface().follow_homepage();
        call.enqueue(new Callback<GetFollow_Result>() {
            @Override
            public void onResponse(Call<GetFollow_Result> call, Response<GetFollow_Result> response) {
                GetFollow_Result resdata = response.body();
                if(resdata!=null && resdata.getStatus()==1 && resdata.getResult()!=null){

                }else{

                }
            }

            @Override
            public void onFailure(Call<GetFollow_Result> call, Throwable t) {

            }
        });
    }
    void liveRecommended(String userId){
        Call<GetFollow_Result> call = RetroConfig2.createService(LoadInterface.class,true)
                .follow_homepage_recommended();
        call.enqueue(new Callback<GetFollow_Result>() {
            @Override
            public void onResponse(Call<GetFollow_Result> call, Response<GetFollow_Result> response) {
                GetFollow_Result resdata = response.body();
                if(resdata!=null && resdata.getStatus()==1 && resdata.getResult()!=null){

                }else{

                }
            }

            @Override
            public void onFailure(Call<GetFollow_Result> call, Throwable t) {

            }
        });
    }

    void single_online_data_run(String remoteuid){
        //executor1.submit(() -> single_onlineuser_data(str_uid, remoteuid));
        //single_onlineuser_data(str_uid, remoteuid);
        new Thread(()->{
            single_onlineuser_data(cread.getId(), remoteuid);
        }).start();
    }


    void single_onlineuser_data(String userId, String iserIdRemote){
        //loge(TAG, "single_onlineuser_data", userId, iserIdRemote);
        Call<ObjectModal<SingleOnlineData>> call = AppConfig.loadInterface()
                .singleonline_user_data(userId, iserIdRemote);
        call.enqueue(new Callback<ObjectModal<SingleOnlineData>>() {
            @Override
            public void onResponse(Call<ObjectModal<SingleOnlineData>> call, Response<ObjectModal<SingleOnlineData>> response) {
                ObjectModal<SingleOnlineData> resdata = response.body();
                if(resdata!=null && resdata.getStatus()==1 && resdata.getResult()!=null){
                    //loge(TAG, "single_onlineuser_data 2", toGson(resdata.getResult()));
                    updateApiData(resdata.getResult());
                }
            }

            @Override
            public void onFailure(Call<ObjectModal<SingleOnlineData>> call, Throwable t) { }
        });
    }

    void updateApiData(SingleOnlineData d){
        LiveEntity ent = new LiveEntity();

        ent.fuid = d.getFirebaseUid();
        ent.friends = d.getNoOfFriends();
        ent.is_friend = d.getIsFriend();
        ent.is_blocked = d.getIsBlocked();
        ent.is_mute = d.getIs_mute();
        ent.is_kick = d.getIs_kick();
        ent.is_following = d.getIsFollowing();
        ent.matched = d.getIsMatched();
        ent.is_online = 1;
        ent.whatsapp = d.getWhatsup();
        ent.diamond = d.getDiamond();
        ent.heart = d.getHeart();
        ent.level = d.getLevel();
        ent.to_next_level = d.getToNextLevel();
        ent.latitude= d.getLatitude();
        ent.longitude= d.getLongitude();

        LiveRepo.updateApiData(getApplicationContext(), ent);
    }

    void liveHot2(List<String> fuids){
        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        final CollectionReference chatMessageReference = firestore.collection(BROADCASTERS);
        final Query johnMessagesQuery = chatMessageReference.whereIn("owneruid", Arrays.asList("USA", "Japan"));

        try {
            final QuerySnapshot querySnapshot = Tasks.await(johnMessagesQuery.get());
            final List<DocumentSnapshot> johnMessagesDocs = querySnapshot.getDocuments();


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////

    void updateservertimeandpkid(ControllModal data){
        RetroCalls.instance().callType(PK_INVITATION)
                .stringParam(String.valueOf(data.longs[0])/*broadcast id*/, String.valueOf(data.longs[1])/*pk duration*/,
                        data.strs[1]/*pk_with*/, data.strs[0]/*broadcaster firebase uid*/)
                .pk_invitation((type_result, obj2) -> {
                    if(obj2!=null && obj2.getResult()!=null) {
                        int pkid = obj2.getResult().id;
                        long pkstartTime = obj2.getResult().current_time;

                    }
                });
    }


    void resetupdateservertimeandpkid(String broaduserid){
        Map<String, Object> map= new HashMap<>();
        map.put("pkid", 0);
        map.put("pkpkid", 0);
        map.put("pkstarttime", 0);
        map.put("localpoint", 0);
        map.put("remotepoint", 0);
        //update2Broadata(broaduserid, map);
    }

    //////////////////////////////////////////////////////

    private void changeOnlineStatus(String uid, int status){
        AppConfig.loadInterface().change_online_status(uid, status)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) { }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) { }
                });
    }

    //////////////////////////live broad users//////////////////
    private void readLiveBroadcasters() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO);

        LiveRepo2.clearFireLive(getApplicationContext());
        
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //loge(TAG, "added", snapshot.getKey(), toGson(snapshot.getValue()));
                LiveEntity2 post = snapshot.getValue(LiveEntity2.class);

                if(post==null) return;

                switch (post.changeto){
                    case FireConst.CHANGE_PK: break;
                    case FireConst.CHANGE_CHAT: break;
                    case FireConst.CHANGE_CALL: break;

                }

                if(post.state==7 /*&& call_event==null*/){
                    //readLiveCall(post.owneruid, post.roomid);
                    Map<String, EntityCall> call_map = post.Calls;
                    if(call_map!=null) {

                        for (String key : call_map.keySet()) {
                            EntityCall entityCall = call_map.get(key);
                            if (entityCall != null)
                                LiveRepo.updateoradd_call(getApplicationContext(), entityCall);
                        }
                    }
                }else {
                    /*if(call_reference!=null && call_event !=null){
                        call_reference.removeEventListener(call_event);
                        call_reference = null;*/
                        LiveRepo.clear_call(getApplicationContext());
                    //}
                }

                if(post.PkInfo!=null){
                   //loge(TAG, "HasPk", post.PkInfo.pk_room_id);
                    post.pk_room = post.PkInfo.pk_room_id;
                    LiveRepo2.pk_update_insert(getApplicationContext(), post.PkInfo);
                }else {
                    post.pk_room = null;
                    LiveRepo2.pk_delete(getApplicationContext());
                }


                LiveRepo2.addFireLive(getApplicationContext(), post);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //loge(TAG, "changed", snapshot.getKey(), toGson(snapshot.getValue()));
                LiveEntity2 post = snapshot.getValue(LiveEntity2.class);


                if(post==null) return;

                switch (post.changeto){
                    case FireConst.CHANGE_PK:
                       // loge(TAG, "changed", "pk");
                        break;
                    case FireConst.CHANGE_CHAT:
                       // loge(TAG, "changed", "chat");
                        break;
                    case FireConst.CHANGE_CALL:
                       // loge(TAG, "changed", "call");
                        break;

                }

               // loge(TAG, "changed", "0");
                if(post.state==7/* && call_event==null*/){
                    //readLiveCall(post.owneruid, post.roomid);
                    Map<String, EntityCall> call_map = post.Calls;
                   // loge(TAG, "changed call 0", new Gson().toJson(post));
                    //loge(TAG, "changed call 1", new Gson().toJson(call_map));
                    if(call_map!=null) {

                        //loge(TAG, "changed call 2", call_map.toString());
                        for (String key : call_map.keySet()) {
                            EntityCall entityCall = call_map.get(key);
                            if (entityCall != null)
                                LiveRepo.updateoradd_call(getApplicationContext(), entityCall);
                        }
                    }
                }else {
                    /*if(call_reference!=null && call_event !=null){
                        call_reference.removeEventListener(call_event);
                        call_reference = null;*/
                        LiveRepo.clear_call(getApplicationContext());
                    //}
                }

                //loge(TAG, "changed pkinfo", new Gson().toJson(post.PkInfo));

                if(post.PkInfo!=null){
                    post.pk_room = post.PkInfo.pk_room_id;
                    LiveRepo2.pk_update_insert(getApplicationContext(), post.PkInfo);
                }else {
                    post.pk_room = null;
                    //LiveRepo2.pk_delete(getApplicationContext());
                }

                LiveRepo2.addFireLive(getApplicationContext(), post);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //loge(TAG, "removed",  snapshot.getKey(), toGson(snapshot.getValue()));

                LiveRepo2.removeFireLive(getApplicationContext(), snapshot.getKey());

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void giftsenderlist(long Broadid){
        this.broad_id = Broadid;
       // refreshUserList();
    }

    void filterGiftSenderResult(GiftSenderList_Response resdata){
        Map<String, Integer> map = new HashMap<>();
        //summing points.....
        for (GiftSenderList_Result result : resdata.getResult()) {
            //if(OUId>0){
            if(result!=null){
                if(map.get(result.getFrom())!=null) {
                    map.put(result.getFrom(),
                            map.get(result.getFrom())
                                    + (result.getPoint().isEmpty() ? 0 : Integer.parseInt(result.getPoint())));
                } else {
                    map.put(result.getFrom(),
                            (result.getPoint().isEmpty() ? 0 : Integer.parseInt(result.getPoint())));
                }
            }
        }

        //updating to db
        List<RoomUserInfo> listinfo = new ArrayList<>();
        for(String wuid:map.keySet()){
            TempRepo.getSingleUserInfo(getApplicationContext(), wuid, map.get(wuid), info -> {
                if(info!=null){ listinfo.add(info); }
            });
        }

        //sort in descending order by points
        Collections.sort(listinfo, new ListSorting.ShortRoomUserInfoByPoints());

        if(listinfo.size() > 10) {
            List<List<RoomUserInfo>> list2 = Partition.ofSize(listinfo, 10);
            //Event.triggerOnTopGiftSender(list2.get(0));
        }else {
            //Event.triggerOnTopGiftSender(listinfo);
        }
    }


    void acceptCall_or_rejectCall(ControllModal data){

        if(data.strs[0]!=null && data.strs[1]!=null) {
            FireFun.acceptCall(data.strs[0], data.strs[1], data.longs[0]);
        }
    }
    void updateCallMap(Map<String, EntityCall> map, String roomid){
        for(EntityCall call:map.values()) {
            call.room = roomid;
            LiveRepo.updateoradd_call(getApplicationContext(), call);
        }
    }
    void closeCall_mode(ControllModal data){
        Map<String, Object> map = new HashMap<>();
        map.put("state", STAT_BROADCAST);
        map.put("callcount", 0);
        map.put("calltype", "");
        map.put("calls", null);
        //update2Broadata(data.strs[0], map);
    }

    void calltouser(ControllModal data){
        Map<String, Object> map = new HashMap<>();
        map.put("state", STAT_CALL_MODE);
        map.put("callcount", 2);
        //update2Broadata(data.strs[0], map);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("room", data.strs[3]);
        map2.put("owner", data.strs[0]);
        map2.put("time", System.currentTimeMillis());
        map2.put("type", TYPE_CALL);
        map2.put("message", data.strs[5]);

        map2.put("senderuid", data.strs[0]);
        map2.put("senderuidweb", cread.getId());
        map2.put("sendername", cread.getFullName());
        map2.put("senderavtar", cread.getProfilePicture());

        map2.put("tofuid", data.strs[1]);
        map2.put("towuid", data.strs[2]);
        map2.put("toname", data.strs[4]);
        map2.put("toxp", 0);
        map2.put("state", 1);

        FieldPath path = FieldPath.of("calls", data.strs[1]);

        updateMap(data.strs[0], path, map2);

    }

    void cahngeState(String broadfuid){
        Map<String, Object> map = new HashMap<>();
        map.put("state", STAT_BROADCAST);
        //update2Broadata(broadfuid, map);
    }

    void updateMap(String ownerUid, FieldPath path, Map<String, Object> map){
        if(ownerUid==null) return;
        FirebaseFirestore.getInstance()
                .collection(BROADCASTERS)
                .document(ownerUid)
                .update(path, map)
                .addOnSuccessListener(aVoid -> {}).addOnFailureListener(e -> {});
    }

    void get_joinerNameInfo(String fuid){
        TempRepo.AddUserData(getApplicationContext(), fuid);
    }

    /////////////////////////////////////////////////////////////////////
    /////////////user updates///////////////
    void get_levels(){
        RetroCalls.instance().callType(JOINER_NAME)
                .stringParam(local_fuid)
                .joiner_name((a,b)->{
                    if(b!=null && b.getStatus()==1 && b.getResult()!=null){
                       // TempRepo.AddUserData(getApplicationContext(), b.getResult());
                    }
                });
    }

    void get_all_gift_list(){

        TempRepo.clearGiftList(getApplicationContext());

        Call<GiftListResponse2> call1 = AppConfig.loadInterface().getGiftList(1);
        Call<GiftListResponse2> call2 = AppConfig.loadInterface().getGiftList(2);
        Call<GiftListResponse2> call3 = AppConfig.loadInterface().getGiftList(3);

        enqueGift(call1);
        enqueGift(call2);
        enqueGift(call3);

    }

    void enqueGift(Call<GiftListResponse2> call){
        call.enqueue(new Callback<GiftListResponse2>() {
            @Override
            public void onResponse(Call<GiftListResponse2> call, Response<GiftListResponse2> response) {
                GiftListResponse2 data = response.body();
                if(data!=null && data.getStatus()==1 && data.getResult()!=null){
                    TempRepo.addGiftList(getApplicationContext(), data.result);
                }
            }

            @Override
            public void onFailure(Call<GiftListResponse2> call, Throwable t) { }
        });
    }

    void getZipList(){
        RetroCalls.instance().callType(GET_ZIP_GIFTS)
                .setOnFail((a,b,c)->{ })
                .get_zip_gifts((a,b)->{
                    if(a==GET_ZIP_GIFTS && b!=null && b.getStatus()==1 && b.getResult()!=null){
                        loge(TAG, "getZipList", toGson(b.getResult()));

                        String parentPath = "/data/data/"+getApplicationContext().getPackageName()+"/";

                        for (ZipGiftItem zip:b.getResult()){
                            testDownloadedZip(parentPath, zip);
                        }

                        loge(TAG, "getZipList", ""+undownloaded.size());
                    }
                });
    }

    List<ZipGiftItem> undownloaded = new ArrayList<>();
    void testDownloadedZip(String parentPath, ZipGiftItem namewithextension){

        String zipPath = parentPath+"gifts/"+namewithextension.animation; //*.zip
        String extractedFolder = zipPath.replace(".zip", "");

        File extractedFile = new File(extractedFolder);
        File zipfolder = new File(zipPath);

        if(extractedFile.exists()){
        }  else {
            if (zipfolder.exists()) {
            }else {
                undownloaded.add(namewithextension);
            }
        }

    }

    void load_music0(){
        String MUSIC_URL = "https://api-solutions.sh.agoralab.co/ent/v1/musics";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MUSIC_URL,
                response -> {
                    Type apiResultType = new TypeToken<DataMod<List<MusicInfo>>>() {}.getType();
                    DataMod<List<MusicInfo>> data = new Gson().fromJson(response, apiResultType);

                    loge(TAG, data.getData().get(0).getUrl());
                    application().config().setMusicList(data.getData());
                },
                error -> {

                });
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    void load_music(){
        RetroCalls.instance().callType(MUSIC_LIST)
                .music_list((a,b)->{
                    if(b!=null && b.getStatus()==1 && b.getResult()!=null) {
                        application().config().setMusicList(b.getResult());
                    }
                });
    }

    void get_level_table(){
        TempRepo.level_data(getApplicationContext());
    }

}