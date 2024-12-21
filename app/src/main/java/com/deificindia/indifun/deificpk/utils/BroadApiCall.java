package com.deificindia.indifun.deificpk.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import androidx.fragment.app.FragmentManager;

import com.deificindia.indifun.R;
import com.deificindia.indifun.anim.CircleReavealAnim;
import com.deificindia.indifun.anim.TranslateView;


public class BroadApiCall {

    Context context;
    FragmentManager fragmentManager;
    String _uid;
    View fromView;

    int cx, cy;

    public static BroadApiCall instance(){
        return new BroadApiCall();
    }

    public BroadApiCall with(Context context, FragmentManager fragmentManager, String uid){
        this.context = context;
        this.fragmentManager = fragmentManager;
        this._uid = uid;
        return this;
    }

    public BroadApiCall with(View fromView){
        this.fromView = fromView;
        return this;
    }

    public BroadApiCall with(Context context, String _uid) {
        this.context = context;
        this._uid = _uid;

        return this;
    }

    public BroadApiCall() {}

    public void start(){

        /*FullScreenDialog dialog = new FullScreenDialog();
        dialog.setOnOkClickListener(() -> {
            //is_user_broadcasting();
            startnewBroadcast();
            return true;
        });

        fragmentManager.beginTransaction()
                .add(dialog, "FullScreenDialog")
                .commitAllowingStateLoss();*/

        //dialog.show(fragmentManager, "FullScreenDialog");

        cx = TranslateView.getScreenWidth()/2;
        cy = TranslateView.getScreenHeight() - 50;

        showDiag();
    }

    private void showDiag() {

        final View dialogView = View.inflate(context, R.layout.go_live_dialog,null);

        final Dialog dialog = new Dialog(context, R.style.FullScreenDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //STYLE_NO_FRAME
        dialog.setContentView(dialogView);

        dialog.findViewById(R.id.btngotit).setOnClickListener(v -> {
            /*circleAnim(dialogView, false, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                }
            });*/
            dialog.dismiss();
            //dialogView.setVisibility(View.INVISIBLE);
            startnewBroadcast();

        });

        dialog.findViewById(R.id.imgClose).setOnClickListener(v->{

            circleAnim(dialogView, false, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    dialogView.setVisibility(View.INVISIBLE);
                }
            });
        });

        dialog.setOnShowListener(dialogInterface -> {
            //CircleReavealAnim.circleRevealDialog(dialogView, cx, cy, true, null)
            circleAnim(dialogView, true, null);
        });

        dialog.setOnKeyListener((dialogInterface, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK){
                circleAnim(dialogView, false, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        dialog.dismiss();
                        dialogView.setVisibility(View.INVISIBLE);
                    }
                });
                return true;
            }

            return false;
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    void circleAnim(View dialogView, boolean b, AnimatorListenerAdapter listener){
        if(fromView==null){
            CircleReavealAnim.circleRevealDialog(dialogView, cx, cy, b, listener);
        }else {
            CircleReavealAnim.circleRevealDialog(dialogView, fromView, b, listener);
        }

    }

    void startnewBroadcast(){
        ActivityUtils.startLivePrepAsOwner(context);
    }


}
