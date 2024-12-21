package com.deificindia.indifun.vm;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.deificindia.indifun.Model.abs_plugs.JoinerName;

import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.db.GiftQueue;
import com.deificindia.indifun.db.LiveAppDb;
import com.deificindia.indifun.db.LiveDao;
import com.deificindia.indifun.db.LiveEntity;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.db.LiveRepo2;
import com.deificindia.indifun.db.TempDao;
import com.deificindia.indifun.db.TempDb;
import com.deificindia.indifun.db.TempRepo;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;
import com.deificindia.indifun.interfaces.Firelistener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import static com.deificindia.indifun.Utility.Logger.loge;

public class LiveVm extends AndroidViewModel {

    private final LiveDao dao;
    private final TempRepo tempRepo;
    private final LiveRepo2 repo2;

    public String owner_fuid;

    public LiveVm(@NonNull Application application) {
        super(application);
        owner_fuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dao = LiveAppDb.getDatabase(application).userDao();
        tempRepo = new TempRepo(application);
        repo2 = new LiveRepo2(application);
    }

    public LiveData<List<LiveEntity2>> liveHot(){
        return repo2.loadHot_live();
    }

    public LiveData<List<LiveEntity2>> liveFollow(){
        return repo2.loadFriend_live();
    }

    public LiveData<List<LiveEntity2>> liveREcommended(){
        return repo2.loadRecommended_live();
    }

    public LiveData<LiveEntity2> getLiveUser(String ownerfuid){
        return dao.getLiveUser(ownerfuid, 1);
    }

    public LiveData<List<EntityCall>> getLiveCalls(String room){
        return dao.getAllCalls(room);
    }




    ///////////////////////////////////////////////////////////////
    //////Live room user listener/////
    public void updateApiData(RoomUserInfo entity){
        loge("LiveVm", "updateApiData");
        tempRepo.updateApiData(entity);
    }

    public LiveData<List<RoomUserInfo>> temp_join_user_only(String owneruid, String roomname){
        return tempRepo.temp_join_user_only(owneruid, roomname);
    }

    public void updateEnterPlayed(String fuid, boolean changeOnline){
        tempRepo.updateEnterPlayed(fuid, changeOnline);
    }

    public void getSingleUserInfo(String fuid, Firelistener.OnRoomUserInfo listener){
        tempRepo.getSingleUserInfo(fuid, listener);
    }

    ///////////////////////////////////////////////////////////////////////////
    ////////////////gift--queue///////////////////////////////////////////////////////////////





}
