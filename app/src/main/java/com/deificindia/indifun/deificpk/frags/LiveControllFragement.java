package com.deificindia.indifun.deificpk.frags;

import android.view.View;
import android.widget.LinearLayout;

import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.MenuUtils;
import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.deificpk.actionsheets.CallPendingList;
import com.deificindia.indifun.deificpk.utils.CallObject;
import com.deificindia.indifun.deificpk.utils.CameraCapturerCompat;
import com.deificindia.indifun.dialogs.DialogUtils;
import com.deificindia.indifun.fires.LiveFireFun;
import com.skydoves.powermenu.CustomPowerMenu;
import com.twilio.video.AudioTrack;
import com.twilio.video.LocalAudioTrack;
import com.twilio.video.LocalParticipant;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.VideoTrack;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.deificpk.utils.CallObject.CALLEE;

public abstract class LiveControllFragement extends BaseFireFragment implements OnStartLocalRender {

    private static final String TAG = "LiveControllFragement";

    protected LinearLayout call_layout_calling_users;

    protected LocalParticipant localParticipant;

    protected CameraCapturerCompat cameraCapturerCompat;
    protected LocalAudioTrack localAudioTrack;
    protected LocalVideoTrack localVideoTrack;

    String __calltype = "audio";

    protected Map<String, VideoTrack> remote_video_track = new HashMap<>();
    protected Map<String, AudioTrack> remote_audio_track = new HashMap<>();

    LinkedHashMap<Long, CallObject> callObject = new LinkedHashMap<>();

    protected abstract void createTracksForCallingUser(String calltype);
    protected abstract void callOrPkButtonVisiblity();
    protected abstract void callCloseButtonVisiblity(int n);

    // bottom sheet action listener ||
    CallPendingList.OnCallPendingListListener onCallPendingListListener = (what, track) -> {
        if(what==0){
            dismissActionSheetDialog();
        }
        if (what==1) {
            createTracksForCallingUser(this.__calltype);

            EntityCall en = new EntityCall();
            en.owner = vm.broad_usr_fuid;
            en.room = vm.broad_room_id;
            en.index = track.index;
            en.jfuid = vm.local_fuid;
            en.jwuid = credentials.getId();
            en.jname = credentials.getFullName();
            en.javtar = credentials.getProfilePicture();
            en.jtime = System.currentTimeMillis();
            en.jxp = 0;
            en.state = 1;

            LiveFireFun.instance().joinCall(en);

            dismissActionSheetDialog();
        }
    };

    //callmode-live:event for call stat==7 =>init call mode once
    protected void AddCallsStat_setup(long callcount, String calltype){
        loge(TAG, "Call", callcount+"", calltype);
        this.__calltype = calltype;

        if(callObject.isEmpty()) {

            //prepare & init callObject
            for (int j = 0; j < callcount; j++) {
                EntityCall call = new EntityCall();
                call.index = j;

                CallObject cobj = new CallObject(getContext());
                cobj.setEntity(call);
                cobj.index(j);
                cobj.set_usr_fuid(vm.broad_usr_fuid, vm.local_fuid);
                cobj.owner(vm.isOwner);
                cobj.callType(calltype);
                cobj.setListener(this);

                call_layout_calling_users.addView(cobj);
                callObject.put((long) j, cobj);

            }

            loge(TAG, "AddCallsStat_setup", "" + callcount, calltype);

        }

        callOrPkButtonVisiblity();
        callCloseButtonVisiblity(VISIBLE);

    }

    //call placeholder click listener
    public void callAdapterClickListener(int what, EntityCall item) {
        switch (what) {
            case 0:
                indexBzy = false;
                showActionSheetCallingInfo(vm.isOwner, false, item, onCallPendingListListener);
                break;
        }
    }

    @Override
    public void onRemovedCallAt(long index) {
        CallObject obj = callObject.get(index);
        if(obj!=null){
            call_layout_calling_users.removeView(obj);
        }
        callObject.remove(index);

        controllButton();
    }

    void controllButton(){
        if(callObject.isEmpty()) {
            callOrPkButtonVisiblity();
            callCloseButtonVisiblity(GONE);
        }
    }

    @Override
    public void callAccepted(long index, int userType, @NotNull String fuid) {
        CallObject obj = callObject.get(index);
        if(obj!=null) {
            if(userType==CALLEE){
                obj.updateLocalCallObject(localParticipant, cameraCapturerCompat, localAudioTrack, localVideoTrack);
            }else {
                obj.updateRemoteCallObject(remote_video_track.get(fuid), remote_audio_track.get(fuid));
            }
        }
    }

    //start call mode by owner//\\setting fdb
    private void plugCallToFireBase(String calltype){
        LiveFireFun.instance().start_call_mode(vm.broad_usr_fuid, calltype);
    }


    protected void removeAllCalls(){

        List<Long> remover = new ArrayList<>(callObject.keySet());
        for (Long key:remover){
            CallObject obj = callObject.get(key);
            if(obj!=null)
                obj.removeCallFinally();

            callObject.remove(key);
        }

        controllButton();
    }

    /*
     *
     * firebse=>room db=>livevm=>
     *
     * */
    protected void readIncomingCallRequestAndStatus(List<EntityCall> entityCalls) {

        if(entityCalls!=null && !entityCalls.isEmpty()) {
            for (EntityCall entityCall:entityCalls){
                if(entityCall.jfuid!=null){
                    //collect user call info...
                    CallObject obj = callObject.get(entityCall.index);
                    if(obj==null) continue;
                    obj.addCallingEntity(entityCall);
                }
            }
        }
        loge(TAG, "ReadCall");
    }


    protected void collectRemoteParticipantVideo(String identity, VideoTrack videoTrack) {
        String remotefuid = Constants.userUid(identity, vm.broad_usr_fuid).second;
        loge(TAG, "collectRemoteParticipantVideo", identity, remotefuid, ""+videoTrack);

        remote_video_track.put(remotefuid, videoTrack);

        /*if(waitingRemoteTracks && callingAdapter!=null){
            callingAdapter.notifyChanged();
        }*/

        for (Long i:callObject.keySet()){
            CallObject cobj = callObject.get(i);
            if (cobj!=null && cobj.callIndex==i){
                cobj.recvCallObjVideo(identity, videoTrack);
            }
        }
    }

    protected void collectRemoteParticipantAudio(String identity, AudioTrack audioTrack) {
        String remotefuid = Constants.userUid(identity, vm.broad_usr_fuid).second;
        loge(TAG, "collectRemoteParticipantAudio", remotefuid, ""+audioTrack);
        remote_audio_track.put(remotefuid, audioTrack);

        /*if(waitingRemoteTracks && callingAdapter!=null){
            callingAdapter.notifyChanged();
        }*/

        for (Long i:callObject.keySet()){
            CallObject cobj = callObject.get(i);
            if (cobj!=null && cobj.callIndex==i){
                cobj.recvCallObjAudio(identity, audioTrack);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void addLocalParticipant(String remotefuid, LocalParticipant localParticipant) {
        //localParticipant.publishTrack();
    }

    CustomPowerMenu customPowerMenu;

    protected void CallOption(View anchorView){
        List<MenuUtils.IconPowerMenuItem> menuitem = new ArrayList<>();

        /*menuitem.add(new MenuUtils.IconPowerMenuItem(
                ContextCompat.getDrawable(getContext(), R.drawable.ic_call_end_white_24px),
                "Audio Call"));

        menuitem.add(new MenuUtils.IconPowerMenuItem(
                ContextCompat.getDrawable(getContext(), R.drawable.ic_videocam_white_24dp),
                "Video Call"));

        customPowerMenu = MenuUtils.createMenu(anchorView, menuitem, (position, item) -> {
            if ("Audio Call".contentEquals(item.title)) {
                plugCallToFireBase(RemoteTrack.acall);
            }
            else if ("Video Call".contentEquals(item.title)) {
                plugCallToFireBase(RemoteTrack.vcall);
            }

            customPowerMenu.dismiss();

        });*/

        DialogUtils.showCallDialog(getContext(), (d, b)->{
            if(d!=null && d.isShowing()) d.dismiss();
            if(b==1){
                plugCallToFireBase(RemoteTrack.vcall);
            }else {
                plugCallToFireBase(RemoteTrack.acall);
            }
        });
    }

    /*
     *
     * Controlls
     *
     * */








}
