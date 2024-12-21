package com.deificindia.indifun.dialogs;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import com.deificindia.indifun.Model.GoldenCoinModal;
import com.deificindia.indifun.Model.abs.ObjectModal;
import com.deificindia.indifun.R;

import com.deificindia.indifun.Utility.Indifun;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.deificpk.modals.GiftInfo2;
import com.deificindia.indifun.deificpk.modals.GiftListResponse2;
import com.deificindia.indifun.dialogs.giftsheet.GiftItemListener;
import com.deificindia.indifun.dialogs.giftsheet.OnGiftItemSelectedListener;
import com.deificindia.indifun.dialogs.giftsheet.ParentFragmentPagerAdapter;
import com.deificindia.indifun.rest.AppConfig;
import com.deificindia.indifun.rest.RetroCallListener;
import com.deificindia.indifun.rest.RetroCalls;
import com.deificindia.indifun.ui.PagerSlidingTabStrip;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiftBottomSheetDialog2 extends BottomSheetDialogFragment
        implements View.OnClickListener, OnGiftItemSelectedListener {

    private GiftActionSheetListener mListener;

    public interface GiftActionSheetListener {
        void onActionSheetGiftSend(int position, GiftInfo2 info);
    }

    public void setActionSheetListener(GiftActionSheetListener listener) {
        this.mListener = listener;
    }

    ViewPager viewPager2;
    PagerSlidingTabStrip tabLayout;

    String mValueFormat;

    private int mSelected = -1;
    GiftInfo2 giftInfo;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gift_bottom_sheet_dialog, container, false);

    }

    Integer my_golden_coin = null;

    void initPagers(){
        tabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                /*Toast.makeText(HomeActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();*/
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

        viewPager2.setAdapter(new ParentFragmentPagerAdapter(getChildFragmentManager()));
        tabLayout.setViewPager(viewPager2);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mValueFormat = getResources().getString(R.string.live_room_gift_action_sheet_value_format);

        viewPager2 = view.findViewById(R.id.viewpager2);
        tabLayout = view.findViewById(R.id.tabs);

        viewPager2.setSaveEnabled(false);
        AppCompatTextView sendBtn = view.findViewById(R.id.live_room_action_sheet_gift_send_btn);
        GiftItemListener.setOnGiftItemSelectedListener(this);
        sendBtn.setOnClickListener(this);
        initPagers();

        /*
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
//        SharedPreferences editor = getContext().getSharedPreferences("PREFS", MODE_PRIVATE);
//        roomid=editor.getString("roomid","none");
        reference = FirebaseDatabase.getInstance()
                .getReference("UserDetails")
                .child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                  full=user.getFull_name();
                  profile=user.getImage();
                  fuid=user.getFuid();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


    }

    @Override
    public void onItemSelected(int pos, GiftInfo2 info) {
        mSelected = pos;
        giftInfo = info;

        if(my_golden_coin==null)
            golden_coin();

        if(my_golden_coin!=null && my_golden_coin < giftInfo.point){
            showAlertDialog();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.live_room_action_sheet_gift_send_btn) {
            getcoins();

        }
    }

    //------send gift------
    private void getcoins() {
        if(my_golden_coin!=null){

            if(my_golden_coin>=giftInfo.point){
                dismiss();
                mListener.onActionSheetGiftSend(mSelected, giftInfo);
                return;
            } else {
                showAlertDialog();
                return;
            }
        }

        golden_coin();

    }

    private void golden_coin(){
        RetroCalls.instance()
                .stringParam(IndifunApplication.getInstance().getCredential().user_token)
                .know_golden_coin((type_result, obj2) -> {

                if(obj2 != null && obj2.status == 1 && obj2.getResult() != null){
                    my_golden_coin = obj2.getResult().golden_coin==null
                            ||obj2.getResult().golden_coin.isEmpty()?0:Integer.parseInt(obj2.getResult().golden_coin);

                    getcoins();

                }else {
                    if(obj2!=null && obj2.status==0){
                        Indifun.toast(getContext(), ""+ obj2.getMessage(), "e");
                        return;
                    }
                }

            //    Indifun.toast(getContext(), "Something went wrong...", "e");

        });
    }

    private void showAlertDialog() {

        dismiss();

        if(getContext() == null) return;

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_gift_layout, null);
        //alertDialog=new AlertDialog.Builder(R.style.TransparentDialog,null);
        alertDialog.setView(customLayout);

        // send data from the AlertDialog to the Activity
        ImageView icclose = customLayout.findViewById(R.id.closeall);
        Button upgrade = customLayout.findViewById(R.id.upgrade);
//                int level = Integer.parseInt(obj2.getResult().level);
//        TextView textView = customLayout.findViewById(R.id.textcount);
//        textView.setText(level1);
        TextView textView1 = customLayout.findViewById(R.id.userlevel);
        TextView textView2 = customLayout.findViewById(R.id.text);

        AlertDialog alert = alertDialog.create();
        icclose.setOnClickListener(v -> alert.dismiss());

        upgrade.setOnClickListener(v -> {
            alert.dismiss();
            Intent RechargeCoins=new Intent(getContext(), com.deificindia.indifun.Activities.RechargeCoins.class);
            startActivity(RechargeCoins);
        });
        alert.setCanceledOnTouchOutside(false);

        alert.show();
    }

}
