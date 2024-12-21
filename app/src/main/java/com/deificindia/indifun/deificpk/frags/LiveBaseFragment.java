package com.deificindia.indifun.deificpk.frags;

import android.animation.Animator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.room.util.StringUtil;
import androidx.transition.TransitionManager;

import com.airbnb.lottie.LottieAnimationView;
import com.deificindia.chat.Model.User;
import com.deificindia.firebaseModel.AddLiveModel;
import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.Model.TokenRess;
import com.deificindia.indifun.Model.abs_plugs.JoinerName;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.Logger;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.db.LiveAppDb;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.db.LiveRepo2;
import com.deificindia.indifun.db.PKInfo;
import com.deificindia.indifun.db.TempRepo;
import com.deificindia.indifun.deificpk.actionsheets.LiveRoomUserListActionSheet;
import com.deificindia.indifun.deificpk.animutils.AnimationPlayer;
import com.deificindia.indifun.deificpk.animutils.GiftAnimView;
import com.deificindia.indifun.deificpk.animutils.GiftHeartAnimView;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.deificpk.tools.MediaPlayerHelper;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.deificpk.widgets.TopSenderSeatWidget;
import com.deificindia.indifun.deificpk.widgets.messeging.LiveRoomMessageView;
import com.deificindia.indifun.anim.AnimUtils;
import com.deificindia.indifun.anim.TranslateView;
import com.deificindia.indifun.db.LiveEntity;
import com.deificindia.indifun.deificpk.actionsheets.BeautySettingActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.PkRoomListActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.toolactionsheet.LiveRoomToolActionSheet;
import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.deificpk.modals.ChatModal;
import com.deificindia.indifun.deificpk.modals.RoomUserInfo;
import com.deificindia.indifun.deificpk.utils.CameraCapturerCompat;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.utils.WidgetUtils;
import com.deificindia.indifun.deificpk.widgets.LiveBottomButtonLayout;
import com.deificindia.indifun.deificpk.widgets.LiveHostNameLayout;
import com.deificindia.indifun.deificpk.widgets.LiveMessageEditLayout;
import com.deificindia.indifun.deificpk.widgets.LiveRoomUserLayout;
import com.deificindia.indifun.deificpk.widgets.PkLayout;
import com.deificindia.indifun.deificpk.actionsheets.ProfilePreviewBottomSheetDialog;
import com.deificindia.indifun.fires.LiveFireFun;
import com.deificindia.indifun.fires.RandomPk;
import com.deificindia.indifun.fires.UserJoinedState;
import com.deificindia.indifun.fires.m.UserJoinedInfo;
import com.deificindia.indifun.interfaces.OnEndTopLottiePlayer;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.twilio.audioswitch.AudioSwitch;
import com.twilio.video.AudioCodec;
import com.twilio.video.CameraCapturer;
import com.twilio.video.ConnectOptions;
import com.twilio.video.EncodingParameters;
import com.twilio.video.LocalAudioTrack;
import com.twilio.video.LocalParticipant;
import com.twilio.video.LocalVideoTrack;
import com.twilio.video.RemoteAudioTrack;
import com.twilio.video.RemoteAudioTrackPublication;
import com.twilio.video.RemoteDataTrack;
import com.twilio.video.RemoteDataTrackPublication;
import com.twilio.video.RemoteParticipant;
import com.twilio.video.RemoteVideoTrack;
import com.twilio.video.RemoteVideoTrackPublication;
import com.twilio.video.Room;
import com.twilio.video.TwilioException;
import com.twilio.video.Video;
import com.twilio.video.VideoCodec;
import com.twilio.video.VideoRenderer;
import com.twilio.video.VideoTrack;
import com.twilio.video.VideoView;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.deificindia.indifun.Utility.Constants.GET_JOINERNAMEINFO;
import static com.deificindia.indifun.Utility.Constants.RESETUPDATESERVERTIMEANDPKID;
import static com.deificindia.indifun.Utility.Constants.UPDATEGIFTPOINT;
import static com.deificindia.indifun.Utility.Constants.UPDATESERVERTIMEANDPKID;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.Utility.MySharePref.DAILY_HEART;
import static com.deificindia.indifun.Utility.MySharePref.getIntData;
import static com.deificindia.indifun.Utility.MySharePref.saveIntData;
import static com.deificindia.indifun.Utility.URLs.UserImageBaseUrl;
import static com.deificindia.indifun.deificpk.modals.LiveConfig.LOCAL_AUDIO_TRACK_NAME;
import static com.deificindia.indifun.deificpk.modals.LiveConfig.LOCAL_VIDEO_TRACK_NAME;
import static com.deificindia.indifun.deificpk.modals.LiveConfig.PREF_AUDIO_CODEC;
import static com.deificindia.indifun.deificpk.modals.LiveConfig.PREF_AUDIO_CODEC_DEFAULT;
import static com.deificindia.indifun.deificpk.modals.LiveConfig.PREF_VIDEO_CODEC;
import static com.deificindia.indifun.deificpk.modals.LiveConfig.PREF_VIDEO_CODEC_DEFAULT;
import static com.deificindia.indifun.deificpk.utils.Const.loge;
import static com.deificindia.indifun.rest.RetroCallListener.ENDPK;
import static com.deificindia.indifun.rest.RetroCallListener.GET_SERVER_TIME;
import static com.deificindia.indifun.rest.RetroCallListener.GIFT_DURING_BROAD;
import static com.deificindia.indifun.rest.RetroCallListener.HEART_POINT;
import static com.deificindia.indifun.rest.RetroCallListener.JOINER_NAME;

public abstract class LiveBaseFragment extends LiveControllFragement
    implements LiveRoomUserLayout.UserLayoutListener,
        LiveRoomUserListActionSheet.OnUserSelectedListener,
        ProfilePreviewBottomSheetDialog.OnProfileItemSelectedListener
{

    private static final String TAG = "LiveBaseFragment";
    protected static final String ARG_CLIP = "clip";
    protected static final String ddc = "Participant disconnected because of duplicate identity";
    //private static final String TAG = "VerticalFramgment";

    //keyboard
    protected InputMethodManager inputMethodManager;
    protected Rect mDecorViewRect;
    protected int mInputMethodHeight;

    protected boolean isSoftInputShown;

    protected ConstraintSet set_base, set_pk;

    protected View layout_call, layCloseCall;
    protected TextView txt_closeCalls;
    protected ImageView background_profile_image;
    protected VideoView local_or_broadcast_video_view;

    protected VideoRenderer localVideoView;
    protected VideoRenderer remoteVideoView;
    TextView ok ;
    protected VideoTrack localVideoTrack1;
    protected VideoTrack pkVideoTrack1;

    protected AudioSwitch audioSwitch;

    private AudioCodec audioCodec;
    private VideoCodec videoCodec;
    private EncodingParameters encodingParameters;

    Room room;

    protected boolean isleftaquired=false;
    protected boolean isrightaquired=false;
    protected boolean shouldReconnect, disconnecting;
    protected boolean isEndingPk;
    protected String disconnectexception="";
    /////////////Views/////////////////////////
    protected FrameLayout topFrame;
    boolean isFrameOpen;

    protected ConstraintLayout constraintRoot;
    protected PkLayout pkLayout;
    protected TopSenderSeatWidget leaftSeat, rightSeat;
    protected LiveBottomButtonLayout liveBottomButtonLayout;
    protected LiveRoomMessageView liveRoomMessageView;
    protected LiveMessageEditLayout messageEditLayout;
    protected RelativeLayout msg1;
    protected AppCompatEditText messageEditText;
    LinearLayout Linela ;
    protected AppCompatTextView live_daily_heart_count;
    protected AppCompatImageView mStartPkButton;
    protected AppCompatImageView mStartCallingButton;
    protected AppCompatImageView mSpeaker, mSpeakerPk;
    protected PkRoomListActionSheet mPkRoomListActionSheet;

    protected LiveHostNameLayout mNamePad;
    protected LiveRoomUserLayout participants;

    protected View default_message_layut;
    protected Guideline guide8;

    //protected LottieAnimationView lottieAnimationView;
    //protected SVGAImageView svgaImageView;
    protected AnimationPlayer animationPlayer;
    protected UserJoinedState userJoinedState;

    protected boolean isPkLayout;
    protected  String err;
    //protected FirebaseUser firebaseUser;
    protected LinearLayout
            itemLayout1,
            itemLayout2,
            layout_room_enter_animation,
            layout_full_gift_animation,
            layout_gift_heart_animation,
            call_layoutcallingusers;

    public TextView heartCollect1, coincollect1, levelcollect1;

    protected RelativeLayout layout_heart_animation_area;
    protected LiveRoomUserListActionSheet mRoomUserActionSheet;
    protected ProfilePreviewBottomSheetDialog profilePreviewBottomSheetDialog;
    protected void dismissUserShortProfile(){
        //if(profilePreviewBottomSheetDialog!=null)  profilePreviewBottomSheetDialog.dismiss();
    }
    boolean disconnecting_from_room_to_join_prim_owner;
    boolean disconnecting_from_room_to_join_own_room;

    ///////////////////////////////////////////////////////////////////////////////////

    protected BroadcastReceiver mHeadPhoneReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AudioManager.ACTION_HEADSET_PLUG.equals(action)) {
                boolean plugged = intent.getIntExtra("state", -1) == 1;
                boolean hasMic = intent.getIntExtra("microphone", -1) == 1;
                mHeadsetWithMicrophonePlugged = plugged && hasMic;
                loge("Wired headset is plugged：" + mHeadsetWithMicrophonePlugged);
            }
        }
    };


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void callOrPkButtonVisiblity() {
        if(!vm.isOwner){
            mStartPkButton.setVisibility(View.GONE);
            mStartCallingButton.setVisibility(View.GONE);
           // playTopInfoAnim( "10-second-countdown.json");
        }else {
            if(vm.isPkMode || vm.state==7){
                mStartPkButton.setVisibility(View.GONE);
                mStartCallingButton.setVisibility(View.GONE);
            //    playTopInfoAnim( "10-second-countdown.json");
            }else {
                mStartPkButton.setVisibility(View.VISIBLE);
                mStartCallingButton.setVisibility(View.VISIBLE);
              //  playTopInfoAnim( "10-second-countdown.json");
            }
        }
    }


    AudioManager audioManager;
    protected MediaPlayerHelper mediaPlayerHelper;

    protected void initAudioManager(){
        getActivity().setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        if(vm.isOwner)
            mediaPlayerHelper = new MediaPlayerHelper();

        speaker();
    }

    private void configureAudio(boolean enable) {
        if (enable) {
            config().previousAudioMode = audioManager.getMode();
            // Request audio focus before making any device switch
            requestAudioFocus();
            /*
             * Use MODE_IN_COMMUNICATION as the default audio mode. It is required
             * to be in this mode when playout and/or recording starts for the best
             * possible VoIP performance. Some devices have difficulties with
             * speaker mode if this is not set.
             */
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            /*
             * Always disable microphone mute during a WebRTC call.
             */
            config().previousMicrophoneMute = audioManager.isMicrophoneMute();
            audioManager.setMicrophoneMute(false);
        } else {
            audioManager.setMode(config().previousAudioMode);
            audioManager.abandonAudioFocus(null);
            audioManager.setMicrophoneMute(config().previousMicrophoneMute);
        }
    }

    private void requestAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes playbackAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            AudioFocusRequest focusRequest =
                    new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                            .setAudioAttributes(playbackAttributes)
                            .setAcceptsDelayedFocusGain(true)
                            .setOnAudioFocusChangeListener(
                                    i -> {
                                    })
                            .build();
            audioManager.requestAudioFocus(focusRequest);
        } else {
            audioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL,
                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }
    }

    void muteCamera(){
        if (localVideoTrack != null) {
            boolean enable = !localVideoTrack.isEnabled();
            localVideoTrack.enable(enable);
        }
    }

    void muteAudio(){
        if (localAudioTrack != null) {
            boolean enable = !localAudioTrack.isEnabled();
            localAudioTrack.enable(enable);
        }
    }

    void speaker(){
        boolean b = config().isSpeakerPhoneEnabled.get();
        if(mSpeaker!=null) mSpeaker.setActivated(b);
        if(mSpeakerPk!=null) mSpeakerPk.setActivated(b);
        muteSpeaker(b);
        config().isSpeakerPhoneEnabled.set(!b);
    }

    void muteUser(){
        speakerOffDisableToggle();
        liveBottomButtonLayout.setIsmuted(true);
    }

    void speakerOffDisableToggle(){
        muteSpeaker(false);
        config().isSpeakerPhoneEnabled.set(false);
        mSpeaker.setActivated(false);
        mSpeakerPk.setActivated(false);
        mSpeaker.setEnabled(false);
        mSpeakerPk.setEnabled(false);

    }

    void muteSpeaker(boolean isSpeakerOn){
        if(audioManager!=null) audioManager.setSpeakerphoneOn(isSpeakerOn);
    }

    void switchCamera(){
        if (cameraCapturerCompat != null) {
            CameraCapturer.CameraSource cameraSource = cameraCapturerCompat.getCameraSource();
            cameraCapturerCompat.switchCamera();
            if (pkLayout.getLeftVideoLayout().getVisibility() == View.VISIBLE) {
                pkLayout.getLeftVideoLayout().setMirror(cameraSource == CameraCapturer.CameraSource.BACK_CAMERA);
            }else {
                local_or_broadcast_video_view.setMirror(cameraSource == CameraCapturer.CameraSource.BACK_CAMERA);
            }
        }
    }

    LiveRoomToolActionSheet.LiveRoomToolActionSheetListener actionSheetListene_liveRoomTool =
            new LiveRoomToolActionSheet.LiveRoomToolActionSheetListener(){

                @Override
                public void onActionSheetRealDataClicked() { }

                @Override
                public void onActionSheetSettingClicked() {
                    showActionSheetDialog(ACTION_SHEET_VIDEO, vm.tabId, vm.isOwner, false, this);
                }

                @Override
                public void onActionSheetRotateClicked() { switchCamera(); }

                @Override
                public void onActionSheetVideoClicked(boolean muted) {
                    if (/*isHost || */vm.isOwner) {
                        //rtcEngine().muteLocalVideoStream(muted);
                        muteCamera();
                        config().isVideoMuted=muted;
                    }
                }

                @Override
                public void onActionSheetSpeakerClicked(boolean muted) {
                    if (/*isHost || */vm.isOwner) {
                        //rtcEngine().muteLocalAudioStream(muted);
                        muteAudio();
                        config().isAudioMuted=muted;
                    }
                }

                @Override
                public boolean onActionSheetEarMonitoringClicked(boolean monitor) {
                    if (monitor) {
                        if (mHeadsetWithMicrophonePlugged) {
                            //rtcEngine().enableInEarMonitoring(true);
                            inEarMonitorEnabled = true;
                            return true;
                        } else {
                            showShortToast(getResources().getString(R.string.in_ear_monitoring_failed));
                            // In ear monitor state does not change here.
                            return false;
                        }
                    } else {
                        //rtcEngine().enableInEarMonitoring(false);
                        // It is always allowed to disable the in-ear monitoring.
                        inEarMonitorEnabled = false;
                        return true;
                    }
                }
            };

    BeautySettingActionSheet.BeautyActionSheetListener actionBeautimode = new BeautySettingActionSheet.BeautyActionSheetListener(){

        @Override
        public void onActionSheetBeautyEnabled(boolean enabled) {

        }

        @Override
        public void onActionSheetBlurSelected(float blur) {

        }

        @Override
        public void onActionSheetWhitenSelected(float whiten) {

        }

        @Override
        public void onActionSheetCheekSelected(float cheek) {

        }

        @Override
        public void onActionSheetEyeEnlargeSelected(float eye) {

        }
    };


    AnimationPlayer.GiftReadyListener animationListener = new AnimationPlayer.GiftReadyListener() {

        @Override
        public void onGiftReady(Object obj) {

        }

        @Override
        public void onGiftStart(Object obj) {
            if(obj instanceof GiftInfo2){
                ///...send gift to broadcast to show other user and owner...
                sendFullScreenGift(vm.broad_room_id, vm.broad_usr_fuid, credentials,vm.local_level, (GiftInfo2) obj);

                loge(TAG, "sendFullScreenGift", ""+vm.broad_pkid, ""+vm.broadcast_id);
                ///---update gift point to owner/self  recvd on from gift---
                GiftInfo2 info = (GiftInfo2) obj;
                RetroCalls.instance()
                        .intParam(info.id, vm.isPkMode?3:2)
                        .stringParam(vm.broad_usr_wuid,
                                vm.local_wuid,
                                String.valueOf(vm.broad_pkid),
                                String.valueOf(vm.broadcast_id),
                                vm.broad_usr_fuid,
                                vm.pk_usr_uid
                        ).sendgift(null);

            } else if(obj instanceof ChatModal){
                ChatModal gift = (ChatModal) obj;

                showFullScreenGiftInfo(layout_full_gift_animation,
                        UserImageBaseUrl + gift.senderavtar,
                        gift.sendername, gift.giftname, gift.senderxp);
                //gift.sendername, gift.giftname, gift.senderxp,gift.level);

            }
        }

        @Override
        public void onGiftPlayEnd(Object obj) {

        }
    };

    protected boolean isnameavtarset;

    protected int get_daily_heart(){
        return getIntData(requireContext(), DAILY_HEART, 0);
    }

    protected void setNameAvtar(){
        if(isnameavtarset) return;
        if(vm.broad_usr_name!=null){
            mNamePad.setName(vm.broad_usr_name, vm.broad_usr_fuid,vm.broad_usr_wuid, vm.isOwner, mClip.is_following==1);
        }

        if(vm.broad_usr_avtar!=null){
            mNamePad.setAvtarByLink(vm.broad_usr_avtar, vm.broad_usr_avtar_type);
        }
        isnameavtarset = true;

        Picasso.get().load(URLs.avtarBaseUrl(vm.broad_usr_avtar_type)+vm.broad_usr_avtar)
                .placeholder(LevelControll.background())
                .error(LevelControll.background())
                .into(background_profile_image);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    protected final PkRoomListActionSheet.OnPkRoomSelectedListener onInviteListener =
        new PkRoomListActionSheet.OnPkRoomSelectedListener() {
            @Override
            public void onPkRoomListActionSheetRoomSelected(int position, String roomId, String uid, long pkTime1) {
                vm.isPkSender = true;
                //vm.pk_room_user_identity=uid;
                //swap room id==sender room id becomes pk room id
                vm.pk_room_id = vm.broad_room_id;

                LiveFireFun.instance().inviteUserForPk(vm.broad_usr_fuid, uid/*remote owner fuid*/,
                        new PKInfo(
                        vm.broad_usr_fuid,
                        vm.broad_usr_wuid,
                        vm.broad_usr_avtar,
                        vm.broad_usr_avtar_type,
                        vm.broad_usr_name,
                        vm.broadcast_id,
                                pkTime1,
                        vm.broad_room_id,
                        vm.broad_room_name).toMap(),
                        STAT_PK_INVITE, null);

                //FireFun.change_sender_state(vm.broad_usr_fuid, roomId, true, pktime, STAT_PK_INVITE);

                dismissActionSheetDialog();
                playTopInfoAnim( "10-second-countdown.json");
                rejecting_after_30sec_timeout(uid);

            }

            @Override
            public void onRandomPkClickListener(long pkTime1) {
                //showShortToast("random pk to be implemented");
                //findRandUser(vm.broad_usr_fuid, vm.broad_usr_wuid, vm.broad_room_id);
                findRandUser(pkTime1);
                playTopInfoAnim( "ripple.json");
                dismissActionSheetDialog();
            }
        };

    void findRandUser(long pkTime1){
        RandomPk.instance().with(
                new PKInfo(
                        vm.broad_usr_fuid,
                        vm.broad_usr_wuid,
                        vm.broad_usr_avtar,
                        vm.broad_usr_avtar_type,
                        vm.broad_usr_name,
                        vm.broadcast_id,
                        pkTime1,
                        vm.broad_room_id,
                        vm.broad_room_name),

                status -> {

                    if(status== RandomPk.State.PKSARTEDWAITING){
                    }else {
                        removeViewFromTop();
                    }
                }
        );
    }

    //resume to broad state == 1 on both owner
    void rejecting_after_30sec_timeout(String pk_user_uid1){
        new Handler(Looper.myLooper()).postDelayed(()->{
            removeViewFromTop();
            if(vm.state!=3) {
                LiveFireFun.instance().resumetobroadstate(vm.broad_usr_fuid,
                        pk_user_uid1,
                        false,
                        STAT_BROADCAST);
            }
        },20*1000);
    }


    boolean pk_invite_dialog_used;
    Dialog pk_invite_dialog;

    protected void background_avtar(int n){
        //background_profile_image.setVisibility(n==UiUtils.GONE?View.GONE:View.VISIBLE);
    }

    void dismiss_pk_invite_info_dialog(){
        if(pk_invite_dialog!=null) {
            pk_invite_dialog.dismiss();
            pk_invite_dialog = null;
        }
    }

    protected Dialog curDialog;
    protected void dismissDialog () {
        if (curDialog != null && curDialog.isShowing()) {
            curDialog.dismiss();
        }
    }

    protected void onPkInvitedialog(LiveEntity2 info){

        String title = getResources().getString(R.string.live_room_pk_room_receive_pk_request_title);
        String messageFormat = getResources().getString(R.string.live_room_pk_room_receive_pk_request_message);
        String message = String.format(messageFormat, info.PkInfo.pk_user_name, String.valueOf(info.PkInfo.pk_dusration/60));

        act.runOnUiThread(() ->{
            dismiss_pk_invite_info_dialog();
            pk_invite_dialog_used = true;
            pk_invite_dialog = act.showDialog(title, message,
                    R.string.dialog_positive_button_accept, R.string.dialog_negative_button_refuse,
                    view -> {
                        //mPKManager.acceptPKInvitation(roomId, pkRoomId);
                        acceptPkFromDialog(info.PkInfo.pk_dusration, info.PkInfo.pk_user_fuid);
                        dismiss_pk_invite_info_dialog();
                    },
                    view -> {
                        rejectPkFromDialog(info.PkInfo.pk_user_fuid);
                        dismiss_pk_invite_info_dialog();
                    });
        });

    }
    boolean is_time_pk_id_send;
    boolean has_pk_layout_mode;

    protected void showUserShortProfile(String fuid, String wuid, String name ){
        //profilePreviewBottomSheetDialog = new ProfilePreviewBottomSheetDialog(fuid, wuid, vm.broad_room_id, name,  vm.isOwner, vm.isPkMode, vm.broad_usr_fuid);
       // profilePreviewBottomSheetDialog.show(getChildFragmentManager(), "profilePreviewBottomSheetDialog");

        loge(TAG,"showUserShortProfile", fuid, wuid);
        Map<String, Object> map = new HashMap<>();
        map.put("fuid", fuid);
        map.put("wuid", wuid);
        map.put("name", name);
        map.put("broad_room_id", vm.broad_room_id);
        map.put("isHost", true);
        map.put("isOwner", vm.isOwner);
        map.put("isPkMode", vm.isPkMode);
        map.put("broad_usr_fuid", vm.broad_usr_fuid);

        profilePreviewBottomSheetDialog = (ProfilePreviewBottomSheetDialog)
                showActionSheetDialog(ACTION_SHEET_PROFILE_PREVIEW, vm.broad_room_type, vm.isOwner, true, map, this);

        profilePreviewBottomSheetDialog.setup(map);

    }

    void rejectPkFromDialog(String pk_request_sender_user_uid){
        removeViewFromTop();
        /*FireFun.rejectInvitationNotifyToPrimOwner(
                vm.pk_usr_uid, vm.pk_room_id,
                *//*local broad info*//*
                vm.broad_usr_fuid, vm.broad_usr_name,
                vm.broad_usr_avtar, vm.broad_usr_avtar_type,
                false, false, STAT_REJECT_PK);

        FireFun.change_ispk(vm.broad_usr_fuid, false, STAT_BROADCAST);*/

        LiveFireFun.instance().rejectPkInvitation(
                vm.broad_usr_fuid,
                pk_request_sender_user_uid,
                new PKInfo(
                        vm.broad_usr_fuid,
                        vm.broad_usr_wuid,
                        vm.broad_usr_avtar,
                        vm.broad_usr_avtar_type,
                        vm.broad_usr_name,
                        vm.broadcast_id,
                        0,
                        vm.broad_room_id,
                        vm.broad_room_name).toMap());

    }


    //////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////animations////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////


    void lottiePlayer(boolean loop, String jsonFileFromAssets, OnEndTopLottiePlayer listener){
        //if (!isOwnerOnline) return;
        try {
            LottieAnimationView anim = new LottieAnimationView(getContext());
            removeViewFromTop();
            topFrame.addView(anim);
            anim.loop(loop);
            anim.setAnimation(jsonFileFromAssets);
            anim.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) { }
                @Override
                public void onAnimationEnd(Animator animator) {
                    if(listener!=null){
                        listener.onEnd(loop);
                    }
                    //if(!loop) topFrame.removeView(anim);
                }
                @Override
                public void onAnimationCancel(Animator animator) { }
                @Override
                public void onAnimationRepeat(Animator animator) { }
            });
            anim.playAnimation();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    /* play top general animation from assets json*/
    void playTopInfoAnim(String jsonFileFromAssets){
        lottiePlayer(true, jsonFileFromAssets, new OnEndTopLottiePlayer() {
            @Override
            public void onEnd(boolean looping) {
                if(!looping) removeViewFromTop();
            }
        });
    }

    /*
     * act of sec. owner
    *
    * */
    void acceptPkFromDialog(long pkTime1, String pk_request_sender_user_uid){
        //send to prim owner ==> inform user to change layout
        /*FireFun.acceptInvitationSecOwner(vm.pk_usr_uid, vm.pk_room_id,
                *//*local broad info*//*vm.broad_usr_fuid, vm.broad_usr_wuid, vm.broad_usr_name,
                vm.broad_usr_avtar,  vm.broad_usr_avtar_type, vm.broadcast_id,
                true, true, STAT_PK_MODE);*/

        LiveFireFun.instance().acceptInvitationSecOwner(vm.broad_usr_fuid, pk_request_sender_user_uid,
                new PKInfo(
                vm.broad_usr_fuid,
                vm.broad_usr_wuid,
                vm.broad_usr_avtar,
                vm.broad_usr_avtar_type,
                vm.broad_usr_name,
                vm.broadcast_id,
                pkTime1,
                vm.broad_room_id,
                vm.broad_room_name).toMap(),
                STAT_PK_MODE);

        vm.isPkSender = false; //accept dialog
        //send to sec. owner ==> inform user to change layout
        //////FireFun.change_ispk(vm.broad_usr_fuid, true, STAT_PK_MODE);
        //swichView2PkBySecOwner(); //switch view to pk wait for prim owner
        //disconnectFromRoom();

        //moveLocalVideoToThumbnailView(); //onaccet pk
        //joinBroad(2);

        //updateservertimeandpkid(1); //generate pk id on accept pk request


    }

    @Override
    protected void callCloseButtonVisiblity(int n) {
        layCloseCall.setVisibility(n==View.GONE?View.GONE:(vm.isOwner?View.VISIBLE:View.GONE));
    }

    //start sending FullScreen Gift
    //play locally and send to room
    protected void sendFullScreenGift(GiftInfo2 info, ChatModal modal, String anim){
        if(anim!=null){
            //queue full screen general animation not a gift
            //animationPlayer.generalFullScrenGift(new AnimationPlayer.GeneralModalData(), null);
        }

        if(info!=null) {
            ///queue gift to be sent
            animationPlayer.sendFullScreenGift(info);


        }

        if(modal!=null){
            //queue gift recved
            animationPlayer.recievedFullScrenGift(modal);

        }
    }

   /* //AnimEnqueueService.startService(getContext(), giftQueue);
    ResultReceiver giftQueue = new ResultReceiver(new Handler()){
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if(resultCode==PARCELID){
                AnimEnqueueService.ModalData data = resultData.getParcelable(ANIMENQUEUESERVICEDATA);
                Logger.loge(TAG, "JobTest " + data);
                if(data!=null){
                    int n = data.ints[0];
                    if(n==LOCALSENT){
                        senderGiftAnimation(AnimEnqueueService.giftQueue_LOCALSENT.poll());
                    } else if (n==RECIVED){
                        recieverGift(AnimEnqueueService.giftQueue_RECIVED.poll());
                    } else  if(n==ASSETS){
                        AnimEnqueueService.ModalData d = AnimEnqueueService.giftQueue_ASSETS.poll();
                        if(d!=null) playTopInfoAnim(d.strs[0]);
                        else notifyEndAnimation();
                    }
                }
            }
        }
    };*/



    /*play sender info ==> full screen gift*/
    protected void showFullScreenGiftInfo(LinearLayout layout_gift_animation, String userprofileimage,
                                        String username, String gifName, long userxp){
        GiftAnimView gav=new GiftAnimView(getContext());
        gav.init();
        layout_gift_animation.addView(gav);
        gav.setData(userprofileimage, username, userxp, "send "+gifName/*, UiUtils.rndDrawableGift()*/);

        AnimUtils.topGiftAnimView(getContext(), layout_gift_animation, gav);
    }

    //////////heart\\//gift\\///////////////////////////////////////
    //send HeartGift//heart clicked
    protected void playHeartAnimation(int n /* 0=>sender 1=> recvr*/, ChatModal modal) {
        if(layout_heart_animation_area==null) return;

        ImageView img = heartImage();
        layout_heart_animation_area.addView(img);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) img.getLayoutParams();
        params.width = 130;
        params.height = 130;
        img.setLayoutParams(params);
        AnimUtils.playHeartAnim(layout_heart_animation_area, img, liveBottomButtonLayout.xy(), TranslateView.getScreenHeight(getActivity()));
        if(n==0) {
            sendHeartGiftMsg(vm.broad_room_id, vm.broad_usr_fuid, credentials, vm.local_level);
            RetroCalls
                    .instance()
                    .callType(HEART_POINT)
                    .withUID()
                    .stringParam(vm.broad_usr_wuid, String.valueOf(vm.broadcast_id))
                    .heart_point((type_result, obj2) -> {
                        if(obj2!=null && obj2.getStatus()==1 && obj2.getResult()!=null){
                            int dailyheart = obj2.getResult().getHeartDaily();
                            saveIntData(getContext(), DAILY_HEART, dailyheart);
                            //live_daily_heart_count.setText(String.valueOf(dailyheart));
                        }
                    });
        }
        if(n==1 && modal!=null ){
           playHeartGiftInfo(layout_gift_heart_animation, modal.senderavtar, modal.sendername, modal.senderxp);
           showuser();
           // playHeartGiftInfo(layout_gift_heart_animation, "8039f16a1afd4bb13506d21b793bd7cc.png", "modal.sendername", 1000);
        }
    }

    public final ImageView heartImage() {
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(LevelControll.rndHeart());
        return imageView;
    }

    private void showuser() {
        String firebaseUser= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("BroadLike")
                .child(firebaseUser).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        // String a =  vm.broad_usr_fuid ;
                        User u = snapshot.getValue(User.class);
                        err = String.valueOf(snapshot.getChildrenCount());
//                      heart.setText(err);
                        loge("fdfd",err);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) { }

                });
    }

    public void playHeartGiftInfo(LinearLayout layout_gift_2_animation, String avtar, String username, long xp) {
        GiftHeartAnimView animView = new GiftHeartAnimView(getContext());
        layout_gift_2_animation.addView(animView);

        animView.init(UserImageBaseUrl+avtar, username, "send heart", R.drawable.ic_heart_red_1,
                LevelControll.getUsernameColor(LevelControll.getLevel(xp)));

        int viewWidth = animView.getWidth();
        animView.setX(-viewWidth);

        AnimUtils.sideGiftAnim(layout_gift_2_animation, animView, viewWidth);
    }

    void removeViewFromTop(){
        if(topFrame.getChildCount()>0) topFrame.removeAllViews();
    }

    ////////////////////////////////////////////////////
    ///////////////Room////////////////////////////////////
    ///////////////////////////////////////////////////
    protected void initLive(){
        getParam();
        starting_room_join();
    }

    private void getParam(){
        audioCodec = config().getAudioCodecPreference(PREF_AUDIO_CODEC, PREF_AUDIO_CODEC_DEFAULT);
        videoCodec = config().getVideoCodecPreference(PREF_VIDEO_CODEC, PREF_VIDEO_CODEC_DEFAULT);
        encodingParameters = config().getEncodingParameters();

    }

    void starting_room_join(){
        if(vm.broad_join_identity==null) {
            showToast("Could not join broad, you might not be logged in", Toast.LENGTH_SHORT);
            return;
        }

        /*get joined owners live data*/
        livevm.getLiveUser(vm.broad_usr_fuid).observe(this, this::readingLiveData);
        //initCallAdapter();
        livevm.getLiveCalls(vm.broad_room_id).observe(this, this::readCalls);

//        livevm.get_JoinerName_live(vm.broad_usr_fuid).observe(this, this::owner_joiner_name_level);

        if(vm.isOwner){
            createAudioAndVideoTracks();
            joinBroad(1); //creating room by owner
        }else {
            readPkInfoOnce(vm.broad_usr_fuid);
        }

        readPkInfoOnce(vm.broad_room_id);
        liveRoomMessageView.onJoinRoomFirsTime();

    }

    protected void xpUpdateOnGift(){
        Constants.sendToService(getContext(),
                new ControllModal(GET_JOINERNAMEINFO,
                        new String[]{vm.broad_usr_fuid},
                        new int[]{}));
    }

    protected void xpUpdateOnHeartReceived(){
        TempRepo.updateBroadXp(getContext(), vm.broad_usr_fuid, 1);
    }

    protected void createAudioAndVideoTracks() {
        createAudioTrack();
        createCameraCapturer();
        createLocalVideoTrack();
        if(config().isBackCam){
            switchCamera();
        }
        publishLocalTrack();
    }

    @Override
    protected void createTracksForCallingUser(String calltype) {
        loge(TAG, "createTracksForCallingUser", calltype);
        switch (calltype){
            case RemoteTrack.acall:
                createAudioTrack();
                break;
            case RemoteTrack.vcall:
                createAudioTrack();
                createCameraCapturer();
                createLocalVideoTrack();
                break;
        }
    }

    void createAudioTrack(){
        // Share your microphone
        localAudioTrack = LocalAudioTrack.create(getContext(), true, LOCAL_AUDIO_TRACK_NAME);
        configureAudio(true);
    }

    void createCameraCapturer(){
        // Share your camera
        cameraCapturerCompat = new CameraCapturerCompat(getContext(), getAvailableCameraSource());
    }

    void createLocalVideoTrack(){
        if (localVideoTrack == null && checkPermissionForCameraAndMicrophone() && cameraCapturerCompat != null) {
            try {
                localVideoTrack = LocalVideoTrack.create(requireContext(),
                        true,
                        cameraCapturerCompat.getVideoCapturer(),
                        LOCAL_VIDEO_TRACK_NAME);
            } catch (IllegalStateException e){ }
        }
    }

    void publishLocalTrack(){
        local_or_broadcast_video_view.setVisibility(View.VISIBLE);
        local_or_broadcast_video_view.setMirror(true);
        localVideoTrack.addRenderer(local_or_broadcast_video_view);
        localVideoView = local_or_broadcast_video_view;
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    // --first time user joined broad--
    // --will decide wat to join, pk or broad
    @Override
    protected void onReadPkInfoOnce(LiveEntity2 b) {
        if(b==null) return;
        vm.broad_usr_fuid = b.owneruid;

        if(b.state==STAT_PK_MODE && b.PkInfo!=null){
            vm.isPkMode = true;
            vm.pk_usr_uid = b.PkInfo.pk_user_fuid;

            if(!b.pksender){
                if (local_or_broadcast_video_view.getVisibility() == View.VISIBLE) {
                    local_or_broadcast_video_view.setVisibility(View.GONE);
                }

                //visible pkLayout
                vm.pk_room_id = b.PkInfo.pk_room_id;
                vm.userPkMode = true;
                vm.userisPkSender = false;
                vm.user_pk_room_id = b.PkInfo.pk_room_id;
                joinBroad(2); //user joins broad of remote owner in pk
                return;
            }

            if(local_or_broadcast_video_view.getVisibility()==View.VISIBLE) {
                local_or_broadcast_video_view.setVisibility(View.GONE);
            }
        } else {

            if (local_or_broadcast_video_view.getVisibility() == View.GONE)
                local_or_broadcast_video_view.setVisibility(View.VISIBLE);

        }
        joinBroad(1);  //user joins broad of local owner |
    }

    private void joinBroad(int n){
        switch(n){
            case 1:
                retrieveAccessTokenfromServer1(vm.broad_room_id, vm.broad_join_identity);
                break;
            case 2:
                retrieveAccessTokenfromServer1(vm.pk_room_id, vm.broad_join_identity);
                break;
        }

        joinFirebaseGroup();
    }

    private void retrieveAccessTokenfromServer1(String room_id1, String room_joiner_uid) {
        AppConfig.loadInterface().token(room_id1, room_joiner_uid)
                .enqueue(new Callback<TokenRess>() {
                    @Override
                    public void onResponse(Call<TokenRess> call, Response<TokenRess> response) {
                        if(response.isSuccessful()) {
                            TokenRess res = response.body();
                            assert res != null;
                            connectToRoom(res.result, room_id1);
                            Log.e(TAG, res.result);
                        }else{
                            Log.e(TAG, "response unsuccess");
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenRess> call, Throwable t) {
                        Log.e(TAG, "response unsuccess" + t.getMessage());
                    }
                });
    }

    public void connectToRoom(String token, String roomId) {
        if(token==null || token.isEmpty() || roomId==null || roomId.isEmpty()) {
            showShortToast("Empty room");
            return;
        }

        if(disconnecting) return;

        ConnectOptions.Builder connectOptions = new ConnectOptions
                .Builder(token)
                .roomName(roomId);

        if(vm.isOwner) {

            if (localAudioTrack != null) {
                connectOptions.audioTracks(Collections.singletonList(localAudioTrack));
            }

            if (localVideoTrack != null) {
                connectOptions.videoTracks(Collections.singletonList(localVideoTrack));
            }
        }

        connectOptions.preferAudioCodecs(Collections.singletonList(audioCodec));
        connectOptions.preferVideoCodecs(Collections.singletonList(videoCodec));

        connectOptions.encodingParameters(encodingParameters);

        //connectOptions.enableAutomaticSubscription(enableAutomaticSubscription);

        room = Video.connect(requireContext(), connectOptions.build(), roomListener());
        //setDisconnectAction();
    }

    private Room.Listener roomListener() {
        return new Room.Listener() {
            @Override
            public void onConnected(Room room) {
                loge(TAG,"onConnected to ", room.getName());
                localParticipant = room.getLocalParticipant();
                vm.connectedToRoom = room.getName();

                //0YU3juKldBOquZaXVLjtdggx3qb2kLapj2xOYYh2MxrqawCG7XzjeyL2
                //loge(TAG, "onConnected locaL", room.getLocalParticipant().getIdentity());
                if(room.getLocalParticipant()!=null){
//                    getLocalParticipant(room.getLocalParticipant()); //call listener
                    localConnectUserAction(true, room.getLocalParticipant());
                }

                //update to firebase
                upadateOnJoinRoomIsOwner1(STAT_BROADCAST); //on room created by owner

                for(RemoteParticipant remoteParticipant : room.getRemoteParticipants()) {
                    remoteParticipantList.add(remoteParticipant);
                    remoteParticipantConnectListener(remoteParticipant);
                }

                errorShowing = false;
                removeOffline();
            }

            @Override
            public void onConnectFailure(@NonNull Room room, @NonNull TwilioException twilioException) {
                loge(TAG,"onConnectFailure to " + room.getName() + ":"+ twilioException.getExplanation());
                // LiveBaseActivity | onConnectFailure to 059aa864968440c6a1c2ecaf664766c8: |
                configureAudio(false);
                if(room.getName().equals(vm.broad_room_id)) {
                    if (local_or_broadcast_video_view.getVisibility()==View.VISIBLE){
                        local_or_broadcast_video_view.setVisibility(View.GONE);
                        local_or_broadcast_video_view.setVisibility(View.VISIBLE);
                    }
                    if (pkLayout.getLeftVideoLayout().getVisibility()==View.VISIBLE){
                        pkLayout.getLeftVideoLayout().setVisibility(View.GONE);
                        pkLayout.getLeftVideoLayout().setVisibility(View.VISIBLE);
                    }
                }

                onErrorMessage("Could not connect to the group, try later.");
            }

            @Override
            public void onReconnecting(@NonNull Room room, @NonNull TwilioException twilioException) {
                loge(TAG,"onReconnecting to " + room.getName() + ":"+ twilioException.getMessage());
                onErrorMessage("Reconnecting.");
            }

            @Override
            public void onReconnected(@NonNull Room room) {
                loge(TAG,"onReconnected to " + room.getName());

                errorShowing = false;
                removeOffline();
            }

            @Override
            public void onDisconnected(@NonNull Room room, @Nullable TwilioException twilioException) {
                localParticipant = null;
                if(twilioException!=null)
                    disconnectexception = twilioException.getMessage();

                loge(TAG,"onDisconnected from " + room.getName() + ":"/*+twilioException.getMessage()*/);
                onDisconnectRoom(room, null);
                if(!disconnecting)
                    configureAudio(false);
            }

            @Override
            public void onParticipantConnected(@NonNull Room room, @NonNull RemoteParticipant remoteParticipant) {
                loge(TAG,"onParticipantConnected to " + room.getName()+ " identity:"+remoteParticipant.getIdentity());
                remoteParticipantConnectListener(remoteParticipant);
                //updateParticipant(0,true, remoteParticipant.getIdentity());
            }

            @Override
            public void onParticipantDisconnected(@NonNull Room room, @NonNull RemoteParticipant remoteParticipant) {
                loge(TAG,"onParticipantDisconnected to " + room.getName()+ " identity:"+remoteParticipant.getIdentity());
                onDisconnectRoom(room, remoteParticipant);

            }

            @Override
            public void onRecordingStarted(@NonNull Room room) {
                loge(TAG,"onRecordingStarted to " + room.getName());
            }

            @Override
            public void onRecordingStopped(@NonNull Room room) {
                loge(TAG,"onRecordingStopped to " + room.getName());
            }
        };
    }

    /////////////////////////////////////////////////////////
    private RemoteParticipant.Listener remoteParticipantListener() {
        return new RemoteParticipant.Listener() {
            @Override
            public void onAudioTrackPublished(RemoteParticipant remoteParticipant,
                                              RemoteAudioTrackPublication remoteAudioTrackPublication) {
                Log.i(TAG+"", String.format("onAudioTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.isTrackEnabled(),
                        remoteAudioTrackPublication.isTrackSubscribed(),
                        remoteAudioTrackPublication.getTrackName()));

                //updateAudioTrack(Constants.userUid(remoteParticipant.getIdentity(), vm.broad_usr_fuid).second, remoteAudioTrackPublication);
            }

            @Override
            public void onAudioTrackUnpublished(RemoteParticipant remoteParticipant,
                                                RemoteAudioTrackPublication remoteAudioTrackPublication) {
                Log.i(TAG+"", String.format("onAudioTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.isTrackEnabled(),
                        remoteAudioTrackPublication.isTrackSubscribed(),
                        remoteAudioTrackPublication.getTrackName()));

                //removeAudioTrac(Constants.userUid(remoteParticipant.getIdentity(), vm.broad_usr_fuid).second);
            }

            @Override
            public void onDataTrackPublished(RemoteParticipant remoteParticipant,
                                             RemoteDataTrackPublication remoteDataTrackPublication) {
                Log.i(TAG+"", String.format("onDataTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.isTrackEnabled(),
                        remoteDataTrackPublication.isTrackSubscribed(),
                        remoteDataTrackPublication.getTrackName()));
            }

            @Override
            public void onDataTrackUnpublished(RemoteParticipant remoteParticipant,
                                               RemoteDataTrackPublication remoteDataTrackPublication) {
                Log.i(TAG+"", String.format("onDataTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.isTrackEnabled(),
                        remoteDataTrackPublication.isTrackSubscribed(),
                        remoteDataTrackPublication.getTrackName()));
            }

            @Override
            public void onVideoTrackPublished(RemoteParticipant remoteParticipant,
                                              RemoteVideoTrackPublication remoteVideoTrackPublication) {
                Log.i(TAG+"", String.format("onVideoTrackPublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.isTrackEnabled(),
                        remoteVideoTrackPublication.isTrackSubscribed(),
                        remoteVideoTrackPublication.getTrackName()));

                //updateVideoTrack(Constants.userUid(remoteParticipant.getIdentity(), vm.broad_usr_fuid).second, remoteVideoTrackPublication);
            }

            @Override
            public void onVideoTrackUnpublished(RemoteParticipant remoteParticipant,
                                                RemoteVideoTrackPublication remoteVideoTrackPublication) {
                Log.i(TAG+"", String.format("onVideoTrackUnpublished: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%s, enabled=%b, " +
                                "subscribed=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.isTrackEnabled(),
                        remoteVideoTrackPublication.isTrackSubscribed(),
                        remoteVideoTrackPublication.getTrackName()));

                //removeVideoTrack(Constants.userUid(remoteParticipant.getIdentity(), vm.broad_usr_fuid).second);
            }

            @Override
            public void onAudioTrackSubscribed(RemoteParticipant remoteParticipant,
                                               RemoteAudioTrackPublication remoteAudioTrackPublication,
                                               RemoteAudioTrack remoteAudioTrack) {
                Log.i(TAG+"", String.format("onAudioTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrack: enabled=%b, playbackEnabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrack.isEnabled(),
                        remoteAudioTrack.isPlaybackEnabled(),
                        remoteAudioTrack.getName()));

                collectRemoteParticipantAudio(remoteParticipant.getIdentity(), remoteAudioTrack);
            }

            @Override
            public void onAudioTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                 RemoteAudioTrackPublication remoteAudioTrackPublication,
                                                 RemoteAudioTrack remoteAudioTrack) {
                Log.i(TAG+"", String.format("onAudioTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrack: enabled=%b, playbackEnabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrack.isEnabled(),
                        remoteAudioTrack.isPlaybackEnabled(),
                        remoteAudioTrack.getName()));
            }

            @Override
            public void onAudioTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                       RemoteAudioTrackPublication remoteAudioTrackPublication,
                                                       TwilioException twilioException) {
                Log.i(TAG+"", String.format("onAudioTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteAudioTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteAudioTrackPublication.getTrackSid(),
                        remoteAudioTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));
            }

            @Override
            public void onDataTrackSubscribed(RemoteParticipant remoteParticipant,
                                              RemoteDataTrackPublication remoteDataTrackPublication,
                                              RemoteDataTrack remoteDataTrack) {
                Log.i(TAG+"", String.format("onDataTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrack.isEnabled(),
                        remoteDataTrack.getName()));
            }

            @Override
            public void onDataTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                RemoteDataTrackPublication remoteDataTrackPublication,
                                                RemoteDataTrack remoteDataTrack) {
                Log.i(TAG+"", String.format("onDataTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrack.isEnabled(),
                        remoteDataTrack.getName()));
            }

            @Override
            public void onDataTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                      RemoteDataTrackPublication remoteDataTrackPublication,
                                                      TwilioException twilioException) {
                Log.i(TAG+"", String.format("onDataTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteDataTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteDataTrackPublication.getTrackSid(),
                        remoteDataTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));
            }

            @Override
            public void onVideoTrackSubscribed(RemoteParticipant remoteParticipant,
                                               RemoteVideoTrackPublication remoteVideoTrackPublication,
                                               RemoteVideoTrack remoteVideoTrack) {
                Log.i(TAG+"", String.format("onVideoTrackSubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrack.isEnabled(),
                        remoteVideoTrack.getName()));

                addRemoteParticipantVideo(remoteVideoTrack, remoteParticipant.getIdentity());
            }

            @Override
            public void onVideoTrackUnsubscribed(RemoteParticipant remoteParticipant,
                                                 RemoteVideoTrackPublication remoteVideoTrackPublication,
                                                 RemoteVideoTrack remoteVideoTrack) {
                Log.i(TAG+"", String.format("onVideoTrackUnsubscribed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrack: enabled=%b, name=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrack.isEnabled(),
                        remoteVideoTrack.getName()));

                removeParticipantVideo(remoteVideoTrack, "", remoteParticipant.getIdentity());
            }

            @Override
            public void onVideoTrackSubscriptionFailed(RemoteParticipant remoteParticipant,
                                                       RemoteVideoTrackPublication remoteVideoTrackPublication,
                                                       TwilioException twilioException) {
                Log.i(TAG+"", String.format("onVideoTrackSubscriptionFailed: " +
                                "[RemoteParticipant: identity=%s], " +
                                "[RemoteVideoTrackPublication: sid=%b, name=%s]" +
                                "[TwilioException: code=%d, message=%s]",
                        remoteParticipant.getIdentity(),
                        remoteVideoTrackPublication.getTrackSid(),
                        remoteVideoTrackPublication.getTrackName(),
                        twilioException.getCode(),
                        twilioException.getMessage()));

            }

            @Override
            public void onAudioTrackEnabled(RemoteParticipant remoteParticipant,
                                            RemoteAudioTrackPublication remoteAudioTrackPublication) {

            }

            @Override
            public void onAudioTrackDisabled(RemoteParticipant remoteParticipant,
                                             RemoteAudioTrackPublication remoteAudioTrackPublication) {

            }

            @Override
            public void onVideoTrackEnabled(RemoteParticipant remoteParticipant,
                                            RemoteVideoTrackPublication remoteVideoTrackPublication) {

            }

            @Override
            public void onVideoTrackDisabled(RemoteParticipant remoteParticipant,
                                             RemoteVideoTrackPublication remoteVideoTrackPublication) {

            }
        };
    }


    //update when user start broadcasting not yet in pk mode
    protected void upadateOnJoinRoomIsOwner1(long state) {
        if (vm.isOwner && !vm.isPkMode) {
            LiveFireFun.instance().update_live(vm.broad_room_name,
                    vm.broad_room_id,
                    vm.broad_usr_fuid,
                    vm.broad_usr_wuid,
                    vm.broad_usr_name,
                    vm.broad_usr_avtar,
                    vm.broad_usr_avtar_type,
                    vm.broadcast_id,
                    state

            );
        }
    }

//    protected abstract void updateFire1(
//            String broad_room_name, String broad_room_id,
//            String broad_usr_fuid, String broad_usr_wuid,
//            String broad_usr_name, String broad_usr_avtar,
//            long broad_usr_avtar_type,
//            long broadcast_id);


    private void addRemoteParticipantVideo(RemoteParticipant remoteParticipant) {
        String remoteParticipantIdentity = remoteParticipant.getIdentity();

        loge(TAG, "onConnected","remoteParticipantIdentity", remoteParticipantIdentity);
        loge(TAG, "isowner "+vm.isOwner, "ispk "+vm.isPkMode, "isSender "+vm.isPkSender);

        if (remoteParticipant.getRemoteVideoTracks().size() > 0) {
            RemoteVideoTrackPublication remoteVideoTrackPublication = remoteParticipant.getRemoteVideoTracks().get(0);

            /*
             * Only render video tracks that are subscribed to
             */
            if (remoteVideoTrackPublication.isTrackSubscribed()) {
                addRemoteParticipantVideo(remoteVideoTrackPublication.getRemoteVideoTrack(), remoteParticipantIdentity);
            }
        }

        remoteParticipant.setListener(remoteParticipantListener());
    }

    private void addRemoteParticipantVideo(VideoTrack videoTrack, String identity) {
        collectRemoteParticipantVideo(identity, videoTrack);
        //remoteVideoPublication(videoTrack, Constants.userUid(identity, vm.broad_usr_uid).second);
        loge(TAG, "addRemoteParticipantVideo", identity, "ispk:"+vm.isPkMode, "isowner:"+vm.isOwner);
        ///accepter owner view change to PK
        //sec. owner
        if(vm.isPkMode && vm.isOwner && !vm.isPkSender && !isrightaquired){
            //is primary owner joined broad rcved /// prim. owner
            Pair<Boolean, String> userUid = Constants.userUid(identity, vm.pk_usr_uid);
            if(userUid.first){
                if(pkLayout.getVisibility()==View.GONE) pkLayout.setVisibility(View.VISIBLE);

                if (pkLayout.getRightVideoLayout().getVisibility() == View.GONE) {
                    pkLayout.getRightVideoLayout().setVisibility(View.VISIBLE);
                }

                pkLayout.getRightVideoLayout().setMirror(false);
                videoTrack.addRenderer(pkLayout.getRightVideoLayout());
                pkVideoTrack1 = videoTrack;
                remoteVideoView = pkLayout.getLeftVideoLayout();
                isrightaquired=true;
            }
        }
        ///sec. owner joined braod rcvd /// sec owner joined as user
        //prim owner
        if(vm.isPkMode && vm.isOwner && vm.isPkSender && !isrightaquired){
            Pair<Boolean, String> userUid = Constants.userUid(identity, vm.pk_usr_uid);
            if(userUid.first){
                if(pkLayout.getVisibility()==View.GONE) pkLayout.setVisibility(View.VISIBLE);

                if (pkLayout.getRightVideoLayout().getVisibility() == View.GONE) {
                    pkLayout.getRightVideoLayout().setVisibility(View.VISIBLE);
                }

                pkLayout.getRightVideoLayout().setMirror(false);
                videoTrack.addRenderer(pkLayout.getRightVideoLayout());
                pkVideoTrack1 = videoTrack;
                remoteVideoView = pkLayout.getLeftVideoLayout();
                isrightaquired=true;
            }
        }

        //users && !pk
        if(!vm.isOwner){
            if(vm.broad_usr_fuid!=null && identity.equals(vm.broad_usr_fuid+vm.broad_usr_fuid)){
                //aquire left
                if(vm.isPkMode){
                    if(!isleftaquired) {
                        if (pkLayout.getVisibility() == View.GONE) pkLayout.setVisibility(View.VISIBLE);

                        if (pkLayout.getLeftVideoLayout().getVisibility() == View.GONE) {
                            pkLayout.getLeftVideoLayout().setVisibility(View.VISIBLE);
                        }

                        pkLayout.getLeftVideoLayout().setMirror(false);
                        videoTrack.addRenderer(pkLayout.getLeftVideoLayout());
                        localVideoTrack1 = videoTrack;
                        localVideoView = pkLayout.getLeftVideoLayout();
                        isleftaquired = true;
                        loge(TAG, " isleftaquired");
                    }
                }else {
                    //broad mode for user
                    if(local_or_broadcast_video_view.getVisibility()==View.GONE)
                        local_or_broadcast_video_view.setVisibility(View.VISIBLE);

                    local_or_broadcast_video_view.setMirror(false);
                    videoTrack.addRenderer(local_or_broadcast_video_view);
                    localVideoTrack1 = videoTrack;
                    localVideoView = local_or_broadcast_video_view;
                    background_avtar(UiUtils.GONE);
                }
            }

            if(vm.pk_usr_uid!=null && identity.equals(vm.pk_usr_uid+vm.pk_usr_uid)){
                //aquire right
                if(vm.isPkMode && !isrightaquired){
                    if(pkLayout.getVisibility()==View.GONE) pkLayout.setVisibility(View.VISIBLE);

                    if (pkLayout.getRightVideoLayout().getVisibility() == View.GONE) {
                        pkLayout.getRightVideoLayout().setVisibility(View.VISIBLE);
                    }

                    pkLayout.getRightVideoLayout().setMirror(false);
                    videoTrack.addRenderer(pkLayout.getRightVideoLayout());
                    pkVideoTrack1 = videoTrack;
                    remoteVideoView = pkLayout.getRightVideoLayout();
                    isrightaquired=true;
                }
            }
        } /**/

    }

    private void removeRemoteParticipant(String room, RemoteParticipant remoteParticipant) {

        String remoteParticipantIdentity1 = remoteParticipant.getIdentity();

        if (!remoteParticipant.getRemoteVideoTracks().isEmpty()) {
            RemoteVideoTrackPublication remoteVideoTrackPublication =
                    remoteParticipant.getRemoteVideoTracks().get(0);


            if (remoteVideoTrackPublication.isTrackSubscribed()) {
                removeParticipantVideo(remoteVideoTrackPublication.getRemoteVideoTrack(), room, remoteParticipantIdentity1);
            }
        }
    }

    private void removeParticipantVideo(VideoTrack videoTrack, String room, String identity) {
        loge(TAG, "room:"+room, "identity:"+identity);

    }

    void updateservertimeandpkid(int i/*should update time or not*/){
        is_time_pk_id_send = true;
        Constants.sendToService(getContext(), new ControllModal
                (UPDATESERVERTIMEANDPKID, new String[]{vm.broad_usr_fuid, vm.pk_usr_uid_web},
                        new long[]{vm.broadcast_id, vm.pkduration}, i));
    }

    protected void moveLocalVideoToPrimaryView() {
        if(local_or_broadcast_video_view.getVisibility()==View.GONE){
            local_or_broadcast_video_view.setVisibility(View.VISIBLE);

        }
        if (has_pk_layout_mode) {

            if (localVideoTrack != null) {
                localVideoTrack.removeRenderer(pkLayout.getLeftVideoLayout());
                localVideoTrack.addRenderer(local_or_broadcast_video_view);
            }

            if (localVideoTrack1 != null) {
                localVideoTrack1.removeRenderer(pkLayout.getLeftVideoLayout());
                localVideoTrack1.addRenderer(local_or_broadcast_video_view);
            }

            if(pkVideoTrack1!=null) pkVideoTrack1.removeRenderer(pkLayout.getRightVideoLayout());
            pkVideoTrack1 = null;

            pkLayout.getLeftVideoLayout().setVisibility(View.GONE);
            pkLayout.getRightVideoLayout().setVisibility(View.GONE);
            //pkLayout.setVisibility(View.GONE);
            change_pk_to_broad();

            localVideoView = local_or_broadcast_video_view;

            if(vm.isOwner) {
                local_or_broadcast_video_view.setMirror(cameraCapturerCompat.getCameraSource() ==
                        CameraCapturer.CameraSource.FRONT_CAMERA);
            }else {
                local_or_broadcast_video_view.setMirror(false);
            }

            isrightaquired = false;
            isleftaquired = false;

            has_pk_layout_mode = false;
        }
    }

    //1=> move local video to thumb left for sec owner
    //2=> move local video to thumb left for prim owner
    protected void moveLocalVideoToThumbnailView() {
        loge(TAG, "moveLocalVideoToThumbnailView");
        if (!has_pk_layout_mode) {
            has_pk_layout_mode = true;

            change_broad_to_pk();

            if(vm.isOwner) {
                if(localVideoTrack!=null){
                    localVideoTrack.removeRenderer(local_or_broadcast_video_view);
                    pkLayout.getLeftVideoLayout().setMirror(cameraCapturerCompat.getCameraSource() ==
                            CameraCapturer.CameraSource.FRONT_CAMERA);
                    localVideoTrack.addRenderer(pkLayout.getLeftVideoLayout());
                    pkLayout.getLeftVideoLayout().setVisibility(View.VISIBLE);

                }
            }else{
                if(localVideoTrack1!=null) {
                    localVideoTrack1.removeRenderer(local_or_broadcast_video_view);
                    pkLayout.getLeftVideoLayout().setMirror(false);
                    localVideoTrack1.addRenderer(pkLayout.getLeftVideoLayout());
                    isleftaquired=true;
                }
            }

            local_or_broadcast_video_view.setVisibility(View.GONE);
            localVideoView = pkLayout.getLeftVideoLayout();
            //disconnecting_from_room_to_join_prim_owner = false;

        }
    }

    protected void disconnectFromRoom(){
        if (room != null && room.getState() != Room.State.DISCONNECTED) {
            loge(TAG, "disconnectFromRoom", ""/*+room.getLocalParticipant().getIdentity()*/);
            room.disconnect();
            room = null;
        }
    }

    //on user disconnected
    //on participant disconnected
    private void onDisconnectRoom(Room room, RemoteParticipant remote){

        if(room!=null && room.getLocalParticipant()!=null){
            localConnectUserAction(false, room.getLocalParticipant());

            loge(TAG, "dc", ""+room.getLocalParticipant().getIdentity());
            //sec owner switch to own room on pk end result
            if(room.getLocalParticipant().getIdentity().equals(vm.broad_usr_fuid+vm.broad_usr_fuid)
                    && disconnecting_from_room_to_join_prim_owner){

                joinBroad(2);

            }

            if(room.getLocalParticipant().getIdentity().equals(vm.local_fuid+vm.broad_usr_fuid)
                    && disconnecting_from_room_to_join_own_room){

                vm.is_pk_sender = false;
                vm.should_change_to_broad = false;
                disconnecting_from_room_to_join_own_room = false;
                joinBroad(1); //join broad after dc from PK room
            }

        }

        if(remote!=null) {
            removeRemoteParticipant(room.getName(), remote);
            remoteParticipant(false, remote.getIdentity());
        }
    }

    //////Local//user//setting//up///////////////////////////////////////////////////////////////
    //local user as participant ... connect or DC
    //local user as owner ... connect or DC
    void localConnectUserAction(boolean conn, @NonNull LocalParticipant participant){
        //if(conn && vm.isOwner){ background_avtar(UiUtils.GONE); }

        if(participant.getIdentity().equals(vm.local_fuid +vm.broad_usr_fuid)
                        || participant.getIdentity().equals(vm.local_fuid +vm.pk_usr_uid)
        ){

        }
    }

    void remoteParticipantConnectListener(RemoteParticipant participant){
        addRemoteParticipantVideo(participant);
        remoteParticipant(true, participant.getIdentity());

    }

    void remoteParticipant(boolean isJoined, String identity){
        boolean id = Constants.isRoomUser(identity, vm.broad_usr_fuid, vm.local_fuid);
        if(isJoined){
            if(id){
                if(identity.equals(vm.broad_usr_fuid+vm.broad_usr_fuid)){
                    isOwnerOnline = true;
                    removeOffline();
                }
            }
        } else {
            if(id){
                if(identity.equals(vm.broad_usr_fuid+vm.broad_usr_fuid)){
                    isOwnerOnline = false;
                    onOffline();
                }
            }
        }
    }



    /* --- ---- room enter animation playing ---- ---- */

    void joinFirebaseGroup(){
        userJoinedState = UserJoinedState.instance(getContext())
                .joinRoom(new UserJoinedInfo(
                        vm.local_fuid,
                        vm.broad_join_identity,
                        vm.broad_room_id,
                        vm.broad_usr_fuid,
                        vm.broadcast_id+""
                ).getinfo(credentials))
                .setUserJoinedListener((state, user) -> {
                    loge(TAG, ""+state);
                    participants.reset(userJoinedState.getUserJoinedInfoList().size());

                    switch (state){
                        case JOINED:
                            owner_xp_level_showup(user);
                            joinerInfo(user);
                            break;
                        case LEAVED:
                            break;
                        case CHANGED:
                            owner_xp_level_showup(user);
                            break;
                    }
                });
    }

    void owner_xp_level_showup(UserJoinedInfo info){
        try {
            if(info.user_fuid.equals(vm.broad_usr_fuid)){
                //update level and xp
                vm.owner_level = info.user_level!=null && !info.user_fuid.isEmpty()?Integer.parseInt(info.user_level):0;
                vm.owner_xp = info.user_xp;

                levelcollect1.setText("" + vm.owner_level);
                coincollect1.setText(UiUtils.coolNumberFormat(vm.owner_xp));

            }

            if(info.user_fuid.equals(vm.local_fuid)){
                vm.local_level = info.user_level!=null && !info.user_fuid.isEmpty()?Integer.parseInt(info.user_level):0;
                vm.local_xp = info.user_xp;
            }
        }catch (Exception e){
            e.printStackTrace();
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }

    void joinerInfo(UserJoinedInfo info){

        if(info.user_fuid.equals(vm.local_fuid)
                || info.user_fuid.equals(vm.broad_usr_fuid)) {

        }else {
            if (info.user_animation!=null && !info.user_animation.isEmpty()) {
                info.animation_from = AnimationPlayer.ASSETS;
                info.user_animation = "posche.svga";

                sendEnterInfoToQueue(info);
            } else {
                playingInfoAnimation(info);
            }
        }
    }

    /*
    * send user enter info to queue to play enter animation
    *   if level is > 5
    *
    *   else show user enter info animation only
    *
    *
    * */
    void sendEnterInfoToQueue(UserJoinedInfo info){
       // Logger.loge("AnimPlayer", new Gson().toJson(info));
        animationPlayer.enterAnimScreenGift(info);
    }

    /*playing started*/
    /*
    * trigger when user enter animation just ready to play
    * have to show user info animation
    *
    * */
    AnimationPlayer.EnterAnimationPlayerListener enterAnimationPlayerListener = this::playingInfoAnimation;

    void playingInfoAnimation(UserJoinedInfo info){
        if(info.isAnimationPlayed) return;
        liveRoomMessageView.joinMessage(LiveRoomMessageView.MSG_TYPE_SYSTEM, info.user_name, "Joined", 0);
        AnimUtils.room_enter_animation(getContext(),
                TranslateView.getScreenWidth(requireActivity()),
                layout_room_enter_animation,
                info.user_name, info.user_level, () -> {
                    info.isAnimationPlayed = true;
                });
    }

    void leaveRoomFinishListener(){
        if(userJoinedState!=null){
            userJoinedState.leaveRoom();
        }
    }

    /****end playing room enter animation*/

    private void readCalls(List<EntityCall> entityCalls) { readIncomingCallRequestAndStatus(entityCalls); }

    ////////////////////////////////////////////////////////////////////////////////////
    /////////////////////room join proces///////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////
    public void readingLiveData(LiveEntity2 en1){
         if(en1!=null) {
             //loge(TAG, "readingLiveData " + en1.state);
             if (en1.is_online == 0) {
                 isOwnerOnline = false;
                 onOffline(); //live data
             }

             // loge(TAG, "readingLiveData", toGson(en));
             if(en1.pk_room!=null){
                en1.PkInfo = LiveRepo2.pk_exist(getContext(), en1.pk_room);
                 //loge(TAG, "PK state en1.pkInfo", new Gson().toJson(en1.PkInfo));

             }

             if(en1.PkInfo!=null) vm.pk_usr_uid = en1.PkInfo.pk_user_fuid;

             //save data locally
             vm.state = en1.state;
             vm.isPkMode = en1.state==STAT_PK_MODE;
             vm.isPkSender = en1.pksender;

             if(vm.state==STAT_CALL_MODE){
                 layout_call.setVisibility(View.VISIBLE);
                 mStartPkButton.setVisibility(View.GONE);
                 txt_closeCalls.setText(en1.callcount>1 ? getString(R.string.close_all_calls) : getString(R.string.close_call));
                 AddCallsStat_setup(en1.callcount, en1.calltype);
             }else {
                 removeAllCalls();
                 layout_call.setVisibility(View.GONE);
             }

             LiveEntity2 en = en1;

             if(vm.isOwner) {
                 /*invite state for both owner2 both users*/
                 if (vm.state == STAT_PK_INVITE) {

                     mStartPkButton.setVisibility(View.GONE);
                     mStartCallingButton.setVisibility(View.GONE);

                     //loge(TAG, "PK state", new Gson().toJson(en));
                     if (!vm.isPkSender && en1.PkInfo != null && !pk_invite_dialog_used) {
                         onPkInvitedialog(en1); //INVITE PK
                     }
                 }

                 if (en.state == STAT_REJECT_PK) {
                     removeViewFromTop();
                     if (en.PkInfo != null) {
                         showShortToast(en.PkInfo.pk_user_name + " have rejected your PK invitation request");
                     }

                     //FireFun.onRejectedBySecOwner(vm.broad_usr_fuid,  STAT_BROADCAST);
                     LiveFireFun.instance().onPkRejectedBySecOwner(vm.broad_usr_fuid, STAT_BROADCAST);
                     vm.isPkMode = false;
                     vm.isPkSender = false; //pk reeject

                 }

                 if (en.state == STAT_RND_PK) { }

             }

             if (en.state == STAT_PK_RESULT_MODE && !isEndingPk) {
                 //ending pk / showing result
                 showPkEndingResult(en.pkid == null, en.pkPoint_local, en.pkPoint_remote);
             }

             if(en.state==STAT_PK_MODE){
                 isEndingPk = false;
                 vm.isPkMode = true;
                 vm.pk_room_id =  en.PkInfo==null?"":en.PkInfo.pk_room_id;
                 vm.userPkMode = true;
                 vm.userisPkSender = en.pksender;
                 pkLayout.removeResult();
                 moveLocalVideoToThumbnailView(); //on start

                 if(vm.isOwner){
                     if(!is_time_pk_id_send){
                         updateservertimeandpkid(0);
                     }

                 }

                 if(!en.pksender && !disconnecting_from_room_to_join_prim_owner){
                         disconnecting_from_room_to_join_prim_owner = true;
                         disconnectFromRoom(); //disconnect by sec. owner to join pk

                 }

                 removeViewFromTop();

                 if(en.pkstarttime>0 && en.PkInfo!=null) {
                     RetroCalls.instance().callType(GET_SERVER_TIME)
                             .get_server_time((a, b) -> {
                                 if (b != null && b.getStatus() == 1 && b.getResult() != null && b.getResult().server_time > 0) {

                                     long currTime = b.getResult().server_time;
                                     if (currTime > en.pkstarttime + en.PkInfo.pk_dusration * 1000) {
                                         pkLayout.startCountDownTimer(0, vm.isOwner);
                                     } else {
                                         long remainintTime = en.pkstarttime + en.PkInfo.pk_dusration * 1000 - currTime;
                                         pkLayout.startCountDownTimer(remainintTime, vm.isOwner);
                                     }
                                 }
                             });
                 }

                 //long remainintTime = en.pkstarttime + en.PkInfo.pk_dusration * 1000 - currTime;
                 //pkLayout.startCountDownTimer(remainintTime, vm.isOwner);
                 pkLayout.setPoints(en.pkPoint_local, en.pkPoint_remote);

                 if(en.PkInfo!=null){

                     pkLayout.setPKHostName(en.PkInfo.pk_user_name, en.PkInfo.pk_user_fuid,
                             en.PkInfo.pk_user_wuid,
                             en.is_following==1);
                     pkLayout.setPKHostAvtar(en.PkInfo.pk_user_avtar, en.PkInfo.pk_user_avtar_type);

                     try {
                         vm.broad_pkid = Long.parseLong(en.pkid);
                     } catch (Exception e){
                         e.printStackTrace();
                     }

                 }

             } else {
                if(!isEndingPk){

                 }
             }


             //resume broad setting
             if(vm.state==STAT_BROADCAST){
                 is_time_pk_id_send = false;
                 pk_invite_dialog_used = false;
                 dismiss_pk_invite_info_dialog(); //reset dialog
                 mStartPkButton.setVisibility(vm.isOwner ? View.VISIBLE : View.GONE);
                 mStartCallingButton.setVisibility(vm.isOwner ? View.VISIBLE : View.GONE);

                 vm.broad_pkid = 0;
                 vm.pk_usr_uid = "";
                 disconnectexception="";

                 moveLocalVideoToPrimaryView(); //uncondinal move to broad mode

                 vm.is_pk_sender = false;

                 if(vm.should_change_to_broad && !disconnecting_from_room_to_join_own_room){
                     disconnecting_from_room_to_join_own_room = true;
                     disconnectFromRoom();
                 }

                 disconnecting_from_room_to_join_prim_owner = false;
                 //disconnecting_from_room_to_join_own_room = false;

             }

         }else loge(TAG, "readingLiveData null");
    }

    String winer_wuid;
    protected void showPkEndingResult(boolean b, String local_point, String remote_point){
        isEndingPk = true;
        ///playTopInfoAnim("stop-watch-yellow.json"); //pk ending result mode

        loge(TAG, "pkending... ", ""+isEndingPk, ""+b);
        //removeViewFromTop();
        //if(b){
            if(local_point.equals(remote_point)){
                pkLayout.setResult(2); //draw
                winer_wuid = "0";
            }
            else if(Integer.parseInt(local_point) > Integer.parseInt(remote_point)){
                pkLayout.setResult(1);
                winer_wuid = vm.broad_usr_wuid;
            }else {
                pkLayout.setResult(0);
                winer_wuid = vm.pk_usr_uid_web;
            }
        //}else {
        //    pkLayout.setResult(3);
        //}
        afterREsult();

    }

    void afterREsult(){
        if(vm.isOwner) endPk();
        new Handler(Looper.myLooper()).postDelayed(this::switchPkToNormalMode, 3000);
    }

    void endPk(){
        RetroCalls.instance().callType(ENDPK)
                .stringParam(String.valueOf(vm.broad_pkid), winer_wuid)
                .pk_end((a,b)->{ });

    }

    protected void switchPkToNormalMode(){

        pkLayout.removeResult();

        vm.is_pk_sender = vm.isPkSender; //at end of pk
        vm.isPkMode = false;
        vm.broad_pkid = 0;
        //if(!vm.is_pk_sender)

        if(vm.is_pk_sender && isEndingPk)
            vm.should_change_to_broad = true;

        isEndingPk = false;

        LiveAppDb.databaseWriteExecutor.execute(()->{
            LiveAppDb.getDatabase(getContext()).userDao().pk_delete2(vm.pk_room_id);
        });

        upadateOnJoinRoomIsOwner1(STAT_BROADCAST); //switching pk

    }

    ////////////////camera/////////////////////
    protected CameraCapturer.CameraSource getAvailableCameraSource() {
        return (CameraCapturer.isSourceAvailable(CameraCapturer.CameraSource.FRONT_CAMERA)) ?
                (CameraCapturer.CameraSource.FRONT_CAMERA) :
                (CameraCapturer.CameraSource.BACK_CAMERA);
    }

    protected boolean isOwnerOnline;
    protected boolean errorShowing;
    private void onOffline(){

        View offlineview = LayoutInflater.from(getContext()).inflate(R.layout.layout_owner_offline, null, false);

        if (topFrame.getChildCount()>0) topFrame.removeAllViews();
        topFrame.addView(offlineview);

        ImageView img_full_image = offlineview.findViewById(R.id.img_full_image);
        CircleImageView circleImageView = offlineview.findViewById(R.id.ownerAvtar);
        TextView ownerName = offlineview.findViewById(R.id.ownerName);
        TextView message_text_view = offlineview.findViewById(R.id.message_text_view);
        TextView message_subscreibe = offlineview.findViewById(R.id.message_subscreibe);
        Button subscribe_channel = offlineview.findViewById(R.id.subscribe_channel);

        UiUtils.setAvtarByLink(vm.broad_usr_avtar, vm.broad_usr_avtar_type, img_full_image, R.drawable.avatar);
        UiUtils.setAvtarByLink(vm.broad_usr_avtar, vm.broad_usr_avtar_type, circleImageView);

        ownerName.setText(vm.broad_usr_name);
        message_text_view.setText("Broadcast has ended.");
        message_subscreibe.setVisibility(View.GONE);
        subscribe_channel.setVisibility(View.GONE);

    }

    void removeOffline(){ topFrame.removeAllViews(); }

    void onErrorMessage(String message){
        /*errorShowing = true;

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_error_connection, null, false);

        if (topFrame.getChildCount()>0) topFrame.removeAllViews();
        topFrame.addView(view);

        TextView textView = view.findViewById(R.id.tv_error_message);
        ConstraintLayout layout = view.findViewById(R.id.parentError);
        View layout_close = view.findViewById(R.id.closeLayout);

        textView.setText(message);
        layout.setBackgroundColor(getResources().getColor(R.color.error_background_color));
        layout_close.setOnClickListener(v->{
            act.finish();
        });*/

    }



    void init_constraint_set(){
        set_base = new ConstraintSet();
        set_base.clone(constraintRoot);

        set_pk = new ConstraintSet();
        set_pk.clone(getContext(), R.layout.activity_indi_live_pk);
    }

    void change_broad_to_pk(){
        TransitionManager.beginDelayedTransition(constraintRoot);
        set_pk.applyTo(constraintRoot);
    }

    void change_pk_to_broad(){
        TransitionManager.beginDelayedTransition(constraintRoot);
        set_base.applyTo(constraintRoot);
    }




}