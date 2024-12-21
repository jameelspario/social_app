package com.deificindia.indifun.deificpk.frags;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.deificindia.firebaseModel.GiftSenderModel;
import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.Model.abs_plugs.JoinerName;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.ListSorting;
import com.deificindia.indifun.Utility.Logger;
import com.deificindia.indifun.Utility.MyDatasKt;
import com.deificindia.indifun.Utility.Partition;
import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.db.TempDao;
import com.deificindia.indifun.db.TempDb;
import com.deificindia.indifun.deificpk.actionsheets.BackgroundMusicActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.CallPendingList;
import com.deificindia.indifun.deificpk.actionsheets.LiveRoomUserListActionSheet;
import com.deificindia.indifun.deificpk.modals.ChatModal;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.db.LiveEntity;
import com.deificindia.indifun.deificpk.actionsheets.AbstractActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.BeautySettingActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.LiveRoomSettingActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.PkRoomListActionSheet;
import com.deificindia.indifun.deificpk.actionsheets.toolactionsheet.LiveRoomToolActionSheet;
import com.deificindia.indifun.deificpk.acts.IndiActivity;
import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.deificpk.modals.Clip;
import com.deificindia.indifun.deificpk.modals.LiveConfig;
import com.deificindia.indifun.deificpk.modals.RndPkMod;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.deificpk.actionsheets.ProfilePreviewBottomSheetDialog;
import com.deificindia.indifun.dialogs.GiftBottomSheetDialog2;
import com.deificindia.indifun.events.IndiLiveFrag;
import com.deificindia.indifun.fires.FireConst;
import com.deificindia.indifun.fires.FireFun;
import com.deificindia.indifun.modals.Result;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.tag.ChipView;
import com.deificindia.indifun.vm.BroadVm;
import com.deificindia.indifun.vm.LiveVm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.twilio.video.RemoteParticipant;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import static com.deificindia.indifun.Utility.Constants.GETTOPGIFTER;
import static com.deificindia.indifun.Utility.Logger.toGson;
import static com.deificindia.indifun.deificpk.utils.Const.loge;
import static com.deificindia.indifun.fires.FireFun.BROADCASTERS;
import static com.deificindia.indifun.fires.FireFun.RANDOMPKCOLL;
import static com.deificindia.indifun.rest.RetroCallListener.GET_SERVER_TIME;
import static com.deificindia.indifun.rest.RetroCallListener.JOINER_NAME;

public abstract class BaseFireFragment extends Fragment {

    protected static final String TAG="BaseFireFragment";
    protected static final int SEARCHTIME = 30000;
    protected static final int TOAST_SHORT_INTERVAL = 2000;

    protected static final int IDEAL_MIN_KEYBOARD_HEIGHT = 200;
    protected static final int MIN_ONLINE_MUSIC_INTERVAL = 100;
    protected static final int TYPE_GIFT_JSON = 1;
    protected static final int TYPE_GIFT_SVGA = 2;
    protected static final int TYPE_GIFT_GIF = 3;
    protected static final int TYPE_GIFT_SEQ1 = 4;

    protected static final int TYPE_CHAT = 1;
    protected static final int TYPE_FULLSCREENGIFT = 2;
    protected static final int TYPE_HEART_GIFT = 3;
    protected static final int TYPE_ENTER = 3;
    public static final int TYPE_SYSTEM = 5;
    public static final int TYPE_CALL = 6;

    public static final int STAT_BROADCAST = 1;
    public static final int STAT_PK_INVITE = 2;
    public static final int STAT_PK_MODE = 3;
    public static final int STAT_REJECT_PK = 4;
    public static final int STAT_PK_RESULT_MODE = 5;
    public static final int STAT_RND_PK = 6;
    public static final int STAT_CALL_MODE = 7;
    public static final int STAT_CHANGE_FROM_PK_MODE = 8;

    protected int DISCONNECTSTATE=0;
    protected long mLastMusicPlayedTimeStamp;
    protected long pkstartTime;

    protected IndiActivity act;

    protected BroadVm vm;
    protected LiveVm livevm;
    Result credentials;

    public boolean inEarMonitorEnabled;
    public boolean mHeadsetWithMicrophonePlugged;

    protected LiveEntity2 mClip;

    //callControll Field
    protected int callIndex;
    protected boolean indexBzy;

    protected Map<String, ChipView> chipMap = new HashMap<>();
    protected Map<String, UserTags> chipMap2 = new HashMap<>();
    protected List<RemoteParticipant> remoteParticipantList = new ArrayList<>();
    protected LinkedList<String> animation_queue = new LinkedList<>();

    protected abstract void onSendGift(int position, GiftInfo2 info);
    protected abstract void changePkLayout(boolean isOwnr, boolean ispk);

    protected IndifunApplication app(){ return IndifunApplication.getInstance(); }

    protected LiveConfig config(){ return app().config(); }

    protected TempDao daoUser(){
        return MyDatasKt.__tempDao();
    }

    protected boolean is30NotSecPast(long past){
        if(past+30*1000 < System.currentTimeMillis()){
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////
    protected static final int ACTION_SHEET_VIDEO = 0;
    protected static final int ACTION_SHEET_BEAUTY = 1;
    protected static final int ACTION_SHEET_BG_MUSIC = 2;
    protected static final int ACTION_SHEET_GIFT = 3;
    protected static final int ACTION_SHEET_TOOL = 4;
    protected static final int ACTION_SHEET_VOICE = 5;
    protected static final int ACTION_SHEET_INVITE_AUDIENCE = 6;
    protected static final int ACTION_SHEET_ROOM_USER = 7;
    protected static final int ACTION_SHEET_PK_ROOM_LIST = 8;
    protected static final int ACTION_SHEET_PRODUCT_LIST = 9;
    protected static final int ACTION_SHEET_PRODUCT_INVITE_ONLINE_SHOP = 10;
    protected static final int ACTION_SHEET_PROFILE_PREVIEW = 11;
    protected static final int ACTION_SHEET_CALL_PENDING = 12;

    private static final int ACTION_SHEET_DIALOG_STYLE_RES = R.style.CustomBottomSheetDialogTheme;

    private Stack<AbstractActionSheet> mActionSheetStack = new Stack<>();
    private BottomSheetDialog mSheetDialog;

    protected boolean checkPermissionForCameraAndMicrophone() {
        int resultCamera = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA);
        int resultMic = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO);
        return resultCamera == PackageManager.PERMISSION_GRANTED &&
                resultMic == PackageManager.PERMISSION_GRANTED;
    }


    protected void showActionSheetDialog(final AbstractActionSheet sheet) {
        dismissActionSheetDialog();

        mSheetDialog = new BottomSheetDialog(getContext(), ACTION_SHEET_DIALOG_STYLE_RES);
        mSheetDialog.setCanceledOnTouchOutside(true);
        mSheetDialog.setContentView(sheet);
        //hideStatusBar(mSheetDialog.getWindow(), false);

        mSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (mActionSheetStack.isEmpty()) {
                    // Happens only in case of errors.
                    return;
                }

                if (sheet != mActionSheetStack.peek()) {
                    // When this action sheet is not at the top of
                    // stack, it means that a new action sheet
                    // is about to be shown and it needs a fallback
                    // history, and this sheet needs to be retained.
                    return;
                }

                // At this moment, we want to fallback to
                // the previous action sheet if exists.
                mActionSheetStack.pop();
                if (!mActionSheetStack.isEmpty()) {
                    AbstractActionSheet sheet = mActionSheetStack.peek();
                    ((ViewGroup) sheet.getParent()).removeAllViews();
                    showActionSheetDialog(mActionSheetStack.peek());
                }
            }
        });

        mSheetDialog.show();
    }

    protected AbstractActionSheet showActionSheetDialog(int sheetType, int liveType, boolean isHost, boolean newStack,
                                                        AbstractActionSheet.AbsActionSheetListener listener) {

        return showActionSheetDialog(sheetType, 1, isHost, newStack, null, listener);
    }

    protected AbstractActionSheet showActionSheetDialog(int sheetType, int liveType, boolean isHost, boolean newStack,
                                                        Map<String, Object> map,
                                                        AbstractActionSheet.AbsActionSheetListener listener) {
        AbstractActionSheet actionSheet;
        switch (sheetType) {
            case ACTION_SHEET_BEAUTY:
                actionSheet = new BeautySettingActionSheet(getContext());
                break;
            case ACTION_SHEET_BG_MUSIC:
                actionSheet = new BackgroundMusicActionSheet(getContext());
                break;
            case ACTION_SHEET_GIFT:
                /*actionSheet = new GiftBottomSheetDialog(getContext(), getChildFragmentManager());
                ((GiftBottomSheetDialog) actionSheet).registerLifecycle(getLifecycle());
                ((GiftBottomSheetDialog) actionSheet).init();
                break;*/
                showGiftBottomSheet();
                return null;
            case ACTION_SHEET_TOOL:
                actionSheet = new LiveRoomToolActionSheet(getContext());
                ((LiveRoomToolActionSheet) actionSheet).setHost(isHost);
                break;
           /*  case ACTION_SHEET_VOICE:
                actionSheet = new VoiceActionSheet(this);
                break;*/
           /* case ACTION_SHEET_INVITE_AUDIENCE:
                actionSheet = new InviteUserActionSheet(this);
                break;*/
           case ACTION_SHEET_ROOM_USER:
                actionSheet = new LiveRoomUserListActionSheet(getContext(), map);
                break;
            case ACTION_SHEET_PK_ROOM_LIST:
                actionSheet = new PkRoomListActionSheet(getContext(), map);
                break;
           /* case ACTION_SHEET_PRODUCT_LIST:
                actionSheet = new ProductActionSheet(this);
                break;
            case ACTION_SHEET_PRODUCT_INVITE_ONLINE_SHOP:
                actionSheet = new OnlineUserInviteCallActionSheet(this);
                break;*/
            case ACTION_SHEET_PROFILE_PREVIEW:
                actionSheet = new ProfilePreviewBottomSheetDialog(getContext(), map);
                //((ProfilePreviewBottomSheetDialog) actionSheet).setup(str[0], str[1], vm.broad_room_id, str[2],  vm.isOwner, vm.isPkMode, vm.broad_usr_fuid);
                break;
//            case ACTION_SHEET_CALL_PENDING:
//                actionSheet = new CallPendingList(getContext(), isHost, indexBzy, callIndex, vm.broad_room_id);
//                //actionSheet = new CallPendingList(getContext(), isHost);
//                break;
            default:
                actionSheet = new LiveRoomSettingActionSheet(getContext());
                ((LiveRoomSettingActionSheet) actionSheet).setFallback(!newStack);
                ((LiveRoomSettingActionSheet) actionSheet).setLiveType(liveType);
        }

        actionSheet.setActionSheetListener(listener);
        if (newStack) mActionSheetStack.clear();
        mActionSheetStack.push(actionSheet);
        showActionSheetDialog(actionSheet);
        return actionSheet;
    }

    protected void showCustomActionSheetDialog(boolean newStack, AbstractActionSheet sheet) {
        if (newStack) mActionSheetStack.clear();
        mActionSheetStack.push(sheet);
        showActionSheetDialog(sheet);
    }

    protected void showGiftBottomSheet(){
        //loge(TAG, "showGiftBottomSheet2");
        GiftBottomSheetDialog2 bottomSheet = new GiftBottomSheetDialog2();
        bottomSheet.setActionSheetListener((position, info) -> {
            onSendGift(position, info);
            bottomSheet.dismiss();
        });
        bottomSheet.show(getChildFragmentManager(), "GiftBottomSheetDialog");
    }

    protected void dismissActionSheetDialog() {
        if (mSheetDialog != null && mSheetDialog.isShowing()) {
            mSheetDialog.dismiss();
        }
    }

    private long mLastToastTime;

    protected void showShortToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    protected void showLongToast(String message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    protected void showToast(String message, int length) {
        long current = System.currentTimeMillis();
        if (current - mLastToastTime > TOAST_SHORT_INTERVAL) {
            // avoid showing the toast too frequently
            Toast.makeText(getContext(), message, length).show();
            mLastToastTime = current;
        }
    }

    ////////////////////////////////////////////////////////////////////
    ////////////Dialogs/////////////////

    ////////////////////////////////////////////////////////////////////
    //--going--live--
    void updateFire(String broad_room_name, String broad_room_id, String broad_usr_uid, String broad_usr_uid2,
                    String broad_usr_name,String broad_usr_avtar, long broad_usr_avtar_type, long broadcast_id){
        loge(TAG, "update broad info");
        Result cred = IndifunApplication.getInstance().getCredential();
        Map<String, Object> map = new HashMap<>();
        map.put("roomname", broad_room_name);
        map.put("roomid", broad_room_id);
        map.put("owneruid", broad_usr_uid);
        map.put("owneruidweb", broad_usr_uid2);
        map.put("ownername", broad_usr_name);
        map.put("owneravtar", broad_usr_avtar);
        map.put("owneravtartype", broad_usr_avtar_type);
        map.put("broadid", broadcast_id);

        if(cred!=null && cred.getAge()!=null) map.put("oage", cred.getAge());
        if(cred!=null && cred.getGender()!=null) map.put("ogender", cred.getGender());
       /* if(cred!=null && cred.level!=null) map.put("olevel", cred.level);
        if(cred!=null && cred.heart!=null) map.put("oheart", cred.heart);
        if(cred!=null && cred.diamond!=null) map.put("odiamond", cred.diamond);
        if(cred!=null && cred.xp!=null) map.put("oxp", cred.xp);
        if(cred!=null && cred.friends!=null) map.put("ofriends", cred.friends);*/

        map.put("ispk", false);
        map.put("pksender", false);
        map.put("timestamp", FieldValue.serverTimestamp());
        map.put("state", 1);

        FirebaseFirestore.getInstance().collection(BROADCASTERS)
                .document(broad_usr_uid)
                .set(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       // Toast.makeText(act, ""+broad_room_id, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    protected void showActionSheetCallingInfo(boolean isOwner, boolean newStack, EntityCall track,
                                              AbstractActionSheet.AbsActionSheetListener listener){

        AbstractActionSheet actionSheet = new CallPendingList(getContext(), isOwner, vm.broad_usr_fuid, track, vm.broad_room_id);
        actionSheet.setActionSheetListener(listener);
        if (newStack) mActionSheetStack.clear();
        mActionSheetStack.push(actionSheet);
        showActionSheetDialog(actionSheet);

    }
    //state == 5
    protected void updateState(long state, String self_uid1, String remote_uid1){
        Map<String, Object> map = new HashMap<>();
        map.put("state", state);

        updateSata(self_uid1, map);
        updateSata(remote_uid1, map);

    }

    //broadcast map update
    protected void updateSata(String uid,  Map<String, Object> map){
        loge(TAG, ""+uid);
        if(uid==null) return;
       FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
               .child(uid)
               .updateChildren(map);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////Chat//////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    protected void sendChatMessage(String toString, String broad_room_id, String broad_usr_uid, Result credentials, long xp) {
        /*FireFun.sendMessage(
                broad_room_id,
                broad_usr_uid,
                TYPE_CHAT,
                toString,
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
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
                0,"", "", ""
        );*/

        FireFun.sendMessage(ChatModal.instance().buildMessage(toString, broad_room_id, broad_usr_uid, credentials, xp)); //chat=1

    }

    protected void sendFullScreenGift1(String broad_room_id, String broad_usr_uid,
                                      Result credentials, long xp, GiftInfo2 info){
      /*  FireFun.sendMessage(
                broad_room_id,
                broad_usr_uid,
                TYPE_FULLSCREENGIFT,
                "",
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                credentials.getId(),
                credentials.getFullName(),
                credentials.getProfilePicture(),
                xp,
                info.getId(),
                String.valueOf(info.gift_category),
                info.getAnimation(),
                info.getName(),
                info.getType(),
                Long.parseLong(info.getPoint()),
                0,"", "", ""
        );*/


    }

    protected void sendFullScreenGift(String broad_room_id, String broad_usr_uid,
                                      Result credentials, long xp, GiftInfo2 info) {

        FireFun.sendMessage(ChatModal.instance().buildGift(broad_room_id, broad_usr_uid, credentials, xp, info)); //gift=2
    }

    protected void sendHeartGiftMsg1(String broad_room_id, String broad_usr_uid, Result credentials, long xp){
        /*FireFun.sendMessage(
                broad_room_id,
                broad_usr_uid,
                TYPE_HEART_GIFT,
                "",
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                credentials.getId(),
                credentials.getFullName(),
                credentials.getProfilePicture(),
                xp,
                0, "", "","",
                0,0,0,"", "",""
        );*/
    }

    protected void sendHeartGiftMsg(String broad_room_id, String broad_usr_uid, Result credentials, long xp){
        FireFun.sendMessage(ChatModal.instance().buildHeart(broad_room_id, broad_usr_uid, credentials, xp));
    }

    protected void onXpData(JoinerName xps){}

    protected void getUserXp(String broadfUid){
        RetroCalls.instance().callType(JOINER_NAME)
                .stringParam(broadfUid)
                .joiner_name((type_result, obj2) -> {
                    if(obj2!=null && obj2.getStatus()==1 && obj2.getResult()!=null){
                        onXpData(obj2.getResult());
                    }
                });

    }


    //finder is prim owner
    //send own info to remote rcvr owner
    void rndInfo(String fuid_local, String roomid, String fuid_remote, String wuid, String uname, String avtar, long avtartype, long pktime, long broaid){
        Map<String, Object> remotemap= new HashMap<>();
        remotemap.put("pkroomid", roomid);
        remotemap.put("pkuseruid", fuid_local);
        remotemap.put("pkuseruidweb", wuid);
        remotemap.put("pkusername", uname);
        remotemap.put("pkavtar", avtar);
        remotemap.put("pkavtartype", avtartype);
        remotemap.put("pkduration", pktime);
        //map.put("pkstarttime", pkstarttime);
        remotemap.put("pkbroadid", broaid);
        remotemap.put("pksender", false);
        //remotemap.put("ispk", false);
        remotemap.put("state", 6);

        Map<String, Object> localmap= new HashMap<>();
        localmap.put("pksender", true);
        localmap.put("pkduration", pktime);
        localmap.put("pkroomid", roomid);
        //map.put("pkstarttime", pkstarttime);
        localmap.put("state", 6);

        FireFun.updateMap2(fuid_remote, remotemap);
        FireFun.updateMap2(fuid_local, localmap);

    }

    //after rcvng state==6 rcvr owner sends own info to sender owner
    void rndPkRcvr(String fuid_local, String fuid, String wuid, String uname,
                   String avtar, long avtartype, long broaid) {
        Map<String, Object> remotemap = new HashMap<>();
        //remotemap.put("pkroomid", roomid);
        remotemap.put("pkuseruid", fuid);
        remotemap.put("pkuseruidweb", wuid);
        remotemap.put("pkusername", uname);
        remotemap.put("pkavtar", avtar);
        remotemap.put("pkavtartype", avtartype);
        //remotemap.put("pkduration", pktime);
        //map.put("pkstarttime", pkstarttime);
        remotemap.put("pkbroadid", broaid);
        remotemap.put("ispk", true);
        remotemap.put("state", 3);

        FireFun.updateMap2(fuid_local, remotemap);

        Map<String, Object> localmap= new HashMap<>();
        localmap.put("state", 3);
        localmap.put("ispk", true);
        FireFun.updateMap2(fuid, localmap);

    }


    ///////////////////////random pk end///////////////////////////////
    ///////////////////////////////////////////////////////////////////
    protected abstract void onReadPkInfoOnce(LiveEntity2 res);
    protected void readPkInfoOnce(String owneruid){

        FirebaseDatabase.getInstance().getReference(FireConst.COLL_GO)
                .child(owneruid)
                .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        LiveEntity2 entity2 = task.getResult().getValue(LiveEntity2.class);

                        if(entity2!=null){
                            onReadPkInfoOnce(entity2);
                            return;
                        }
                    }
                    onReadPkInfoOnce(null);
                }).addOnFailureListener(e -> {
                    onReadPkInfoOnce(null);
        });
    }


    public void hide(boolean b){
        EventBus.getDefault().post(new IndiLiveFrag(3, b));
    }


    protected int getPx(int dp){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    //protected abstract void onResume1();
    //protected abstract void onPause1();
    //protected abstract void onDestroy1();

    @Override
    public void onResume() {
        //onResume1();
        super.onResume();
        //onResume1();
    }

    @Override
    public void onPause() {
        //onPause1();
        super.onPause();
        //onPause1();
    }

    @Override
    public void onDestroy() {
        if(ref_gift_senders!=null) ref_gift_senders.removeEventListener(childEventListener);

        super.onDestroy();
    }

    /*
    *
    *
    * */
    DatabaseReference ref_gift_senders;
    ChildEventListener childEventListener;
    private final Map<String, GiftSenderModel> modwllist = new HashMap<>();
    protected List<GiftSenderModel> top10sender = new ArrayList<>();
    protected abstract void onChangeSender(List<GiftSenderModel> top);

    protected void readlivegiftsender(String room_id) {

        ref_gift_senders = FirebaseDatabase.getInstance().getReference(FireConst.GiftSender);
        Query query = ref_gift_senders.orderByChild("roomid").equalTo(room_id);

        childEventListener = query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                try {
                    GiftSenderModel fi = snapshot.getValue(GiftSenderModel.class);
                    if(modwllist.containsKey(fi.fuid)){
                        GiftSenderModel olffi = modwllist.get(fi.fuid);
                        olffi.points = olffi.points + fi.points;
                    }else {
                        modwllist.put(fi.fuid, fi);
                    }

                    short_crop();
                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    void short_crop(){
        List<GiftSenderModel> items = new ArrayList<>();
        for (String fu:modwllist.keySet()){
            items.add(modwllist.get(fu));
        }

        Collections.sort(items, new ListSorting.ShortGiftSenderModel());
        top10sender = items.size() > 10? Partition.ofSize(items, 10).get(0): items;
    }



}
