package com.deificindia.indifun.db;

import static com.deificindia.indifun.Utility.Logger.loge;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.List;

public class LiveRepo2 {

    Context context;

    LiveDao dao;
    String firebaseuid;

    public LiveRepo2(Context context) {
        this.context = context;
        firebaseuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dao = LiveAppDb.getDatabase(context).userDao();
    }

    LiveData<List<LiveEntity2>> read_live(){
        return dao.read_live();
    }

    public static void addFireLive(Context context, LiveEntity2 entity){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            LiveEntity2 ifExist = LiveAppDb.getDatabase(context).userDao().exist_live(entity.owneruid);
            //loge("Db", ""+ifExist);
            if(ifExist==null){
                long l = LiveAppDb.getDatabase(context).userDao().insert_live(entity);
            }else {
                entity.id = ifExist.id;
                LiveAppDb.getDatabase(context).userDao().update_live(entity);
            }
        });
    }

    public static void removeFireLive(Context context, String str){
        LiveAppDb.databaseWriteExecutor.execute(() -> {

            LiveAppDb.getDatabase(context).userDao().delete_single(str);
        });
    }

    public static void clearFireLive(Context context){
        //LiveAppDb.databaseWriteExecutor.execute(() -> {
            LiveAppDb.getDatabase(context).userDao().clear_live();
        //});
    }

    public static void goOfflineFireLive(Context context){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            LiveAppDb.getDatabase(context).userDao().gooffline(0);
        });
    }

    public static PKInfo pk_exist(Context context, String pk_room_id){
        return LiveAppDb.getDatabase(context).userDao().pk_exist(pk_room_id);
    }

    public static void pk_update_insert(Context context, PKInfo info){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            LiveDao d = LiveAppDb.getDatabase(context).userDao();
            PKInfo pkinfo = d.pk_exist(info.pk_room_id);
            loge("REPO2", new Gson().toJson(pkinfo));
            if(pkinfo==null){
                d.pk_insert(info);
            }else {
                info.id = pkinfo.id;
                d.pk_update(info);
            }
        });
    }

    public static void pk_delete(Context context){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            LiveAppDb.getDatabase(context).userDao().pk_delete();
        });
    }

    public static void call_delete(Context context){
        LiveAppDb.databaseWriteExecutor.execute(() -> {
            LiveAppDb.getDatabase(context).userDao().clear_call();
        });
    }

    /*
    *
    *
    * */

    public LiveData<List<LiveEntity2>> loadHot_live(){
        return dao.loadHot_live(1, firebaseuid);
    }

   public LiveData<List<LiveEntity2>> loadFriend_live(){
        return dao.loadFriend_live(1, 1, firebaseuid);
    }

    public LiveData<List<LiveEntity2>> loadRecommended_live(){
        return dao.loadRecommended_live(1, 1, 1, firebaseuid);
    }

    public List<LiveEntity2> pkUsersResult(){
        return dao.pkusersResult(1, firebaseuid);
    }

}
