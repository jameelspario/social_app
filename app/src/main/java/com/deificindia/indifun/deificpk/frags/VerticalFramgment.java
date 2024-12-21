package com.deificindia.indifun.deificpk.frags;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.TransitionManager;

import com.deificindia.chat.Model.User;
import com.deificindia.firebaseAdapter.GiftSenderAdapter;
import com.deificindia.firebaseModel.AddLiveModel;
import com.deificindia.firebaseModel.GiftSenderModel;
import com.deificindia.indifun.Adapter.HotAdapter;
import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Logger;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.Utility.UiUtils;
import com.deificindia.indifun.anim.AnimUtils;
import com.deificindia.indifun.anim.TranslateView;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.deificpk.actionsheets.BackgroundMusicActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.LiveRoomUserListActionSheet;
import com.deificindia.indifun.deificpk.animutils.AnimationPlayer;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.deificpk.widgets.LiveRemoteHostNameLayout;
import com.deificindia.indifun.deificpk.widgets.messeging.LiveRoomMessageAdapter;
import com.deificindia.indifun.db.LiveEntity;
import com.deificindia.indifun.db.LiveRepo;
import com.deificindia.indifun.deificpk.actionsheets.AbstractActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.toolactionsheet.LiveRoomToolActionSheet;
import com.deificindia.indifun.deificpk.acts.IndiActivity;
import com.deificindia.indifun.deificpk.widgets.LiveBottomButtonLayout;
import com.deificindia.indifun.deificpk.widgets.LiveHostNameLayout;
import com.deificindia.indifun.deificpk.widgets.LiveMessageEditLayout;
import com.deificindia.indifun.dialogs.DialogUtils;
import com.deificindia.indifun.events.IndiActEvent;
import com.deificindia.indifun.fires.LiveFireFun;
import com.deificindia.indifun.fires.m.UserJoinedInfo;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.LoadingAnimView;
import com.deificindia.indifun.vm.BroadVm;
import com.deificindia.indifun.vm.LiveVm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.twilio.audioswitch.AudioSwitch;
import com.twilio.video.Room;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.Utility.MySharePref.DAILY_HEART;
import static com.deificindia.indifun.Utility.MySharePref.saveIntData;
import static com.deificindia.indifun.Utility.URLs.MUSICLIST;
import static com.deificindia.indifun.anim.TranslateView.getScreenHeight;
import static com.deificindia.indifun.anim.TranslateView.getScreenWidth;
import static com.deificindia.indifun.deificpk.utils.Const.KEY_BUNDLE_IS_OWNER;
import static com.deificindia.indifun.deificpk.utils.Const.loge;
import static com.deificindia.indifun.dialogs.DialogUtils.showPkTimeDialog;
import static com.deificindia.indifun.rest.RetroCallListener.HEART_POINT;

public class VerticalFramgment extends LiveBaseFragment
        implements VisibilityAware, View.OnClickListener,
        TextView.OnEditorActionListener,
        AbstractActionSheet.AbsActionSheetListener,
        BackgroundMusicActionSheet.BackgroundMusicActionSheetListener {

    private static final String TAG = "VerticalFramgment";
    RelativeLayout level_layout,coin_collect;
    String firebaseId;
    String wr;

    boolean isowner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClip = (LiveEntity2) requireArguments().getSerializable(ARG_CLIP);
        isowner =  requireArguments().getBoolean(KEY_BUNDLE_IS_OWNER);
        Timber.e("onCreate:%s", toGson(mClip));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_indi_live, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* FrameLayout frame = view.findViewById(R.id.vertical_parent);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        frame.setBackgroundColor(color);*/

        loge(TAG, "onViewCreated", ""+isowner, new Gson().toJson(mClip));

        if (mClip.owneruid!=null) {
            if(getActivity()!=null)
                getActivity().getViewModelStore().clear();

            initIntent();
            initUI(view);
            initLive();

            isFrameOpen = true;
        }else {
            isFrameOpen = true;
        }
    }

    public static VerticalFramgment newInstance(LiveEntity2 clip, boolean isowner1) {
        VerticalFramgment fragment = new VerticalFramgment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_CLIP, clip);
        arguments.putBoolean(KEY_BUNDLE_IS_OWNER, isowner1);
        fragment.setArguments(arguments);
        return fragment;
    }


    protected void initIntent() {

        act = (IndiActivity) getActivity();

        act.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this::detectKeyboardLayout);
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        vm = new ViewModelProvider(this).get(BroadVm.class);

        livevm = new ViewModelProvider(this).get(LiveVm.class);
        livevm.owner_fuid = mClip.owneruid;

        credentials = IndifunApplication.getInstance().getCredential();
        vm.local_fuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        vm.local_wuid = credentials.getId();

        vm.isOwner = this.isowner;

        vm.broad_room_id = mClip.roomid;
        vm.broad_room_name = mClip.roomname;
        vm.broadcast_id = mClip.broadid;
        vm.broad_usr_avtar = mClip.owneravtar;
        vm.broad_usr_avtar_type= mClip.owneravtartype;

        vm.is_following = mClip.is_following;

        vm.broad_usr_fuid = mClip.owneruid;
        vm.broad_usr_wuid = mClip.owneruidweb;
        vm.broad_usr_name = mClip.ownername;

        if (vm.isOwner) {

            loge("self", String.valueOf(vm.local_fuid));
            vm.broad_usr_fuid = vm.local_fuid;
            vm.broad_usr_wuid = credentials.getId();
            vm.broad_usr_name = credentials.getFullName();

            if (vm.broad_usr_avtar == null || vm.broad_usr_avtar.isEmpty()) {
                vm.broad_usr_avtar_type = 0;
                vm.broad_usr_avtar = credentials.getProfilePicture();
            }
        }

        wr = vm.broad_usr_fuid;
        loge("fui", String.valueOf(vm.broad_usr_fuid));
        firebaseId = vm.broad_usr_fuid;

        vm.broad_join_identity = Constants.roomIdentity(isowner, vm.broad_usr_fuid);
        //audio maNAGER
        initAudioManager();

        vm.liveChatListener().observe(getActivity(), modal -> {
            if (modal.type == TYPE_CHAT && is30NotSecPast(modal.time)) {
                loge(TAG, modal.senderuid, modal.room, modal.message);
                liveRoomMessageView.addMessage(TYPE_CHAT,
                        modal.sendername, modal.message, modal.senderuid, modal.senderuidweb, modal.senderxp);
            }

            if (modal.type == TYPE_FULLSCREENGIFT) {

                if (is30NotSecPast(modal.time) && !modal.senderuid.equals(vm.local_fuid)) {
                    sendFullScreenGift(null, modal, null);
                    xpUpdateOnGift();
                }
            }

            if (modal.type == TYPE_HEART_GIFT && is30NotSecPast(modal.time)) {

              /*  vm.user_heart++;
                wr = String.valueOf(vm.user_heart);
                loge("ssss", wr);
                setHeart();*/
                if (!modal.senderuid.equals(vm.local_fuid)) playHeartAnimation(1, modal);
                xpUpdateOnHeartReceived();
            }

            if (modal.type == TYPE_SYSTEM && is30NotSecPast(modal.time)) {
                if (modal.tofuid.equals(vm.local_fuid)) {
                    switch ((int) modal.system) {
                        case 1: /*blocked"*/
                            muteUser();
                            LiveRepo.updateIsBlocked(getContext(), modal.senderuidweb, 1);
                            DialogUtils.infoDialog(getContext(), "Warning",
                                    "You have been blocked from this broad.", dialog -> {
                                        dialog.dismiss();
                                        act.finish();
                                    });
                            break;
                        case 2: /*muted*/
                            muteUser();
                            LiveRepo.updateIsMuted(getContext(), modal.senderuidweb, 1);
                            DialogUtils.infoDialog(getContext(), "Warning",
                                    "You have been muted on this broad for next 24 hours.", Dialog::dismiss);
                            break;
                        case 3: /*kicked */
                            muteUser();
                            LiveRepo.updateIsKicked(getContext(), modal.senderuidweb, 1);
                            DialogUtils.infoDialog(getContext(), "Warning",
                                    "You have been kicked out of this broad and you cant join this broad for next 48 hours.", dialog -> {
                                        dialog.dismiss();
                                        act.finish();
                                    });

                            break;
                        //default: msg = "";
                    }
                }
                liveRoomMessageView.addMessage(TYPE_SYSTEM,
                        modal.sendername, modal.message, modal.senderuid, modal.senderuidweb, modal.system);
            }

        });
        vm.liveListenChat(vm.broad_usr_fuid, vm.broad_room_id);
        //refreshUserList();
        hide(true);

    }

    DatabaseReference reference_hearts;
    ValueEventListener valueEventListener_hearts;
    private void heart_count_listener() {

        reference_hearts = FirebaseDatabase.getInstance().getReference();
        valueEventListener_hearts = reference_hearts.child("BroadLike").child(firebaseId).
              addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
             // String a =  vm.broad_usr_fuid ;
              User u=snapshot.getValue(User.class);
              err= String.valueOf(snapshot.getChildrenCount());
              heartCollect1.setText(err);
          }
          @Override
          public void onCancelled(@NonNull @NotNull DatabaseError error) {
          }
      });
    }

    private void removeHeartListener(){
        if(reference_hearts!=null && valueEventListener_hearts!=null){
            reference_hearts.removeEventListener(valueEventListener_hearts);
        }
    }

    void initUI(View v){

        constraintRoot = v.findViewById(R.id.rootLayout);
        init_constraint_set();

        default_message_layut = v.findViewById(R.id.default_message_layut) ;
        guide8 = v.findViewById(R.id.guideline8) ;

        LinearLayout heart1 = v.findViewById(R.id.play_heart) ;
        RelativeLayout msg1 = v.findViewById(R.id.play_mesg) ;
        RelativeLayout msg2 = v.findViewById(R.id.play_mesg2) ;
        RelativeLayout msg3 = v.findViewById(R.id.play_mesg3) ;
        TextView msg24 = v.findViewById(R.id.msg) ;
        TextView msg34= v.findViewById(R.id.msg2) ;
        TextView msg4 = v.findViewById(R.id.msg3) ;
        msg24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendChatMessage(msg24.getText
                                ().toString(),
                        vm.broad_room_id,
                        vm.broad_usr_fuid,
                        credentials,
                        vm.local_level);
                msg24.setVisibility(View.GONE);
                msg34.setVisibility(View.GONE);
                msg4.setVisibility(View.GONE);
            }
        });
        msg34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendChatMessage(msg34.getText
                                ().toString(),
                        vm.broad_room_id,
                        vm.broad_usr_fuid,
                        credentials,
                        vm.local_level);
                msg24.setVisibility(View.GONE);
                msg34.setVisibility(View.GONE);
                msg4.setVisibility(View.GONE);
            }
        });
        msg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendChatMessage(msg4.getText
                                ().toString(),
                        vm.broad_room_id,
                        vm.broad_usr_fuid,
                        credentials,
                        vm.local_level);
                msg24.setVisibility(View.GONE);
                msg34.setVisibility(View.GONE);
                msg4.setVisibility(View.GONE);
            }
        });
        heart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playHeartAnimation_withoutpoint();


            }
        });

        txt_closeCalls = v.findViewById(R.id.txt_closeCalls);
        layout_call = v.findViewById(R.id.layout_calling_users1);
        layCloseCall = v.findViewById(R.id.layCloseCall);
        layCloseCall.setOnClickListener(this);
        background_profile_image = v.findViewById(R.id.background_profile_image);
        local_or_broadcast_video_view = v.findViewById(R.id.stream_primary_cam);

        liveBottomButtonLayout = v.findViewById(R.id.pk_host_in_bottom_layout);
        liveBottomButtonLayout.init();
        liveBottomButtonLayout.setLiveBottomButtonListener(liveBottomButtonListener);
        liveBottomButtonLayout.setRole(vm.isOwner);

        v.findViewById(R.id.live_bottom_btn_close).setOnClickListener(this);
        v.findViewById(R.id.live_bottom_btn_fun4).setOnClickListener(this);
        live_daily_heart_count = v.findViewById(R.id.live_bottom_btn_fun4_label);


        v.findViewById(R.id.live_bottom_btn_fun3).setOnClickListener(this);
        v.findViewById(R.id.live_bottom_btn_fun2).setOnClickListener(this);
        v.findViewById(R.id.live_bottom_btn_fun1).setOnClickListener(this);

        liveRoomMessageView = v.findViewById(R.id.message_list_view);
        liveRoomMessageView.init(getScreenWidth(requireActivity()));
        liveRoomMessageView.setListener(liveMessageAdapter);

        messageEditLayout = v.findViewById(R.id.message_edit_layout);

        messageEditText = messageEditLayout.findViewById(LiveMessageEditLayout.EDIT_TEXT_ID);

        layout_room_enter_animation = v.findViewById(R.id.layout_room_enter_animation);

        coincollect1 = v.findViewById(R.id.txt_user_coin);
        levelcollect1 = v.findViewById(R.id.txt_user_level);
        heartCollect1 = v.findViewById(R.id.txt_user_heart);

        //svgaImageView.setVisibility(View.VISIBLE);
        itemLayout2 = v.findViewById(R.id.itemLayout2);

       call_layoutcallingusers = v.findViewById(R.id.layout_calling_users);

        layout_full_gift_animation = v.findViewById(R.id.layout_gift_animation);
        layout_heart_animation_area = v.findViewById(R.id.layout_heart_animation_area);
        layout_gift_heart_animation = v.findViewById(R.id.layout_gift_heart_animation);

        //mLayout = findViewById(R.id.live_room_pk_room_layout);
        //mVideoNormalLayout = findViewById(R.id.live_pk_video_normal_layout);

        mNamePad = v.findViewById(R.id.pk_host_in_name_pad);
        mNamePad.init();
        mNamePad.setListener(livehostnameclick);

        participants = v.findViewById(R.id.pk_host_in_participant);
        participants.init(vm.broad_room_id);
        participants.setUserLayoutListener(this);

        pkLayout = v.findViewById(R.id.pk_layout);
        pkLayout.remoteHostNameClickListener(liveRemoteHostNameClick);
        pkLayout.setHeight(getScreenHeight(getActivity()));

        leaftSeat = v.findViewById(R.id.left_top_seat);
        rightSeat = v.findViewById(R.id.right_top_seat);

        mStartPkButton = v.findViewById(R.id.start_pk_button);
        mStartCallingButton = v.findViewById(R.id.start_calling_button);
        mStartPkButton.setOnClickListener(this);
        mStartCallingButton.setOnClickListener(this);


        mSpeaker = v.findViewById(R.id.img_speaker);
        mSpeaker.setVisibility(vm.isOwner?View.GONE:View.VISIBLE);
        mSpeakerPk = pkLayout.mSpeaker;
        mSpeaker.setOnClickListener(this);
        mSpeakerPk.setOnClickListener(this);


        //lottieAnimationView = v.findViewById(R.id.liveStreaming_fullscreenGift);
        //svgaImageView = v.findViewById(R.id.svga_mage_seq_image_view);
        topFrame = v.findViewById(R.id.topFrame_activity_indi_live);

        View animation_layout = v.findViewById(R.id.animation_layout);
        animationPlayer = new AnimationPlayer();
        animationPlayer.init(animation_layout);
        animationPlayer.setListener(animationListener);
        animationPlayer.setEnterListener(enterAnimationPlayerListener);

        audioSwitch = new AudioSwitch(getContext());

        setNameAvtar();
        background_avtar(UiUtils.VISIBLE);

        callOrPkButtonVisiblity();

        //daily 10 heart on start
        int dai = get_daily_heart(); //initial
        loge(TAG, "DailyHeart", ""+dai);
        live_daily_heart_count.setText(String.valueOf(dai)); //initial hear count

        heart_count_listener();


    }


    @Override
    protected void changePkLayout(boolean isOwnr, boolean ispk) {
        //setupMessageListLayout(ispk);
    }


    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////
    @SuppressLint("NewApi")
    LiveBottomButtonLayout.LiveBottomButtonListener liveBottomButtonListener = () -> {
        if (messageEditLayout != null) {
            messageEditLayout.setVisibility(View.VISIBLE);
            messageEditText.requestFocus();
            hide(false);
            inputMethodManager.showSoftInput(messageEditText, 0);
        }
    };

    @SuppressLint("NewApi")
    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            Editable editable = messageEditText.getText();
            if (TextUtils.isEmpty(editable)) {
                showShortToast(getResources().getString(R.string.live_send_empty_message));
            } else {
                sendChatMessage(editable.toString(),
                        vm.broad_room_id,
                        vm.broad_usr_fuid,
                        credentials,
                        vm.local_level
                );
                messageEditText.setText("");
            }

            inputMethodManager.hideSoftInputFromWindow(messageEditText.getWindowToken(), 0);
            return true;
        }
        return false;
    }


    private void detectKeyboardLayout() {
        Rect rect = new Rect();
        act.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        if (mDecorViewRect == null) {
            mDecorViewRect = rect;
        }

        int diff = mDecorViewRect.height() - rect.height();

        // The global layout listener may be invoked several
        // times when the activity is launched, we need to care
        // about the value of detected input method height to
        // filter out the cases that are not desirable.
        if (diff == mInputMethodHeight) {
            // The input method is still shown
            return;
        }

        //loge(TAG, "KeyInput", ""+diff, ""+IDEAL_MIN_KEYBOARD_HEIGHT, ""+ mInputMethodHeight);
        if (diff > IDEAL_MIN_KEYBOARD_HEIGHT && mInputMethodHeight == 0) {
            mInputMethodHeight = diff;
            isSoftInputShown=true;
            //setupMessageListLayout(vm.isPkMode);
            onInputMethodToggle(true, diff);
        } else if (mInputMethodHeight > 0) {
            isSoftInputShown=false;
            //setupMessageListLayout(vm.isPkMode);
            onInputMethodToggle(false, mInputMethodHeight);
            mInputMethodHeight = 0;
        }
    }

    protected void onInputMethodToggle(boolean shown, int height) {
        /*RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) messageEditLayout.getLayoutParams();
        int change = shown ? height : -height;
        params.bottomMargin += change;
        messageEditLayout.setLayoutParams(params);

*/
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) messageEditLayout.getLayoutParams();
        int change = shown ? height : -height;
        params.bottomMargin = /*shown?(messageEditLayout.getHeight() - TranslateView.getRelBottom(messageEditText)):*/0;
        messageEditLayout.setLayoutParams(params);

        if (shown) {
            messageEditText.requestFocus();
            messageEditText.setOnEditorActionListener(this);
        } else {
            messageEditLayout.setVisibility(View.GONE);
            hide(true);
        }

        setupMessageListLayout();
        liveRoomMessageView.scroll_to_end();
    }

    private void setupMessageListLayout() {

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) liveRoomMessageView.getLayoutParams();
        ConstraintSet set = new ConstraintSet();
        set.clone(constraintRoot);

        if(!isSoftInputShown){
            params.height = 0;
            set.connect(liveRoomMessageView.getId(), ConstraintSet.BOTTOM, default_message_layut.getId(), ConstraintSet.TOP);
            set.connect(liveRoomMessageView.getId(), ConstraintSet.TOP, guide8.getId(), ConstraintSet.BOTTOM);
        }else{
            set.clear(liveRoomMessageView.getId(), ConstraintSet.TOP);
            params.height = 500;
            set.connect(liveRoomMessageView.getId(), ConstraintSet.BOTTOM, messageEditLayout.getId(), ConstraintSet.TOP);
        }
        //params.addRule(RelativeLayout.ABOVE, R.id.pk_host_in_bottom_layout);
        liveRoomMessageView.setLayoutParams(params);
        TransitionManager.beginDelayedTransition(constraintRoot);
        set.applyTo(constraintRoot);
    }

    /////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onTopGifterUserClick(GiftSenderModel info) {
        /*profilePreviewBottomSheetDialog =
                new ProfilePreviewBottomSheetDialog(info.fuid, info.wuid, vm.broad_room_id, info.name, vm.isOwner, vm.isPkMode, vm.broad_usr_fuid);
        profilePreviewBottomSheetDialog.show(getChildFragmentManager(), "profilePreviewBottomSheetDialog");*/
        showUserShortProfile(info.broadUid, info.fuid, info.full_name);
    }

    @Override
    public void onUserLayoutShowUserList(View view) {
        // Show all user info list
        // Show all user info flist
        Map<String, Object> map1 = new HashMap<>();
        map1.put("room_id", vm.broad_room_id);
        map1.put("owner_uid", vm.broad_usr_fuid);
        map1.put("is_owner", vm.isOwner);
        mRoomUserActionSheet = (LiveRoomUserListActionSheet)
                showActionSheetDialog(ACTION_SHEET_ROOM_USER,
                        1, isowner, true, map1,this);

    }

    @Override
    public void onActionSheetUserListItemSelected(UserJoinedInfo info) {
        // Called when clicking an online user's name, and want to see the detail
        loge(TAG, "onActionSheetUserListItemSelected", ""+info);
       // showUserShortProfile(info.getBroad_id(), info.getJoiner_id(), info.getBroad_user());
    }


    @Override
    public void onProfileItemSelectedListenerBack() {
        dismissActionSheetDialog();
    }

    LiveHostNameLayout.OnAddClickListener livehostnameclick = new LiveHostNameLayout.OnAddClickListener() {

            @Override
            public void onAvtarClick(String fuid, String wuid, String name) {
                showUserShortProfile(fuid, wuid, name);
            }
    };

    LiveRemoteHostNameLayout.OnAddClickListener liveRemoteHostNameClick = new LiveRemoteHostNameLayout.OnAddClickListener(){

            @Override
            public void onAvtarClick(String fuid, String wuid, String name) {
                showUserShortProfile(fuid, wuid, name);
            }

            @Override
            public void onPkEnd() {
                if(vm.isPkMode && vm.isOwner) endPkFinally();
            }

        };

    LiveRoomMessageAdapter.OnAddClickListener liveMessageAdapter = this::showUserShortProfile;


    private void endPkFinally(){
        updateState(STAT_PK_RESULT_MODE, vm.broad_usr_fuid, vm.pk_usr_uid);
        //showPkEndingResult();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.live_bottom_btn_close:

                onBackPressed();

                break;
            case R.id.start_pk_button:
                if (vm.isOwner) {
                    showPkTimeDialog(getContext(),(d, n)->{
                        //pktime =  n;
                        if(d!=null && d.isShowing()) d.dismiss();

                        Map<String, Object> map1 = new HashMap<>();
                        map1.put("pktime", n);

                        showActionSheetDialog(ACTION_SHEET_PK_ROOM_LIST, vm.broad_room_type,
                                true, true, map1, onInviteListener);
                    });

                }

                break;
            case R.id.layCloseCall:
                LiveFireFun.instance().closeCall_mode(vm.broad_usr_fuid);
                break;
            case R.id.start_calling_button:
                if (vm.isOwner) {
                    CallOption(view);
                }
                break;
            case R.id.img_speaker:
                speaker();
                break;
            case R.id.img_speakerPk:
                speaker();
                break;
            case R.id.live_bottom_btn_fun4:
                if (vm.isOwner) {
                    //more setting
                    LiveRoomToolActionSheet toolSheet = (LiveRoomToolActionSheet) showActionSheetDialog(
                            ACTION_SHEET_TOOL, 3, vm.isOwner, true, actionSheetListene_liveRoomTool);
                    toolSheet.setEnableInEarMonitoring(inEarMonitorEnabled);
                }else {
                    //send heart gift
                    int daily_heart = get_daily_heart();

                    if(daily_heart>0){
                        playHeartAnimation(0, null);
                        live_daily_heart_count.setText(String.valueOf(daily_heart-1));

                      DatabaseReference rff=FirebaseDatabase.getInstance().getReference("BroadLike");
                      String rff1=rff.push().getKey();
                      rff.child(vm.broad_usr_fuid)
                              .child(rff1).setValue(true);

                    }

                   /* hide(isStatus);
                    isStatus=!isStatus;*/
                    //showFullScreenGiftInfo(layout_full_gift_animation, "", "spa", "test", 3200);
                }
                break;
            case R.id.live_bottom_btn_fun3:

//                if(isFron){
//                    pkLayout.getLeftVideoLayout().setVisibility(View.GONE);
//                    local_or_broadcast_video_view.setVisibility(View.VISIBLE);
//                    moveLocalVideoToPrimaryView();
//                    isFron = false;
//                }else {
//                    local_or_broadcast_video_view.setVisibility(View.GONE);
//                    pkLayout.getLeftVideoLayout().setVisibility(View.VISIBLE);
//                    moveLocalVideoToThumbnailView();
//                    isFron = true;
//                }
                //AnimUtils.room_enter_animation(this, getScreenWidth(this), layout_room_enter_animation, "indifun");
                if (vm.isOwner) {
                    //beauty
                    showActionSheetDialog(ACTION_SHEET_BEAUTY, vm.broad_room_type,
                            vm.isOwner, true, actionBeautimode);
                } else {
                    //gift
                    //showActionSheetDialog(ACTION_SHEET_GIFT, vm.broad_room_type, vm.isOwner, true, this);
                    //loge(TAG, "click gift");
                    showActionSheetDialog(ACTION_SHEET_GIFT, vm.broad_room_type, false, true, this);
//                    onSendGift(position, info);
//                    dismissActionSheetDialog();
//                    mGiftBottomSheetDialog.setActionSheetListener((position, info) -> {
//                        onSendGift(position, info);
//                        dismissActionSheetDialog();
//                    });
//                    mGiftBottomSheetDialog.initPagers(getChildFragmentManager());
                }
                break;
            case R.id.live_bottom_btn_fun2:
                if (vm.isOwner) {
                    //music
                    showActionSheetDialog(ACTION_SHEET_BG_MUSIC, vm.broad_room_type,
                            vm.isOwner, true, this);
                } else {
                    //gift 2
                }
                break;
            case R.id.live_bottom_btn_fun1:
                break;

        }
    }


    private void uploadImage_sender() {
        DatabaseReference rf = FirebaseDatabase.getInstance().getReference("GiftSender");
        boolean isgolive=false;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("isgolive",isgolive);
        rf.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hashMap);

    }


    private void uploadImage_124() {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("GOLIVE1");

        database.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .removeValue();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onIndiActEvent(IndiActEvent event){
        if(event.WHAT==1){
            onBackPressed();
        }
    }

    @Override
    public void setVisibleOrNot(String fuid, boolean visible) {
        loge(TAG, "setVisibleOrNot", ""+fuid,  ""+visible);
//        if(!visible){
//            closeStreaming();
//            resetAll();
//           // isViewCreated = false;
//        }else {
//            loge(""+config().connectedfuid, ""+mClip.ownerfuid);
//            if(mClip.ownerfuid!=null && (config().connectedfuid==null || !config().connectedfuid.equals(mClip.ownerfuid))){
//                initLive();
//                config().connectedfuid = mClip.ownerfuid;
//            }
//        }
    }

    private void resetAll(){
        liveRoomMessageView.clear();
        isnameavtarset = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        IntentFilter headPhoneFilter = new IntentFilter();
        headPhoneFilter.addAction(AudioManager.ACTION_HEADSET_PLUG);
        getContext().registerReceiver(mHeadPhoneReceiver, headPhoneFilter);
        loge(TAG, "onStart");
        if(room!=null && room.getState() != Room.State.DISCONNECTED){
            resumecamera();
           // svgaImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resumecamera();

    }

    void resumecamera(){
        if(vm!=null && vm.isOwner){
            createLocalVideoTrack();
        }
        if (localVideoTrack!=null)localVideoTrack.addRenderer(localVideoView);
    }

    @Override
    public void onPause() {
        /*
         * Release the local video track before going in the background. This ensures that the
         * camera can be used by other applications while this app is in the background.
         */
        /*if (localVideoTrack != null) {
            *//*
             * If this local video track is being shared in a Room, unpublish from room before
             * releasing the video track. Participants will be notified that the track has been
             * unpublished.
             *//*
           *//* if (localParticipant != null) {
                localParticipant.unpublishTrack(localVideoTrack);
            }*//*

            localVideoTrack.release();
            localVideoTrack = null;
        }*/
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        loge(TAG, "onStop");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loge(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loge(TAG, "onDetach");
    }

    @Override
    public void onDestroy() {
        loge(TAG, "onDestroy");
        try {
            if(mHeadPhoneReceiver!=null)
                getContext().unregisterReceiver(mHeadPhoneReceiver);
        }catch (IllegalArgumentException e){}
        removeHeartListener();
        closeStreaming();
        super.onDestroy();
    }

    @Override
    protected void onChangeSender(List<GiftSenderModel> top) {

    }


    private void closeStreaming(){
        /*
         * Tear down audio management and restore previous volume stream
         */
        //if(getContext()!=null)
         //   AnimEnqueueService.stopQueue(getContext());

        disconnecting = true;
        shouldReconnect = false;
        if(vm!=null) vm.removeListeners();

        if(audioSwitch!=null) audioSwitch.stop();
        //setVolumeControlStream(savedVolumeControlStream);

        onStopMusicPlaying();
        /*
         * Always disconnect from the room before leaving the Activity to
         * ensure any memory allocated to the Room resource is freed.
         */

        disconnectFromRoom();

        /*
         * Release the local audio and video tracks ensuring any memory allocated to audio
         * or video is freed.
         */
        if (localAudioTrack != null) {
            localAudioTrack.release();
            localAudioTrack = null;
        }
        if (localVideoTrack != null) {
            localVideoTrack.release();
            localVideoTrack = null;
        }

        //removeListeners();
        dismissDialog();

    }

    public void onBackPressed() {
        if(isEndingPk) return;

        if(vm.isOwner){
            if(vm.isPkMode) {
                String title = getString(R.string.dialog_pk_force_quit_title);
                String message = getString(R.string.dialog_pk_force_quit_message);
                message = String.format(message, vm.pk_usr_name != null ? vm.pk_usr_name : "");
                curDialog = act.showDialog(title, message,
                        R.string.dialog_positive_button,
                        R.string.dialog_negative_button,
                        v -> endPk1(),
                        v -> dismissDialog());
                return;
            } else {
                curDialog = act.showDialog(R.string.end_live_streaming_title_owner,
                        R.string.end_live_streaming_message_owner,
                        view -> endBroadCast1());
                return;
            }
        }

        curDialog = act.showDialog(R.string.end_live_streaming_title_user,
                R.string.end_live_streaming_message_user,
                view -> leaveBroadcast1());
    }

    private void endPk1(){
        dismissDialog();
        endPkFinally();
        uploadImage_124();
        leaveRoomFinishListener();
    }

    private void endBroadCast1(){
        dismissDialog();
        //  seenMessage(fuid);
        Constants.removeBroacast(getContext(), new ControllModal(2,
                vm.broad_usr_fuid, vm.broad_usr_wuid,
                String.valueOf(vm.broadcast_id)));
        //uploadImage_12();
        uploadImage_124();
        leaveRoomFinishListener();
        act.supportFinishAfterTransition();

    }

    private void leaveBroadcast1(){
        dismissDialog();
        uploadImage_124();
        // uploadImage_12();
        uploadImage_sender();
        leaveRoomFinishListener();
        act.supportFinishAfterTransition();
    }


    @Override
    protected void onSendGift(int position, GiftInfo2 info) {
        sendFullScreenGift( info, null, null);
        uploadImage_123(info);
    }

    private void uploadImage_123(GiftInfo2 info) {
        Result r= IndifunApplication.getInstance().getCredential();
        DatabaseReference rf = FirebaseDatabase.getInstance().getReference("Gifting");
        String rw=rf.push().getKey();
        boolean isgolive=true;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("fuid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("full_name",r.getFullName());
        hashMap.put("avtar",r.getProfilePicture());
        hashMap.put("uid",r.getUid());
        hashMap.put("broadUid",vm.broadcast_id);
        hashMap.put("roomid",  vm.broad_room_id);
        hashMap.put("points",info.point);
        hashMap.put("isgolive",isgolive);


        //   hashMap.put("image",r.getProfilePicture());
        rf.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(rw).setValue(hashMap);
    }

    @Override
    public void onActionSheetMusicSelected(int index, String name, String url) {
        long now = System.currentTimeMillis();
        if (now - mLastMusicPlayedTimeStamp > MIN_ONLINE_MUSIC_INTERVAL) {
            //rtcEngine().startAudioMixing(url, false, false, -1);
           /* HandlerThread mHandlerThread = new HandlerThread("MusicHandlerThread");
            mHandlerThread.start();
            Handler mHandler = new Handler(mHandlerThread.getLooper());
            mHandler.post(()->{*/
            //new Thread(()->{
                try {
                    mediaPlayerHelper.playFile(getContext(), Uri.parse(MUSICLIST+url), mp -> {
                        if (!mediaPlayerHelper.isPlaying()) {
                            onStopMusicPlaying();
                        }
                    });

                    config().audiosessionid = mediaPlayerHelper.getAudioId();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            //}).start();



            /*if(audiosessionid!=-1){
                barVisualizer.setAudioSessionId(audiosessionid);
            }*/


            /*if (liveBottomButtonLayout != null) */liveBottomButtonLayout.setMusicPlaying(true);
            mLastMusicPlayedTimeStamp = now;

            dismissActionSheetDialog();
        }
    }

    @Override
    public void onActionSheetMusicStopped() {
        //rtcEngine().stopAudioMixing();
        onStopMusicPlaying();
    }

    void onStopMusicPlaying(){
        if (mediaPlayerHelper!=null && mediaPlayerHelper.isPlaying()) {
            mediaPlayerHelper.stopPlaying();
        }
        /*if (liveBottomButtonLayout != null) */liveBottomButtonLayout.setMusicPlaying(false);
    }

    protected void playHeartAnimation() {

        ImageView img = heartImage();
        layout_heart_animation_area.addView(img);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) img.getLayoutParams();
        params.width = 130;
        params.height = 130;
        img.setLayoutParams(params);
        AnimUtils.playHeartAnim(layout_heart_animation_area, img, liveBottomButtonLayout.xy(), TranslateView.getScreenHeight(getActivity()));

            sendHeartGiftMsg(vm.broad_room_id, vm.broad_usr_fuid, credentials, vm.local_level);
            RetroCalls
                    .instance()
                    .callType(HEART_POINT)
                    .withUID()
                    .stringParam(vm.broad_usr_wuid, String.valueOf(vm.broadcast_id))
                    .heart_point((type_result, obj2) -> {
                        if(obj2!=null && obj2.getStatus()==1 && obj2.getResult()!=null) {
                            int dailyheart = obj2.getResult().getHeartDaily();
                            saveIntData(getContext(), DAILY_HEART, dailyheart);
                            live_daily_heart_count.setText(String.valueOf(dailyheart));
                        }
                    });
    }


    private void nrLikes(final TextView likes, String postId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postId);
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    likes.setText("be_the_person_to_first_like");
                    likes.setVisibility(View.VISIBLE);
                } else {
                    likes.setText(dataSnapshot.getChildrenCount() + " " + "likes ");
                    likes.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void playHeartAnimation_withoutpoint() {


        ImageView img = heartImage();
        layout_heart_animation_area.addView(img);
        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) img.getLayoutParams();
        params.width = 130;
        params.height = 130;
        img.setLayoutParams(params);

        AnimUtils.playHeartAnim(layout_heart_animation_area, img, liveBottomButtonLayout.xy(),
                TranslateView.getScreenHeight(getActivity()));

        sendHeartGiftMsg(vm.broad_room_id, vm.broad_usr_fuid, credentials, vm.local_level);

        RetroCalls
                .instance()
                .callType(HEART_POINT)
                .withUID()
                .stringParam(vm.broad_usr_wuid, String.valueOf(vm.broadcast_id))
                .heart_point((type_result, obj2) -> {
                    if(obj2!=null && obj2.getStatus()==1 && obj2.getResult()!=null) {
                        int dailyheart = obj2.getResult().getHeartDaily();
                        saveIntData(requireContext(), DAILY_HEART, dailyheart);
                        live_daily_heart_count.setText(String.valueOf(dailyheart));
                    }
                });
    }

}
