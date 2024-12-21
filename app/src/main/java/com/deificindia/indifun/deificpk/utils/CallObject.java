package com.deificindia.indifun.deificpk.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.MenuUtils;
import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.deificpk.frags.OnStartLocalRender;
import com.deificindia.indifun.deificpk.frags.RemoteTrack;
import com.deificindia.indifun.fires.FireFun;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.skydoves.powermenu.CustomPowerMenu;
import com.twilio.video.AudioSink;
import com.twilio.video.AudioTrack;
import com.twilio.video.CameraCapturer;
import com.twilio.video.LocalAudioTrack;
import com.twilio.video.LocalParticipant;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.RemoteAudioTrack;
import com.twilio.video.RemoteVideoTrack;
import com.twilio.video.VideoTrack;
import com.twilio.video.VideoView;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.deificpk.frags.RemoteTrack.CALLACCEPTED;
import static com.deificindia.indifun.deificpk.frags.RemoteTrack.CALLING;
import static com.deificindia.indifun.deificpk.frags.RemoteTrack.CALLREJECTED;

public class CallObject extends RelativeLayout {

    public static final String TAG = "CallObject";

    public static final int OWNER = 1;
    public static final int CALLEE = 2;
    public static final int LISTENER = 3;
    public long callIndex = 0;
    public long callSate = 0;
    public long localState = 0;
    public int userType;

    public AudioSink audioSink = new AudioSink() {
        @Override
        public void renderSample(@NonNull ByteBuffer audioSample, int encoding, int sampleRate, int channels) {
           /* try {
                //wavFileHelper.writeBytesToFile(audioSample, encoding, sampleRate, channels);
            } catch (IOException e) {
                Log.e(TAG, String.format("A fatal error has occurred. Stacktrace %s", e.getLocalizedMessage()));
            }*/
        }
    };
    protected LocalParticipant localParticipant;
    protected CameraCapturerCompat cameraCapturerCompat;
    protected LocalAudioTrack localAudioTrack;
    protected LocalVideoTrack localVideoTrack;
    VideoTrack videoTrack;
    AudioTrack audioTrack;
    View parentView;
    ConstraintLayout myparent;
    VideoView videoviewCallLayout;
    LottieAnimationView imageCallLayout;
    ImageView call_option_layout;
    ProgressBar progressBar;
    TextView tvMessage;
    CustomPowerMenu customPowerMenu;
    LinkedHashMap<String, EntityCall> callingEntity = new LinkedHashMap<>();
    private EntityCall item;
    private boolean isOwner;
    private String calltype;
    private OnStartLocalRender listener;
    private String broad_usr_fuid, local_user_fuid;
    private boolean waitingRemoteTracks;


    public CallObject(Context context) {
        super(context);
        initView();
    }

    public CallObject(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CallObject(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    void initView(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.layout_call_view, this, true);

        parentView = v;
        //this.binding = binding;
        myparent = v.findViewById(R.id.myparent);
        videoviewCallLayout = v.findViewById(R.id.videoview_call_layout);
        imageCallLayout = v.findViewById(R.id.image_call_layout);
        call_option_layout = v.findViewById(R.id.call_option_layout);
        progressBar = v.findViewById(R.id.progress1);
        tvMessage = v.findViewById(R.id.tvMessage);


        callingEntity.clear();

        initialState();

        myparent.setOnClickListener(v1->{
            if(listener!=null)
                listener.callAdapterClickListener(0, item);
        });
    }

    public void initialState(){
        tvMessage.setVisibility(View.VISIBLE);
        if(isOwner){
            tvMessage.setText("Waiting member to join call.");
        }else {
            tvMessage.setText("Join call.");
        }
    }

    //geting data from live
    public void addCallingEntity(EntityCall entiti){
        if(entiti.index==callIndex){
            setMyCallSatatus((int) entiti.state);
            addCallingEntity2(entiti);
        }
    }

    private void addCallingEntity2(EntityCall entiti){

        callingEntity.put(entiti.jfuid, entiti);

        for(String jfuid:callingEntity.keySet()){
            EntityCall call = callingEntity.get(jfuid);
            if(call==null) continue;

            /////\\\\\/////\\\\\/////
            if(call.state==CALLACCEPTED){
                if(callSate==3 || callSate==1 || callSate==0){
                    item = call;
                    callSate = call.state;

                    if(isOwner){
                        userType = OWNER;
                    }else {
                        if (Objects.equals(call.jfuid, local_user_fuid)) {
                            userType = CALLEE;
                        } else userType = LISTENER;
                    }

                    if(listener!=null)
                        listener.callAccepted(callIndex, userType, call.jfuid);

                }
            }

            if(call.state==CALLING){
                if(callSate!=2){
                    item = call;
                    callSate = call.state;
                    localState = call.state;
                    liveCallListener();
                }
            }

            if(call.state==CALLREJECTED){
                if(callSate==2){
                    //remove call
                    removeCall();
                    callSate = 0;
                    localState = 0;
                    item = new EntityCall();
                    item.index = callIndex;
                    callingEntity.remove(jfuid);
                    //setIntial
                    initialState();

                }
            }
        }
    }

    public void removeCallFinally() {
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText("Removing call...");
        removeCall();
        if(listener!=null) listener.onRemovedCallAt(callIndex);

    }


    private void removeCall(){
        removeLocaLcall(videoviewCallLayout);
        removeRemoteAudio();
        removeRemoteVideo(videoviewCallLayout);
    }

    public void updateLocalCallObject(LocalParticipant localParticipant,
                                      CameraCapturerCompat cameraCapturerCompat,
                                      LocalAudioTrack localAudioTrack,
                                      LocalVideoTrack localVideoTrack
    ){

        this.localParticipant = localParticipant;
        this.cameraCapturerCompat = cameraCapturerCompat;
        this.localAudioTrack = localAudioTrack;
        this.localVideoTrack = localVideoTrack;
        liveCallListener();
    }

    public void updateRemoteCallObject(VideoTrack vtrack, AudioTrack aTrack){
        this.videoTrack = vtrack;
        this.audioTrack = aTrack;

        liveCallListener();
    }

    public void liveCallListener(){
        if(item.state==-1){
            Const.amIOnCall = false;
            removeLocaLcall(videoviewCallLayout);
            removeRemoteAudio();
            removeRemoteVideo(videoviewCallLayout);
            return;
        }
        if(item.jfuid!=null){
            if(item.state==CALLING){
                tvMessage.setVisibility(View.VISIBLE);

                if(isOwner){
                    tvMessage.setText(R.string.got_call_request);
                }else {
                    tvMessage.setText(R.string.join_call);

                    Const.amIOnCall = Objects.equals(item.jfuid, local_user_fuid);

                }

                preCall();
            }
            if(item.state==CALLACCEPTED && localState!=CALLACCEPTED){
                tvMessage.setVisibility(View.GONE);
                if(isOwner){
                    ownerView(item);
                }else {
                    loge(TAG, "Callee", item.jfuid, local_user_fuid);
                    if (Objects.equals(item.jfuid, local_user_fuid)) {
                        Const.amIOnCall = true;
                        calleeUser(item);
                    } else
                        viewerView(item);
                }

                localState = item.state;
            }

            if(item.state==CALLREJECTED){
                Const.amIOnCall = false;
                tvMessage.setVisibility(View.VISIBLE);
                tvMessage.setText("Call rejected");
            }

        }
    }


    void preCall(){
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) imageCallLayout.getLayoutParams();
        int min = Math.min(myparent.getWidth(), myparent.getHeight());
        params.width = min / 2;
        params.height = min / 2;
        imageCallLayout.setLayoutParams(params);
        params.setMargins(10, 10, 10, 10);
        imageCallLayout.setImageResource(Objects.equals(calltype, RemoteTrack.vcall)?R.drawable.videocam:R.drawable.ic_call);
        imageCallLayout.setVisibility(View.VISIBLE);

    }

    void ownerView(EntityCall track) {
        loge(TAG, "ownerView", ""+track.state, ""+track.index);

        if (Objects.equals(calltype, RemoteTrack.vcall)) {
            imageCallLayout.setVisibility(View.GONE);
            //videoviewCallLayout.setVisibility(View.VISIBLE);
        } else {
            ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) imageCallLayout.getLayoutParams();
            int min = Math.min(myparent.getWidth(), myparent.getHeight());
            params2.width = min / 2;
            params2.height = min / 2;
            imageCallLayout.setLayoutParams(params2);
            imageCallLayout.setPadding(10, 10, 10, 10);
            imageCallLayout.setImageResource(R.drawable.ic_baseline_speaker_phone_24);
        }

        call_option_layout.setVisibility(View.VISIBLE);
        call_option_layout.setImageResource(R.drawable.ic_baseline_more_vert_24);
        call_option_layout.setOnClickListener(v-> {

            List<MenuUtils.IconPowerMenuItem> menuitem = new ArrayList<>();

            menuitem.add(new MenuUtils.IconPowerMenuItem(ContextCompat.getDrawable(v.getContext(),
                    track.isaudiomute?R.drawable.ic_mic_off_black_24dp:R.drawable.ic_baseline_volume_up_24),
                    track.isaudiomute?"Unmute Mic":"Mute Mic"));

            customPowerMenu = MenuUtils.createMenu(v, menuitem, (position, item) -> {
                if ("Unmute Mic".contentEquals(item.title)) {
                    muteLocalAudioTrack(false);
                }
                else if ("Mute Mic".contentEquals(item.title)) {
                    muteLocalAudioTrack(true);
                }

                customPowerMenu.dismiss();
            });
        });

        renderRemoteCall(videoviewCallLayout, track);

    }

    void viewerView(EntityCall track){

        if (Objects.equals(calltype, RemoteTrack.vcall)) {
            imageCallLayout.setVisibility(View.GONE);
            //videoviewCallLayout.setVisibility(View.VISIBLE);
        } else {
            ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) imageCallLayout.getLayoutParams();
            int min = Math.min(myparent.getWidth(), myparent.getHeight());
            params2.width = min / 2;
            params2.height = min / 2;
            imageCallLayout.setLayoutParams(params2);
            imageCallLayout.setPadding(10, 10, 10, 10);
            imageCallLayout.setImageResource(R.drawable.ic_baseline_speaker_phone_24);
        }

        call_option_layout.setVisibility(View.GONE);

        renderRemoteCall(videoviewCallLayout, track);

    }

    void calleeUser(EntityCall track) {
        loge(TAG, "calleeUser", "");

        if (Objects.equals(calltype, RemoteTrack.vcall)) {
            imageCallLayout.setVisibility(View.GONE);
            //videoviewCallLayout.setVisibility(View.VISIBLE);
        } else {
            ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) imageCallLayout.getLayoutParams();
            int min = Math.min(myparent.getWidth(), myparent.getHeight());
            params2.width = min / 2;
            params2.height = min / 2;
            params2.setMargins(10, 10, 10, 10);
            imageCallLayout.setLayoutParams(params2);
            imageCallLayout.setImageResource(R.drawable.ic_baseline_speaker_phone_24);
        }

        call_option_layout.setVisibility(View.VISIBLE);
        call_option_layout.setImageResource(R.drawable.ic_baseline_more_vert_24);
        call_option_layout.setOnClickListener(v->{
            callemenu(track, videoviewCallLayout);
        });

        renderLocalCall(videoviewCallLayout);
    }

    void callemenu(EntityCall track, VideoView videoView){
        List<MenuUtils.IconPowerMenuItem> menuitem = new ArrayList<>();

        menuitem.add(new MenuUtils.IconPowerMenuItem(
                ContextCompat.getDrawable(parentView.getContext(),
                        track.isaudiomute?R.drawable.ic_mic_off_black_24dp:R.drawable.ic_mic_white_24dp),
                track.isaudiomute?"Unmute Mic":"Mute Mic"));


        if (Objects.equals(calltype, RemoteTrack.vcall)){

            menuitem.add(new MenuUtils.IconPowerMenuItem(ContextCompat.getDrawable(parentView.getContext(),
                    track.iscameramute? R.drawable.ic_videocam_off_black_24dp:R.drawable.ic_videocam_white_24dp),
                    track.iscameramute?"Unmute Camera":"Mute Camera"));

            menuitem.add(new MenuUtils.IconPowerMenuItem(ContextCompat.getDrawable(parentView.getContext(),
                    track.isbackcamera?R.drawable.record_camera_switch_normal:R.drawable.record_camera_switch_normal),
                    track.isbackcamera?"Front Camera":"Back Camera"));
        }

        menuitem.add(new MenuUtils.IconPowerMenuItem(ContextCompat.getDrawable(parentView.getContext(),
                R.drawable.close_button_white), "End"));

        customPowerMenu = MenuUtils.createMenu(parentView, menuitem, (position, it) -> {
            if ("Unmute Mic".contentEquals(it.title)) { muteLocalAudioTrack(false); }
            else if ("Mute Mic".contentEquals(it.title)) { muteLocalAudioTrack(true); }
            else if ("Unmute Camera".contentEquals(it.title)) {
                muteLocalVideoTrack(true, videoView);
                track.iscameramute = true;
            }
            else if ("Mute Camera".contentEquals(it.title)) {
                muteLocalVideoTrack(false, videoView);
                track.iscameramute = false;
            }
            else if ("Front Camera".contentEquals(it.title)) {
                track.isbackcamera=isBackCamera();
            }
            else if ("Back Camera".contentEquals(it.title)) {
                track.isbackcamera=isBackCamera();
            }
            else if ("End".contentEquals(it.title)) {
                closeCall(track.jfuid);
            }

            customPowerMenu.dismiss();

        });
    }

    void closeCall(String tofuid) {
        call_option_layout.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        FireFun.acceptCall(broad_usr_fuid, tofuid, CALLREJECTED);
    }

    public boolean isBackCamera(){
        if (cameraCapturerCompat != null) {
            CameraCapturer.CameraSource cameraSource = cameraCapturerCompat.getCameraSource();
            cameraCapturerCompat.switchCamera();

            return cameraSource == CameraCapturer.CameraSource.BACK_CAMERA;

        }
        return false;
    }

    public void muteLocalAudioTrack(boolean b){
        if(localAudioTrack==null || localParticipant==null) return;
        if(b) {
            // Disable audio track. Audio still flows to participants but the audio is silent.
            localAudioTrack.enable(false);
            // Release and unpublish audio track. Audio no longer flows to participants.
            //localAudioTrack.release();
            //localParticipant.unpublish(localAudioTrack);
            localParticipant.unpublishTrack(localAudioTrack);

        }else {
            // Disable audio track. Audio still flows to participants but the audio is silent.
            localAudioTrack.enable(true);
            // Release and unpublish audio track. Audio no longer flows to participants.
            localParticipant.publishTrack(localAudioTrack);
        }

    }

    public void muteLocalVideoTrack(boolean b, VideoView videoView){
        if (localVideoTrack == null || localParticipant == null) return;
        if(b) {
            try {
                // Disable audio track. Audio still flows to participants but the audio is silent.
                localVideoTrack.enable(false);
                // Release and unpublish audio track. Audio no longer flows to participants.
                //localVideoTrack.release();
                //localParticipant.unpublish(localAudioTrack);
                localParticipant.unpublishTrack(localVideoTrack);
                videoView.setVisibility(View.GONE);
            }catch (IllegalArgumentException e){}
        }else{
            localVideoTrack.enable(true);
            videoView.setVisibility(View.VISIBLE);
            localParticipant.publishTrack(localVideoTrack);
        }
    }

    public void removeRemoteVideo(VideoView videoView){
        if(videoTrack!=null)
            videoTrack.removeRenderer(videoView);

        videoView.setVisibility(View.GONE);
        videoTrack = null;
    }

    public void removeRemoteAudio(){
        if(audioTrack!=null)
            audioTrack.removeSink(audioSink);

        audioTrack = null;
    }

    public boolean renderLocalCall(VideoView videoView){

        loge(TAG, "renderLocalCall", "");
        if(localVideoTrack!=null && videoView.getVisibility()==View.GONE){
            videoView.setVisibility(View.VISIBLE);
            localVideoTrack.addRenderer(videoView);
            videoView.setMirror(true);
        }

        if(Objects.equals(calltype, RemoteTrack.vcall) && localParticipant!=null &&  localVideoTrack!=null) {
            boolean vstat = localParticipant.publishTrack(localAudioTrack);
            boolean astat = localParticipant.publishTrack(localVideoTrack);
            loge(TAG, "renderLocalCall", "v:"+vstat, "a:"+astat);
            return true;
        }

        if(Objects.equals(calltype, RemoteTrack.acall) && localParticipant!=null && localAudioTrack!=null) {
            boolean astat = localParticipant.publishTrack(localAudioTrack);
            loge(TAG, "renderLocalCall", "a:"+astat);
            return true;
        }

        return false;
    }

    public void removeLocaLcall(VideoView videoView){
        if(!isOwner) {
            if (localVideoTrack != null) {
                try {
                    localVideoTrack.removeRenderer(videoView);
                    // Disable audio track. Audio still flows to participants but the audio is silent.
                    localVideoTrack.enable(false);
                    // Release and unpublish audio track. Audio no longer flows to participants.
                    localVideoTrack.release();
                    //localParticipant.unpublish(localAudioTrack);
                    localParticipant.unpublishTrack(localVideoTrack);
                }catch (IllegalArgumentException e){}

            }

            if (localAudioTrack != null)
                localAudioTrack.removeSink(audioSink);
        }
    }


    public boolean renderRemoteCall(VideoView videoView, EntityCall item){
        if(Objects.equals(calltype, RemoteTrack.vcall) && videoView.getVisibility()==View.GONE) {
            loge(TAG, "renderRemoteCall2 "+new Gson().toJson(item));
            if(videoTrack!=null){
                videoView.setVisibility(View.VISIBLE);
                videoTrack.addRenderer(videoView);
                videoView.setMirror(true);
                waitingRemoteTracks = false;
                return true;
            } else {waitingRemoteTracks = true;}
        }else {
            if(audioTrack!=null){
                audioTrack.addSink(audioSink);
                waitingRemoteTracks = false;
                return true;
            }
            else {waitingRemoteTracks = true;}
        }

        return false;
    }


    void muteRemoteAudio(RemoteAudioTrack track, boolean mute){
        // Disable playback of a remote audio track
        if(track!=null) track.enablePlayback(mute);

    }

    void muteRemoteVideo(RemoteVideoTrack track){
        // Disable playback of a remote audio track
        //track.removeRenderer(false);
    }

    public void setListener(OnStartLocalRender listener) {
        this.listener = listener;
    }

    public void setEntity(EntityCall item) {
        this.item = item;
    }

    public void callType(String ctype){
        this.calltype = ctype;
        preCall();
    }

    public void owner(boolean isowner){
        this.isOwner = isowner;
        initialState();
    }


    public void index(int j) {
        this.callIndex = j;
    }

    public void setVideoTrack(VideoTrack videoTrack) {
        this.videoTrack = videoTrack;
    }

    public void set_usr_fuid(String broad_usr_fuid, String local_user_fuid) {
        this.broad_usr_fuid = broad_usr_fuid;
        this.local_user_fuid = local_user_fuid;
    }

    public void recvCallObjVideo(String identity, VideoTrack videoTrack){
        if(item!=null && item.state==CALLACCEPTED){
            this.videoTrack = videoTrack;

            renderRemoteCall(videoviewCallLayout, item);
        }
    }

    public void recvCallObjAudio(String identity, AudioTrack audioTrack){
        if(item!=null && item.state==CALLACCEPTED){
            this.audioTrack = audioTrack;

            renderRemoteCall(videoviewCallLayout, item);
        }

    }

    private void setMyCallSatatus(int state){
        if(item.jfuid!=null && item.jfuid.equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
            Const.myCallStatus = state;
    }



}
