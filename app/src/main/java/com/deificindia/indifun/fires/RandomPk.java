package com.deificindia.indifun.fires;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.deificpk.frags.BaseFireFragment.STAT_PK_MODE;
import static com.deificindia.indifun.deificpk.utils.Const.SEARCHTIME;

import android.os.Handler;
import android.os.Looper;

import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Model.abs_plugs.ServerTime;
import com.deificindia.indifun.Utility.HandlerThread;
import com.deificindia.indifun.db.PKInfo;
import com.deificindia.indifun.rest.AppConfig;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Response;

public class RandomPk {

    public static final String TAG = "Random PK";


    Handler rndHandler;

    List<PKInfo> rnd;

    private PKInfo sendingPkInfo;

    long serverTime;
    boolean canceledRandomPkRequest;
    RandomPkStateListener listener;

    public static RandomPk instance(){
        return new RandomPk();
    }

    public RandomPk with(PKInfo sendingPkInfo, RandomPkStateListener listener){
        this.sendingPkInfo = sendingPkInfo;
        this.listener = listener;
        startRandomPk();
        return this;
    }

    public RandomPk with(PKInfo sendingPkInfo){
        this.sendingPkInfo = sendingPkInfo;
        return this;
    }

    public RandomPk startRandomPk(){
        findRndPkUser();
        return this;
    }

    public void cancelRandomPk(){
        canceledRandomPkRequest = true;
        rndHandler.removeCallbacksAndMessages(null);
    }


    private void findRndPkUser(){

        HandlerThread.executor.execute(()-> {

            //get server time //
            try {

                serverTime = currentServerTime();

                DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReference(FireConst.RANDOMPKCOLL);

                //Query query = ref.orderByChild("state").equalTo(1);//equalTo(1, "state");
                //Query query = ref.orderByChild("currtime").endBefore()

                List<PKInfo> result = FireAsync.getRealtimeValue(ref, false);
                //loge("RndPk", new Gson().toJson(result.getValue()));

               // GenericTypeIndicator<List<PKInfo>> gen = new GenericTypeIndicator<List<PKInfo>>() {};

                if (result != null) {
                    rnd = result;
                    randomResultRequesting();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }


    void randomResultRequesting() throws Exception {

        if(canceledRandomPkRequest){
            trigger_listener(State.PKCANCEALED);
            return;
        }

        if(rnd!=null && rnd.size()>0){
            Random rand = new Random();
            PKInfo rndResult = rnd.get(rand.nextInt(rnd.size()));

            if(rndResult!=null && !rndResult.pk_user_fuid.equals(sendingPkInfo.pk_user_fuid) && rndResult.current_time > serverTime){
                //send pk request to rndResult.fuid
                sendPkRequest(rndResult);
            } else {
                rnd.remove(rndResult);
                //next rnd result
                randomResultRequesting();
            }
        } else {

            goToWaitingList();

        }

    }

    void sendPkRequest(PKInfo rndResult) throws Exception {
//        inviteUserForPk(sendingPkInfo.pk_user_fuid, rndResult.pk_user_fuid,
//                sendingPkInfo.toMap(), STAT_RND_PK);

        startPk(sendingPkInfo, rndResult, STAT_PK_MODE);

    }

    void goToWaitingList() throws IOException {

        long serverTime = currentServerTime();

        sendingPkInfo.current_time = serverTime+SEARCHTIME;

        FirebaseDatabase.getInstance().getReference(FireConst.RANDOMPKCOLL)
                .child(sendingPkInfo.pk_user_fuid)
                .setValue(sendingPkInfo);

        endPkHandler();

        trigger_listener(State.PKSARTEDWAITING);

    }

    long currentServerTime() throws IOException {
        Response<ObjectModal<ServerTime>> response = AppConfig.loadInterface().get_server_time().execute();

        return response.body().getResult().server_time;
    }

    void endPkHandler(){
        rndHandler = new Handler(Looper.getMainLooper());
        rndHandler.postDelayed(()->{

            deleteFromWaitList();
            trigger_listener(State.PKTIMEOUT);
            //time out
        }, SEARCHTIME);
    }

    public void startPk(PKInfo  local_pk_info, PKInfo remote_pk_info, int state) throws Exception {
        AtomicBoolean shouldSendRequest = new AtomicBoolean(false);

        CountDownLatch done = new CountDownLatch(1);

        FirebaseDatabase.getInstance()
                .getReference(FireConst.COLL_GO)
                .child(remote_pk_info.pk_user_fuid).child("state").get()
                .addOnCompleteListener(task -> {
                    try {
                        if (task.getResult().getValue(Long.class) == 1) {
                            shouldSendRequest.set(true);
                            done.countDown();
                        }
                    }catch (Exception e){
                        done.countDown();
                        FirebaseCrashlytics.getInstance().recordException(e);
                    }
                }).addOnFailureListener(e -> {
            done.countDown();
        });
        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if(shouldSendRequest.get() && !canceledRandomPkRequest) {
            //sending to local
            Map<String, Object> map_local = LiveFireFun.getMap(FireConst.CHANGE_PK);
            map_local.put("state", state);
            map_local.put("pksender", true);
            map_local.put(FireConst.SUB_COLL_GO_PK, local_pk_info.toMap());

            FirebaseDatabase.getInstance()
                    .getReference(FireConst.COLL_GO)
                    .child(local_pk_info.pk_user_fuid)
                    .updateChildren(map_local);


            //sending to remote
            Map<String, Object> map= LiveFireFun.getMap(FireConst.CHANGE_PK);
            map.put("state", state);
            map.put("pksender", false);
            map.put(FireConst.SUB_COLL_GO_PK, remote_pk_info.toMap());

            FirebaseDatabase.getInstance()
                    .getReference(FireConst.COLL_GO)
                    .child(remote_pk_info.pk_user_fuid)
                    .updateChildren(map);



            trigger_listener(State.PKREQUESTSENT);

        } else {
            randomResultRequesting();
        }
    }

    public void inviteUserForPk(String local_owner_uid, String remote_uid,
                                Map<String, Object> pkinfo,
                                int stat) throws Exception {

        AtomicBoolean shouldSendRequest = new AtomicBoolean(false);


        CountDownLatch done = new CountDownLatch(1);

        FirebaseDatabase.getInstance()
                .getReference(FireConst.COLL_GO)
                .child(remote_uid).child("state").get()
                .addOnCompleteListener(task -> {
                    try {
                        if (task.getResult().getValue(Long.class) == 1) {
                            shouldSendRequest.set(true);
                            done.countDown();
                        }
                    }catch (Exception e){
                        done.countDown();
                        FirebaseCrashlytics.getInstance().recordException(e);
                    }
                }).addOnFailureListener(e -> {
                    done.countDown();
                });
        try {
            done.await(); //it will wait till the response is received from firebase.
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        if(shouldSendRequest.get() && !canceledRandomPkRequest) {

            Map<String, Object> map= LiveFireFun.getMap(FireConst.CHANGE_PK);
            map.put("state", stat);
            map.put("pksender", false);
            map.put(FireConst.SUB_COLL_GO_PK, pkinfo);

            FirebaseDatabase.getInstance()
                    .getReference(FireConst.COLL_GO)
                    .child(remote_uid)
                    .updateChildren(map);

            Map<String, Object> map_local = LiveFireFun.getMap(FireConst.CHANGE_PK);
            map.put("state", stat);
            map.put("pksender", true);
            map.put(FireConst.SUB_COLL_GO_PK, null);

            FirebaseDatabase.getInstance()
                    .getReference(FireConst.COLL_GO)
                    .child(local_owner_uid)
                    .updateChildren(map_local);

            trigger_listener(State.PKREQUESTSENT);

        }else {
            randomResultRequesting();
        }
    }



    public interface RandomPkStateListener{
        void onState(State status);

    }

    public enum State{
        PKCANCEALED, PKTIMEOUT,PKSARTEDWAITING, PKREQUESTSENT
    }

    void trigger_listener(State n){
        if(listener!=null) {
            HandlerThread.mHandler.post(()->{
                listener.onState(n);
            });
        }
    }

    void deleteFromWaitList(){
        FirebaseDatabase.getInstance().getReference(FireConst.RANDOMPKCOLL)
                .child(sendingPkInfo.pk_user_fuid).removeValue();
    }



}
