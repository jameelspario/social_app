package com.deificindia.indifun.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.deificpk.frags.OnResultLiveEntity;
import com.deificindia.indifun.deificpk.modals.Clip;
import com.deificindia.indifun.interfaces.Firelistener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.deificindia.indifun.Utility.Logger.loge;


public class LiveRepo {
    Context context;

    public static LiveEntity readSingle(Context context, String sear_fuid){
        LiveEntity ifExist = LiveAppDb.getDatabase(context).userDao().exist(sear_fuid);
        return ifExist;
    }

    public static void readEntityById(int id, OnResultLiveEntity listener){
        LiveAppDb.databaseWriteExecutor.execute(()->{
//<<<<<<< HEAD
            List<LiveEntity> ifExist = IndifunApplication.getInstance().liveDao.getByIndex();
            //if(listener!=null) listener.onResult(ifExist);

        });
    }
    public static int countOngoingCalls(Context context, String room){
        return LiveAppDb.getDatabase(context).userDao().countOngoingCalls(room, 2);
    }

    public static void liveUsersFilter(String first_clip_owner, int n, OnResultLiveEntity<Clip> listener){
        LiveAppDb.databaseWriteExecutor.execute(()->{

            String owner_fuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            List<LiveEntity> ifExist;

            if(n==1) ifExist = IndifunApplication.getInstance().liveDao.loadFriend1(1,1,0,0,0, owner_fuid);
            else if(n==2) ifExist = IndifunApplication.getInstance().liveDao.loadRecommended1(1,1,1,0,0,0, owner_fuid);
            else ifExist = IndifunApplication.getInstance().liveDao.universal1(1,0,0,0, owner_fuid);

            List<Clip> l = new ArrayList<>();
            for(LiveEntity en:ifExist){
                if(en.full_name!=null && !en.full_name.isEmpty() && !Objects.equals(en.fuid, first_clip_owner)) {
                    Clip clp = new Clip();
                    clp.id = en.id;
                    clp.isowner = false;
                    clp.ownerfuid = en.fuid;
                    clp.owneravtar = en.image;
                    clp.avtartype = en.owneravtartype;
                    clp.roomid = en.roomid;
                    clp.ownername = en.full_name;

                    clp.broad_join_identity = Constants.roomIdentity(false, en.fuid);

                    l.add(clp);
                }
            }

//            if(listener!=null) listener.onResult(l);
//=======
           /* int counts = IndifunApplication.getInstance().liveDao.getCount();
            if(id > counts){
                if(listener!=null) listener.onResult(null);
            }
            if(id<1){
                if(listener!=null) listener.onResult(null);
            }*/
            //List<LiveEntity> ifExist = IndifunApplication.getInstance().liveDao.getByIndex();
            //if(listener!=null) listener.onResult(ifExist);
///>>>>>>> Indifunupdated3/master

        });
    }

    public static void AddFireData(Context context, LiveEntity entity){
        loge("DB", "updateToTableFire");
        LiveAppDb.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LiveEntity ifExist = LiveAppDb.getDatabase(context).userDao().exist(entity.fuid);
                //loge("Db", ""+ifExist.id);
                if(ifExist==null){
                    long l = LiveAppDb.getDatabase(context).userDao().insert(entity);
                    loge("Db","updateToTableFire", ""+l);
                }else {
                    LiveAppDb.getDatabase(context).userDao().update(
                            entity.fuid,
                            entity.roomname,
                            entity.roomid,
                            entity.wuid,
                            entity.full_name,
                            entity.image,
                            entity.owneravtartype,
                            entity.broadid,
                            entity.age,
                            entity.gender,
                            entity.is_online,
                            entity.ispk,
                            entity.pksender,
                            entity.pkduration,
                            entity.pkstarttime,
                            entity.state,
                            entity.callcount,
                            entity.calltype,
                            entity.pkroomid,
                            entity.pkuseruid,
                            entity.pkuseruidweb,
                            entity.pkusername,
                            entity.pkavtar,
                            entity.pkavtartype,
                            entity.pkbroadid,
                            entity.pkid,
                            entity.pkpkid
                    );
                }
            }
        });
    }

    public static void updateApiData(Context context, LiveEntity entity){
        LiveAppDb.databaseWriteExecutor.execute(() -> LiveAppDb.getDatabase(context).userDao().updateApiData(
                entity.fuid,
                entity.is_friend,
                entity.is_blocked,
                entity.is_mute,
                entity.is_kick,
                entity.is_following,
                entity.matched,
                entity.is_online,
                entity.whatsapp,
                entity.diamond,
                entity.heart,
                entity.level,
                entity.to_next_level
        ));
    }

    public static void updateFireData(Context context, LiveEntity entity){
        LiveAppDb.databaseWriteExecutor.execute(() -> LiveAppDb.getDatabase(context).userDao().updateFire(
                entity.fuid,

                entity.ispk,
                entity.pksender,
                entity.pkduration,
                entity.pkstarttime,
                entity.state,
                entity.callcount,
                entity.calltype,
                entity.pkroomid,
                entity.pkuseruid,
                entity.pkuseruidweb,
                entity.pkusername,
                entity.pkavtar,
                entity.pkavtartype,
                entity.pkbroadid,
                entity.localpoint,
                entity.remotepoint,
                entity.pkid,
                entity.pkpkid
        ));
    }




    public void getDatas(Context context){
        LiveAppDb.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<LiveEntity> users = LiveAppDb.getDatabase(context).userDao().getAll();
                for(LiveEntity user : users) {
                    //Log.d("User", user.getDebugString());
                }
            }
        });
    }

    public static void updateIsOnline(Context context, String fuid, int isonline){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.databaseWriteExecutor.execute(() -> LiveAppDb.getDatabase(context).userDao().updateOnline(fuid, isonline));
        });
    }

    public static void updateIsFolowing(Context context, String wuid, int isfollowing){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.databaseWriteExecutor.execute(() -> LiveAppDb.getDatabase(context)
                    .userDao().updateFollowing(wuid, isfollowing));
        });
    }

    public static void isFollowing(Context context, String wuid){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.databaseWriteExecutor.execute(() -> LiveAppDb.getDatabase(context)
                    .userDao().exist(wuid));
        });
    }

    public static void updateIsBlocked(Context context, String wuid, int state){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.databaseWriteExecutor.execute(() -> LiveAppDb.getDatabase(context)
                    .userDao().updateIsBlocked(wuid, state));
        });
    }

    public static void updateIsKicked(Context context, String wuid, int state){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.databaseWriteExecutor.execute(() -> LiveAppDb.getDatabase(context)
                    .userDao().updateIsKicked(wuid, state));
        });
    }

    public static void updateIsMuted(Context context, String wuid, int state){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.databaseWriteExecutor.execute(() -> LiveAppDb.getDatabase(context)
                    .userDao().updateIsMuted(wuid, state));
        });
    }

    public static void clear(Context context){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.databaseWriteExecutor.execute(() -> LiveAppDb.getDatabase(context)
                    .userDao().deleteAll());
        });
    }

    public static void connectionState(Context context, String fuid, int state){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.databaseWriteExecutor.execute(() -> LiveAppDb.getDatabase(context)
                    .userDao().connectionState(fuid, state));
        });
    }

    ///////////////////////////////////////////////
//     public static void updateoradd_call(Context context, EntityCall entity){
//         LiveAppDb.databaseWriteExecutor.execute(() -> {
//             EntityCall ifExist = LiveAppDb.getDatabase(context).userDao().call_exist(entity.jfuid);
//             if(ifExist==null){
//                 long l = LiveAppDb.getDatabase(context).userDao().call_insert(entity);
//             }else {
//                 LiveAppDb.getDatabase(context).userDao()
//                         .call_update(
//                                 entity.jfuid,
//                                 entity.jwuid,
//                                 entity.index,
//                                 entity.jname,
//                                 entity.javtar,
//                                 entity.jtime,
//                                 entity.jxp,
//                                 entity.state,
//                                 entity.owner,
//                                 entity.room
//                 );
//             }
//         });
//     }

//     public static void getFilteredCall(Context context, String room, long callindex, Firelistener.OnCallRead listener){
//        LiveAppDb.databaseWriteExecutor.execute(()->{
//            List<EntityCall> list = LiveAppDb.getDatabase(context).userDao().getSelectedCalls(room, callindex);
//            if(listener!=null) listener.onCalls(list);
//        });
//     }

//    public static List<EntityCall> getFilteredCall(Context context, String room){
//        return LiveAppDb.getDatabase(context).userDao().getSelectedCalls(room);
//    }

    

    public static List<EntityCall> getFilteredCall(Context context, String room, long indx){

        return LiveAppDb.getDatabase(context).userDao().getSelectedCalls(room, indx);
    }
    public static void call_state(Context context, String tofuid, int mute){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.getDatabase(context).userDao().call_state(tofuid, mute);
        });
    }

     public static void audio(Context context, String tofuid, boolean mute){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.getDatabase(context).userDao().call_update_audio(tofuid, mute);
        });
     }

    public static void camera(Context context, String tofuid, boolean mute){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.getDatabase(context).userDao().call_update_camera(tofuid, mute);
        });
    }

    public static void camera_view(Context context, String tofuid, boolean mute){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.getDatabase(context).userDao().call_update_camera_view(tofuid, mute);
        });
    }

     public static void clear_call(Context context){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.getDatabase(context).userDao().clear_call();
        });
     }

    public static void clear_call(Context context, String jfuid){
        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.getDatabase(context).userDao().delete_call(jfuid);
        });
    }

    public static void updateoradd_call(Context context, EntityCall entity) {
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            EntityCall ifExist = LiveAppDb.getDatabase(context).userDao().call_exist(entity.jfuid);
            if (ifExist == null) {
                long l = LiveAppDb.getDatabase(context).userDao().call_insert(entity);
            } else {
                entity.id = ifExist.id;
                if (entity.state > 1) {
                    LiveAppDb.getDatabase(context).userDao()
                            .call_state(entity.jfuid, entity.state);
                } else {
                    LiveAppDb.getDatabase(context).userDao()
                            .call_update(entity);
                }
            }
        });
    }

}
