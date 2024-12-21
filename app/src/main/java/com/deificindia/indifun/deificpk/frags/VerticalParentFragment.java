package com.deificindia.indifun.deificpk.frags;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.firebaseModel.GiftSenderModel;
import com.deificindia.indifun.Model.abs_plugs.JoinerName;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.db.LiveEntity2;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.db.LiveEntity;
import com.deificindia.indifun.db.LiveRepo;
import com.deificindia.indifun.deificpk.modals.BroadList;
import com.deificindia.indifun.deificpk.modals.Clip;
import com.deificindia.indifun.events.IndiActEvent;
import com.deificindia.indifun.events.IndiLiveFrag;
import com.deificindia.indifun.interfaces.IndiFunResult;
import com.deificindia.indifun.modals.Result;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Random;

import static com.deificindia.indifun.deificpk.frags.LiveBaseFragment.ARG_CLIP;
import static com.deificindia.indifun.deificpk.utils.Const.KEY_BUNDLE_IS_OWNER;
import static com.deificindia.indifun.deificpk.utils.Const.loge;

public class VerticalParentFragment extends BaseFireFragment implements VisibilityAware{

    private static final String TAG = "VerticalParentFragment";

    LiveEntity2 mClip;
    ImageView imageView;
    TextView text;
    FrameLayout parent;

    boolean isowner;

    public VerticalParentFragment() {}

    public static VerticalParentFragment instance(LiveEntity2 clip, boolean isowner1) {
        VerticalParentFragment fragment = new VerticalParentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CLIP, clip);
        args.putBoolean(KEY_BUNDLE_IS_OWNER, isowner1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log( "onCreate");
        if (getArguments() != null) {
            mClip = (LiveEntity2) requireArguments().getSerializable(ARG_CLIP);
            isowner = requireArguments().getBoolean(KEY_BUNDLE_IS_OWNER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vertical_parent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    void initView(View view){

        imageView = view.findViewById(R.id.image_profile_top_layout_vertical_parent_frag);
        text = view.findViewById(R.id.txt_top_layout_vertical_parent_frag);
        parent = view.findViewById(R.id.parent_frame_layout);

        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        parent.setBackgroundColor(color);

        loge(TAG, "avtar",""+new Gson().toJson(mClip));
        if(mClip.owneravtar!=null && !mClip.owneravtar.isEmpty()) {
            String avtar = (mClip.owneravtartype == 0 ? URLs.UserImageBaseUrl : URLs.UserImageBaseUrlTemp) + mClip.owneravtar;
            loge(TAG, "avtar",""+avtar);
            Picasso.get()
                    .load(avtar)
                    .into(imageView);
        }

        if (mClip.owneruid!=null && mClip.ownername!=null && !mClip.ownername.isEmpty()) {
            text.setText("Connecting...");
        }else {
            text.setText("Live Ended... ");
        }

    }

    void attachView(){
        if(mClip.owneruid!=null && mClip.ownername!=null && !mClip.ownername.isEmpty()/*&& (config().connectedfuid==null || !config().connectedfuid.equals(mClip.ownerfuid))*/){
            replaceFragment(VerticalFramgment.newInstance(mClip, isowner), "f"+mClip.id);
            config().connectedfuid = mClip.owneruid;
        }
    }

    @Override
    public void setVisibleOrNot(String fuid, boolean visible) {
        loge("TAG", "setVisibleOrNot", ""+fuid,  ""+visible);
        if(!visible){
            removeFrag();
        }
       else {
            //loge(""+config().connectedfuid, ""+mClip.ownerfuid);
           /* if(mClip.ownerfuid!=null && (config().connectedfuid==null || !config().connectedfuid.equals(mClip.ownerfuid))){
                addFrag();
                config().connectedfuid = mClip.ownerfuid;
            }*/
        }
    }

    void changeState(String fuid, int s){
        LiveRepo.connectionState(getContext(), fuid, s);
    }

    private void replaceFragment(Fragment fragment, String tag) {
        removeFrag();
        getChildFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.top_frame_container_vertical_parent_frag, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void removeFrag(){
        if(getChildFragmentManager().findFragmentById(R.id.top_frame_container_vertical_parent_frag)!=null){
            getChildFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .remove(getChildFragmentManager().findFragmentById(R.id.top_frame_container_vertical_parent_frag))
                    .commitAllowingStateLoss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onIndiActEvent(IndiActEvent event){
        if(event.WHAT==1){
            if(mClip.owneruid==null || mClip.ownername==null || mClip.ownername.isEmpty()){
                EventBus.getDefault().post(new IndiLiveFrag(4));
            }
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        log( "onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        log( "onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
        attachView();
        log( "onResume");
    }


    @Override
    public void onPause() {
        removeFrag();
        changeState("", 0);
        super.onPause();
        log( "onPause");
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        log( "onStop");
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        log( "onSaveInstanceState");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log( "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log( "onDestroy");
    }

    @Override
    protected void onChangeSender(List<GiftSenderModel> top) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        log( "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log( "onDetach");
    }

    void log(String s){
        if(mClip!=null) {
            loge(TAG, s, mClip.owneruid, mClip.ownername);
        } else loge(TAG, s);
    }

    @Override
    protected void onSendGift(int position, GiftInfo2 info) { }

    @Override
    protected void changePkLayout(boolean isOwnr, boolean ispk) { }

    protected void onUserStateChange(LiveEntity en) { }

    @Override
    protected void onXpData(JoinerName xps) { }

    @Override
    protected void onReadPkInfoOnce(LiveEntity2 res) { }


}