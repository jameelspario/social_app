package com.deificindia.indifun.db;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.deificindia.firebaseModel.AddLiveModel;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.Model.abs_plugs.JoinerName;
import com.deificindia.indifun.Model.abs_plugs.LevelData;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;
import com.deificindia.indifun.interfaces.Firelistener;
import com.deificindia.indifun.rest.RetroCalls;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import timber.log.Timber;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.rest.RetroCallListener.JOINER_NAME;
import static com.deificindia.indifun.rest.RetroCallListener.LEVEL_DATA;
import static tvi.webrtc.ContextUtils.getApplicationContext;

public class TempRepo {
    private TempDao tempDao;

    public TempRepo(Context context) {
        tempDao = TempDb.getDatabase(context).dao();
    }

    public static void AddUserData(Context context, RoomUserInfo entity){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            RoomUserInfo ifExist = TempDb.getDatabase(context).dao().exist(entity.fuid);
            //loge("Db", ""+ifExist.id);
            if(ifExist==null){
                long l = TempDb.getDatabase(context).dao().insert(entity);
                //loge("TempRepo","AddUserData", ""+l);
            }else {
                TempDb.getDatabase(context).dao().updateFirstData(
                        entity.fuid,
                        entity.identity,
                        entity.roomname,
                        entity.ownerfuid,
                        entity.online);
            }
        });
    }


    public static void updateOnDisconnect(Context context, String fuid, boolean changeOnline){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            TempDb.getDatabase(context).dao().updateOnline(fuid, changeOnline);
        });
    }

    public void updateApiData(RoomUserInfo entity){
        loge("LiveVm", "updateApiData");
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            tempDao.updateApiData(entity.fuid,
                    entity.wuid,
                    entity.avtar,
                    entity.name,
                    entity.points, entity.level, entity.broadid);

        });
    }

    public LiveData<List<RoomUserInfo>> temp_join_user_only(String owneruid, String roomname){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            tempDao.clear();
        });

        return tempDao.temp_join_user_only(owneruid, roomname, true);
    }

    public void updateEnterPlayed(String fuid, boolean changeOnline){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            tempDao.updateEnterPlayed(fuid, changeOnline);
        });
    }

    public static void getSingleUserInfo(Context context, String wuid, int points, Firelistener.OnRoomUserInfo listener){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            TempDao dao =  TempDb.getDatabase(context).dao();
            dao.updatePoint(wuid, points);
            RoomUserInfo info = dao.getSingleUserInfo(wuid, true, false);
            if(listener!=null) listener.roomUserInfo(info);
        });
    }

    public static void getAllUserInfo(Context context, Firelistener.OnRoomUserList listener){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            TempDao dao =  TempDb.getDatabase(context).dao();
            List<RoomUserInfo> info = dao.getAllUserInfo(true);
            if(listener!=null) listener.roomUserInfo(info);
        });
    }

    public void getSingleUserInfo(String fuid, Firelistener.OnRoomUserInfo listener){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            RoomUserInfo info = tempDao.getSingleUserInfo(fuid, true, false);
            if(listener!=null) listener.roomUserInfo(info);
        });
    }


    public static long getOnlineCount(TempDao dao, String owneruid, String roomname){
        return dao.getCount(owneruid, roomname, true);
    }

    ///////////////////////////////////////////////////////////////////////
    public static void clearGiftList(Context context){
        loge("TempRepo", "clearGiftList");
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            TempDb.getDatabase(context).dao().clearGift();
        });
    }

    public static void addGiftList(Context context, List<GiftInfo2> entity){
        loge("TempRepo", "addGiftList");
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            TempDb.getDatabase(context).dao().insertAllOrders(entity);
        });
    }
    ///////////////////////////////////////////////////////////////////////////

    public static void AddUserRestData(Context context, RoomUserInfo entity){
        loge("TempRepo", "AddUserRestData");
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            RoomUserInfo ifExist = TempDb.getDatabase(context).dao().exist(entity.identity);
            //loge("Db", ""+ifExist.id);
            if(ifExist==null){
                long l = TempDb.getDatabase(context).dao().insert(entity);
                //loge("TempRepo","updateToTableFire", ""+l);
            }else {
                //TempDb.getDatabase(context).dao().updateApiData(entity);
            }
        });
    }

    public static void clear_gift_queue(Context context){
        TempDb.databaseWriteExecutor.execute(()->{
            TempDb.getDatabase(context).dao().clear_gift_queue();
        });
    }

    /////////////////////////////joiner name////////////////////////////
    public static void AddUserData(Context context, String local_fuid){

            LiveAppDb.databaseWriteExecutor.execute(() -> {

                ObjectModal<JoinerName> data =  RetroCalls.instance().callType(JOINER_NAME)
                        .stringParam(local_fuid)
                        .joiner_name_sync();

                if(data!=null && data.getStatus() ==1 && data.getResult()!=null) {
                    JoinerName entity = data.getResult();

                    JoinerName ifExist = TempDb.getDatabase(context).dao().joinerExist(entity.firebase_uid);
                    if (ifExist == null) {
                        long l = TempDb.getDatabase(context).dao().insertJoinerName(entity);
                    } else {
                        TempDb.getDatabase(context).dao().updateJoinerName(
                                entity.firebase_uid,
                                entity.level,
                                entity.diamond,
                                entity.heart,
                                entity.broadcast_level,
                                entity.broadcast_point,
                                entity.my_xp,
                                entity.total_xp,
                                entity.to_next_level,
                                entity.friends,
                                entity.add_broadcast_id,
                                entity.add_broadcast_title,
                                entity.broadcasting_type,
                                entity.is_broadcasting);
                    }
                }
            });
    }

    public static void updateBroadXp(Context context, String fuid, long broad_point){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            TempDb.getDatabase(context).dao().joinerName_broadcaste_point(fuid, broad_point);
        });
    }

    public static void getJoinerName(Context context, String fuid, Firelistener.OnJoinerNameData listener){
        LiveAppDb.databaseWriteExecutor.submit(() -> {
             JoinerName jn = TempDb.getDatabase(context).dao().get_JoinerName(fuid);
             if(listener!=null){
                 listener.onJoinerName(jn);
             }
        });
    }

    public LiveData<JoinerName> get_JoinerName_live(String owneruid){
        return tempDao.get_JoinerName_live(owneruid);
    }

    ////////level_data////////////
    public static void level_data(Context context){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            ObjectModal<List<LevelData>> data =  RetroCalls.instance().callType(LEVEL_DATA)
                    .level_data();

            if(data!=null && data.getStatus()==1 && data.getResult()!=null){
                TempDb.getDatabase(context).dao().clearLevelData();
                TempDb.getDatabase(context).dao().insertLevelData(data.getResult());
            }

        });
    }


    public static void calculate_level(Context context, long point, Firelistener.OnLevel listener){
        LiveAppDb.databaseWriteExecutor.submit(() -> {
            long level = 0;
            long max_point= TempDb.getDatabase(context).dao().live_max();
            if(max_point<point) {
                level = TempDb.getDatabase(context).dao().live_max_level();
            }else {
                level = TempDb.getDatabase(context).dao().live_level(point);
            }
            loge("lvl ", ""+max_point, ""+level);
            if(listener!=null){
                listener.onLevel(level);
            }
        });
    }



}
