package com.deificindia.indifun.rest;

import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.deificindia.indifun.R;
import com.deificindia.indifun.db.LiveRepo;

import static com.deificindia.indifun.rest.RetroCallListener.ONFOLLOWCLICK;

public class RestWork {

    public static void FollowApi(AppCompatButton btn, String followto){
        changeFolowButton(btn,2);

        RetroCalls.instance().callType(ONFOLLOWCLICK)
                .withUID()
                .stringParam(followto)
                .setOnFail((type_result, code, message) -> {
                    if(type_result==ONFOLLOWCLICK) {
                        changeFolowButton(btn, 0);
                    }
                })
                .follow_user((type_result, obj) -> {
                    if(obj!=null){
                        if(obj.getStatus()==1 && btn!=null){
                            changeFolowButton(btn, 1);
                            LiveRepo.updateIsFolowing(btn.getContext(), followto, 1);
                        }else {
                            changeFolowButton(btn, 0);
                        }
                    }
                });
    }

    public static void UnFollowApi(AppCompatButton btn, String followto){
        changeFolowButton(btn,2);

        RetroCalls.instance().callType(ONFOLLOWCLICK)
                .withUID()
                .stringParam(followto)
                .setOnFail((type_result, code, message) -> {
                    if(type_result==ONFOLLOWCLICK) {
                        changeFolowButton(btn, 1);
                    }
                })
                .unfollow_friend((type_result, obj) -> {
                    if(obj!=null){
                        if(obj.getStatus()==1 && btn!=null){
                            changeFolowButton(btn, 0);
                            LiveRepo.updateIsFolowing(btn.getContext(), followto, 0);
                        }else {
                            changeFolowButton(btn, 1);
                        }
                    }
                });
    }


    public static void changeFolowButton(String txt, AppCompatButton btn , int n){
        if(n==1) {
            btn.setBackgroundResource(R.drawable.ic_btn_state_disable_1);
            btn.setTextColor(ContextCompat.getColor(btn.getContext(), R.color.black_trans_90));
            btn.setText(txt);
            btn.setEnabled(true);
            //btn.setVisibility(View.GONE);
        }else if(n==2){
            btn.setText(txt);
            btn.setEnabled(false);
        }else {
            btn.setBackgroundResource(R.drawable.ic_btn_state_active_1);
            btn.setTextColor(ContextCompat.getColor(btn.getContext(), R.color.white));
            btn.setText(txt);
            btn.setEnabled(true);
        }
    }
    public static void changeFolowButton(AppCompatButton btn , int n){
        String txt = "";
        if(n==1) {
            txt = "Following";
        }else if(n==2){
            txt = "Follo...";
        }else {
            txt = "Follow";
        }
        changeFolowButton(txt, btn, n);
    }

    public static void changeTextView(TextView btn , int n){
        if(n==1) {
            btn.setBackgroundResource(R.drawable.ic_btn_state_activie_red);
            btn.setTextColor(ContextCompat.getColor(btn.getContext(), R.color.white));
            btn.setText("Block");
            btn.setEnabled(false);
        }else if(n==2){
            btn.setText("block...");
            btn.setEnabled(false);
        }else {
            btn.setBackgroundResource(R.drawable.ic_btn_state_active_1);
            btn.setTextColor(ContextCompat.getColor(btn.getContext(), R.color.white));
            btn.setText("UnBlock");
            btn.setEnabled(true);
        }
    }


}
