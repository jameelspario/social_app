package com.deificindia.indifun.deificpk.actionsheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.transition.TransitionManager;

import com.deificindia.indifun.Model.ControllModal;
import com.deificindia.indifun.Model.abs_plugs.UserShotProfile;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.Constants;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.LevelUtils;
import com.deificindia.indifun.Utility.MySharePref;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.db.EntityCall;
import com.deificindia.indifun.db.LiveAppDb;
import com.deificindia.indifun.deificpk.frags.RemoteTrack;
import com.deificindia.indifun.deificpk.utils.ActivityUtils;
import com.deificindia.indifun.deificpk.utils.LevelControll;
import com.deificindia.indifun.deificpk.utils.UserTags;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.CircleImageView;
import com.deificindia.indifun.ui.EasyFlipView;
import com.deificindia.indifun.ui.ImageArea;
import com.squareup.picasso.Picasso;

import static com.deificindia.indifun.Utility.Constants.BLOACKUSER;
import static com.deificindia.indifun.Utility.Logger.loge;
import static com.deificindia.indifun.deificpk.utils.UserTags.GENDER;
import static com.deificindia.indifun.rest.RetroCallListener.ONFOLLOWCLICK;
import static com.deificindia.indifun.rest.RetroCallListener.USERPROFILE;

import java.util.Map;

public class ProfilePreviewBottomSheetDialog extends AbstractActionSheet implements ImageArea.OnImageClickListener {

    private String localUid;
    private String fuserId;
    private String wuserId;
    private String room_id;
    private String broad_user_fuid;
    private String uname;
    private boolean isOwner;
    private boolean isAdmin;

    private boolean ispkmode;


    View parent_zoom;
    CircleImageView profile_image;
    FrameLayout profileFrame;

    LinearLayout tags, controlLay ;
    TextView usenname, userstatus;
    ImageView bimg1, bimg2, bimg3, bimg4, bimg5;
    EasyFlipView Brodflip, Userflip;

    TextView txt1, txt_coin_sent;
    ImageArea ia_kick, ia_ban, ia_mute, ia_admin;
    TextView ulvlfront, ulvlback, blvlfront, blvlback;
    ProgressBar progressuserlevel, progressbroadlevel;

    UserTags userTags;

   /* public ProfilePreviewBottomSheetDialog1() { }
    public ProfilePreviewBottomSheetDialog1(String fuid, String wuid, String room_id, String uRemoteName, boolean isOw) {
        this.wuserId = wuid;
        this.fuserId = fuid;
        this.uname = uRemoteName;
        this.localUid = IndifunApplication.getInstance().getCredential().getId();
        this.isOwner = isOw;
        this.room_id = room_id;
    }

    public ProfilePreviewBottomSheetDialog1(String fuid, String wuid, String room_id, String uRemoteName,
                                           boolean isOw, boolean ispk,  String broad_user_fuid) {
        this.wuserId = wuid;
        this.fuserId = fuid;
        this.uname = uRemoteName;
        this.localUid = IndifunApplication.getInstance().getCredential().getId();
        this.isOwner = isOw;
        this.room_id = room_id;
        this.broad_user_fuid = broad_user_fuid;
        this.ispkmode = ispk;
    }*/

    public void setup(Map<String, Object> map) {
        try {

            this.wuserId = (String) map.get("fuid");
            this.fuserId = (String) map.get("wuid");
            this.uname = (String) map.get("name");
            this.localUid = IndifunApplication.getInstance().getCredential().getId();
            this.isOwner = (boolean) map.get("isOwner");
            this.room_id = (String) map.get("broad_room_id");
            this.broad_user_fuid = (String) map.get("broad_usr_fuid");
            this.ispkmode = (boolean) map.get("isPkMode");

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public interface OnProfileItemSelectedListener extends AbsActionSheetListener {
        void onProfileItemSelectedListenerBack();
    }

    private OnProfileItemSelectedListener mOnUserSelectedListener;

    @Override
    public void setActionSheetListener(AbsActionSheetListener listener) {
        if (listener instanceof LiveRoomUserListActionSheet.OnUserSelectedListener) {
            mOnUserSelectedListener = (OnProfileItemSelectedListener) listener;
        }
    }

    private void dismiss(){
        if(mOnUserSelectedListener!=null) mOnUserSelectedListener.onProfileItemSelectedListenerBack();
    }

    public ProfilePreviewBottomSheetDialog(Context context, Map<String, Object> map) {
        super(context);
        setup(map);
        onViewCreated();
    }

    ConstraintLayout progress_constraint;
    Guideline progress_guide;
    TextView curr_level, next_level, total_xp, next_xp, percent, txt_progress;

    public void onViewCreated() {

        LayoutInflater.from(getContext()).inflate(R.layout.profile_info, this, true);

        parent_zoom = findViewById(R.id.parent_zoom);
        profile_image = findViewById(R.id.profile_image);
        profileFrame = findViewById(R.id.profileFrame);

        tags = findViewById(R.id.tgview);
        userTags = UserTags.instance().container(tags);

        controlLay = findViewById(R.id.ln6);

        usenname = findViewById(R.id.tvusername);
        userstatus = findViewById(R.id.tvStatus);

        txt1 = findViewById(R.id.itxt);
        txt1.setText("");

        txt_coin_sent = findViewById(R.id.txt_coin_sent);
        curr_level = findViewById(R.id.txt_current_level);
        next_level = findViewById(R.id.txt_next_level);
        total_xp = findViewById(R.id.txt_total_xp);
        next_xp = findViewById(R.id.txt_to_next_xp);
        percent = findViewById(R.id.progress_percent);
        txt_progress = findViewById(R.id.txt_progress);

        progress_constraint = findViewById(R.id.layout_progress);
        progress_guide = findViewById(R.id.guide_progress);

        bimg1 = findViewById(R.id.bimg1);
        bimg2 = findViewById(R.id.bimg2);
        bimg3 = findViewById(R.id.bimg3);
        bimg4 = findViewById(R.id.bimg4);
        bimg5 = findViewById(R.id.bimg5);

        ia_kick = findViewById(R.id.bimg62);
        ia_ban = findViewById(R.id.bimg63);
        ia_mute = findViewById(R.id.bimg61);
        ia_admin = findViewById(R.id.bimg64);

        ia_kick.setOnImageClickListener(this);
        ia_ban.setOnImageClickListener(this);
        ia_mute.setOnImageClickListener(this);
        ia_admin.setOnImageClickListener(this);



        profileFrame.setOnClickListener(v->{
            dismiss();
            //ActivityUtils.openUserDetailsActivity(getContext(), wuserId, uname);
            ActivityUtils.openUserDetailsActivity3(getContext(),
                    wuserId, uname, parent_zoom);
        });

        apiCall();

    }

    @Override
    public void onClickIA(View v) {
        int id = v.getId();
        if(id==R.id.bimg61){
            changeAccess(Constants.MUTE);
        } else if(id==R.id.bimg62){
            changeAccess(Constants.KICK);
        } else if(id==R.id.bimg63){
            changeAccess(Constants.BLOCK);
        } /*else if(id==R.id.bimg64){

        }*/
    }

    void changeAccess(int type){
        Constants.sendToService(getContext(),
                new ControllModal(BLOACKUSER,
                        new String[]{room_id, wuserId, fuserId, uname},
                        new int[]{type})
        );

        dismiss();

    }

    void apiCall(){
        loge("ProfileBottomgSheet", localUid, fuserId);
        RetroCalls.instance().callType(USERPROFILE)
                .stringParam(localUid, fuserId)
                .get_user_profile((type_result, obj2) -> {
                    if(obj2!=null && obj2.getStatus()==1 && obj2.getResult()!=null){
                        loge("ProfileBottomgSheet", obj2.getResult().getFull_name());
                        setValue(obj2.getResult());
                        ulvlfront.setText(""+obj2.getResult().getUser_level());
                        ulvlback.setText(""+obj2.getResult().getTo_next_userlevel());
                        int minval = LevelControll.getMinLevelLimit(obj2.getResult().getUser_level());
                        int per = obj2.getResult().getUser_level()/(minval+obj2.getResult().getTo_next_userlevel());
                        progressuserlevel.setProgress(per);

                        blvlfront.setText(""+obj2.getResult().getBroadcast_level());
                        blvlback.setText(""+obj2.getResult().getTo_next_broadcastlevel());
                        int bminval = LevelControll.getMinLevelLimit(obj2.getResult().getBroadcast_level());
                        int bper = obj2.getResult().getBroadcast_level()/(bminval+obj2.getResult().getTo_next_broadcastlevel());
                        progressuserlevel.setProgress(bper);

                        //if(obj2.getResult().get)
                        /*profile_image.updateImageFrame(URLs.UserImageBaseUrl+obj2.getResult().getProfile_picture(),

                        ));*/
                        userstatus.setText(obj2.getResult().getWhatsup()==null?"":obj2.getResult().getWhatsup());

                        Picasso.get().load(URLs.UserImageBaseUrl+obj2.getResult().getProfile_picture())
                                .into(profile_image);

                        profileFrame.setBackgroundResource(
                                LevelControll.getFrameByXp(obj2.getResult().getMy_xp())
                        );

                        if(obj2.getResult().getGender()!=null && obj2.getResult().getAge()!=null){
                            /*TagView taggender;
                            if(tagmap.get("GENDER")!=null){
                                taggender = tagmap.get("GENDER");
                                UiUtils.setGenderTag(taggender, obj2.getResult().getGender(), obj2.getResult().getAge());

                            }else {
                                if(getContext()!=null) {
                                    taggender = new TagView(getContext());
                                    taggender.init();
                                    tags.addView(taggender);
                                    tagmap.put("GENDER", taggender);
                                    UiUtils.setGenderTag(taggender, obj2.getResult().getGender(), obj2.getResult().getAge());
                                }
                            }*/

                            UserTags.instance().container(tags).addTo(GENDER)
                                    .updateGender(obj2.getResult().getAge(),
                                            obj2.getResult().getGender());
                        }

                        if(!obj2.getResult().getId().equals(MySharePref.getUserId())) {

                            if(localUid.equals(wuserId)){
                                bimg5.setVisibility(View.GONE);
                            }
                            //bimg4.setVisibility(View.VISIBLE);
                            if (obj2.getResult().getIs_following() > 0) {
                                //callOption();
                            } else {
                                bimg5.setVisibility(View.VISIBLE);
                                bimg5.setImageResource(R.drawable.ic_follow_2);
                                bimg5.setOnClickListener(v -> {
                                    followapi(obj2.getResult().getId());
                                    dismiss();
                                });
                            }
                           // bimg4.setOnClickListener(v->{});

                            if(isOwner){
                                if(obj2.getResult().getIs_mute()<1 && obj2.getResult().getIs_kick()<1 && obj2.getResult().getIs_blocked()<1){
                                    makeVisible();
                                }else {
                                    if(obj2.getResult().getIs_mute()<1){
                                        addMessage("Is muted.");
                                    }
                                    if(obj2.getResult().getIs_kick()<1){
                                        addMessage("In kicked");
                                    }
                                    if(obj2.getResult().getIs_blocked()<1){
                                        addMessage("Is Blocked");
                                    }
                                }
                           /* if(obj2.getResult().isAdmin()==0){
                                ia_mute.setVisibility(View.VISIBLE);
                            }*/
                            }

                        }

                        loge("ProfileBottomgSheet", ""+isOwner,
                                ""+obj2.getResult().getIs_mute(),
                                ""+obj2.getResult().getIs_blocked());


                    }
                });
    }

    void setValue(UserShotProfile result){
        usenname.setText(uname);

        txt1.setText("ID: "+result.getUid());

        txt_coin_sent.setText("Sent: " + 0);

        if(result.getGender()!=null){
            userTags.addTo(UserTags.GENDER).updateGender(result.getAge(), result.getAge());
        }

        userTags.addTo(UserTags.LEVEL).updateLevel(""+result.getUser_level());

        userstatus.setText(result.getWhatsup());

        curr_level.setText("Lvl: "+result.getUser_level());
        next_level.setText("Lvl: "+result.getTo_next_userlevel());

        total_xp.setText("Xp: "+result.getMy_xp());
        next_xp.setText("Xp: "+result.getTo_next_userlevel());
        percent.setText("");








    }

    void makeVisible(){
            ia_mute.setVisibility(View.VISIBLE);
            ia_kick.setVisibility(View.VISIBLE);
            ia_ban.setVisibility(View.VISIBLE);
    }

    void addMessage(String msg){
        TextView tv = new TextView(getContext());
        controlLay.removeAllViews();
        controlLay.addView(tv);
        tv.setText(msg);
        tv.setTextColor(getContext().getResources().getColor(R.color.red));

    }

    private void followapi(String remote_uid) {
        RetroCalls.instance().callType(ONFOLLOWCLICK)
                .withUID()
                .stringParam(remote_uid)
                .setOnFail((type_result, code, message) -> {

                })
                .follow_user((type_result, obj) -> {
                    if(obj!=null){
                        if(obj.getStatus()==1){
                            //callOption();
                            bimg5.setVisibility(View.GONE);
                        }else {

                        }
                    }
                });
    }



    void  setProgress(float percent /*0.07f 7% => range: 0 <-> 1*/){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(progress_constraint);
        constraintSet.setGuidelinePercent(R.id.guide_progress, percent);
        TransitionManager.beginDelayedTransition(progress_constraint);
        constraintSet.applyTo(progress_constraint);
    }


}
